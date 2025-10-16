package uo.ri.cws.application.invoice.crud;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.assertion.ArgumentChecks;

public class FindNotInvoicedWorkOrdersByClient
    implements Command<List<InvoicingWorkOrderDto>> {

    private WorkOrderGateway wg = Factories.persistence.forWorkOrder();
    private String nif;

    public FindNotInvoicedWorkOrdersByClient(String nif) {
	ArgumentChecks.isNotNull(nif);
	this.nif = nif;
    }

    @Override
    public List<InvoicingWorkOrderDto> execute() {

	List<InvoicingWorkOrderDto> result = new ArrayList<>();
	List<WorkOrderRecord> wrList = wg.findNotInvoicedWorkOrders(nif);
	for (WorkOrderRecord wr : wrList) {
	    result.add(InvoicingWorkOrderAssembler.toDto(wr));
	}
	return result;

    }
}
