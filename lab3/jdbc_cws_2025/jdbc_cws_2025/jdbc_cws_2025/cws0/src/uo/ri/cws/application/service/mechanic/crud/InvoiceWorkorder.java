package uo.ri.cws.application.service.mechanic.crud;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.crud.InvoiceAssembler;
import uo.ri.cws.application.service.invoice.crud.InvoicingWorkOrderAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.math.Rounds;

public class InvoiceWorkorder {

    private final InvoiceGateway ig = Factories.persistence.forInvoice();
    private final WorkOrderGateway wg = Factories.persistence.forWorkOrder();
    private final List<String> workOrderIds;

    public InvoiceWorkorder(List<String> workOrderIds) {
	ArgumentChecks.isNotNull(workOrderIds);
	checkWorkOrderIds(workOrderIds);
	this.workOrderIds = workOrderIds;
    }

    private void checkWorkOrderIds(List<String> workOrderIds) {
	if (workOrderIds.isEmpty()) {
	    throw new IllegalArgumentException(
		"WorkOrder list cannot be empty");
	}

	for (String id : workOrderIds) {
	    if (id == null) {
		throw new IllegalArgumentException(
		    "WorkOrder list cannot contain nulls");
	    }
	}
    }

    public InvoiceDto execute() throws BusinessException {
	try (Connection ignored = Jdbc.createThreadConnection()) {

	    checkWorkOrdersExist(workOrderIds);
	    checkWorkOrdersFinished(workOrderIds);

	    long invoiceNumber = ig.getLastNumber();
	    LocalDate invoiceDate = LocalDate.now();

	    double amount = calculateTotalInvoice(workOrderIds);
	    double vatPercentage = vatPercentage(invoiceDate);
	    double vatAmount = amount * (vatPercentage / 100);
	    double total = Rounds.toCents(amount + vatAmount);

	    String invoiceId = createInvoice(invoiceNumber, invoiceDate,
		vatAmount, total);
	    updateWorkOrdersWithInvoice(invoiceId, workOrderIds);

	    return buildInvoiceDto(invoiceId, invoiceNumber, invoiceDate,
		vatAmount, total);

	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    private void updateWorkOrdersWithInvoice(String invoiceId,
	List<String> workOrderIds) {
	for (String workOrderId : workOrderIds) {
	    WorkOrderRecord wr = new WorkOrderRecord();
	    wr.id = workOrderId;
	    wr.invoice_id = invoiceId;
	    wg.update(wr);
	}
    }

    private void checkWorkOrdersExist(List<String> workOrderIds)
	throws SQLException, BusinessException {
	for (String id : workOrderIds) {
	    Optional<WorkOrderRecord> maybeWorkOrder = wg.findById(id);
	    BusinessChecks.exists(maybeWorkOrder,
		"The WorkOrder does not exist");
	}
    }

    private void checkWorkOrdersFinished(List<String> workOrderIds)
	throws SQLException, BusinessException {
	for (String id : workOrderIds) {
	    Optional<WorkOrderRecord> maybeWorkOrder = wg.findById(id);
	    BusinessChecks.exists(maybeWorkOrder,
		"The WorkOrder does not exist");

	    InvoicingWorkOrderDto dto = InvoicingWorkOrderAssembler.toDto(
		maybeWorkOrder.get());
	    if (!"FINISHED".equalsIgnoreCase(dto.state)) {
		throw new BusinessException(
		    "The workorder is not finished yet");
	    }
	}
    }

    private double calculateTotalInvoice(List<String> workOrderIds)
	throws SQLException {
	double total = 0.0;

	for (String id : workOrderIds) {
	    Optional<WorkOrderRecord> maybeWorkOrder = wg.findById(id);
	    if (maybeWorkOrder.isEmpty()) {
		continue;
	    }

	    InvoicingWorkOrderDto dto = InvoicingWorkOrderAssembler.toDto(
		maybeWorkOrder.get());
	    total += dto.amount;
	}

	return total;
    }

    private double vatPercentage(LocalDate date) {
	LocalDate vatChangeDate = LocalDate.parse("2012-07-01");
	return vatChangeDate.isBefore(date) ? 21.0 : 18.0;
    }

    private String createInvoice(long number, LocalDate date, double vat,
	double total) throws SQLException {
	InvoiceDto dto = new InvoiceDto();
	dto.id = UUID.randomUUID()
		     .toString();
	dto.number = number;
	dto.date = date;
	dto.vat = vat;
	dto.amount = total;
	dto.state = "NOT_YET_PAID";

	ig.add(InvoiceAssembler.toRecord(dto));
	return dto.id;
    }

    private InvoiceDto buildInvoiceDto(String id, long number, LocalDate date,
	double vat, double total) {
	InvoiceDto dto = new InvoiceDto();
	dto.id = id;
	dto.version = 1L;
	dto.amount = total;
	dto.vat = vat;
	dto.number = number;
	dto.date = date;
	dto.state = "NOT_YET_PAID";
	return dto;
    }
}
