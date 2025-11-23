package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindNotInvoicedWorkordersByClientNif
	implements Command<List<InvoicingWorkOrderDto>> {

	private WorkOrderRepository repo = Factories.repository.forWorkOrder();

	private String nif;

	public FindNotInvoicedWorkordersByClientNif(String nif) {
		ArgumentChecks.isNotNull(nif);

		this.nif = nif;
	}

	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		List<WorkOrder> workorders = repo.findNotInvoicedByClientNif(nif);
		return DtoAssembler.toInvoicingWorkOrderDtoList(workorders);
	}

}
