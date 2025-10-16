package uo.ri.cws.application.invoice.crud;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;

public class InvoicingWorkOrderAssembler {

    public static InvoicingWorkOrderDto toDto(WorkOrderRecord r) {
	if (r == null) {
	    return null;
	}

	InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
	dto.id = r.id;
	dto.description = r.description;
	dto.date = r.date;
	dto.state = r.state;
	dto.amount = r.amount;

	return dto;
    }
}
