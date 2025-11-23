package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<String> workOrderIds;

	private InvoiceRepository invoiceRepo = Factories.repository.forInvoice();
	private WorkOrderRepository workorderRepo = Factories.repository
		.forWorkOrder();

	public CreateInvoiceFor(List<String> workOrderIds) {
		ArgumentChecks.isNotNull(workOrderIds);
		ArgumentChecks.isFalse(workOrderIds.isEmpty());
		ArgumentChecks.isFalse(workOrderIds.stream()
			.anyMatch(i -> i == null));

		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		//
		List<WorkOrder> workorderList = workorderRepo.findByIds(workOrderIds);
		BusinessChecks.isTrue(workOrderIds.size() == workorderList.size(),
			"All the workorders must exist");
		BusinessChecks.isTrue(check(workorderList),
			"All workorders must be finished");
		long number = invoiceRepo.getNextInvoiceNumber();
		Invoice invoice = new Invoice(number, workorderList);
		invoiceRepo.add(invoice);
		return DtoAssembler.toDto(invoice);

	}

	private boolean check(List<WorkOrder> workorderList) {
		for (WorkOrder w : workorderList) {
			if (!w.isFinished()) {
				return false;
			}
		}
		return true;
	}

}
