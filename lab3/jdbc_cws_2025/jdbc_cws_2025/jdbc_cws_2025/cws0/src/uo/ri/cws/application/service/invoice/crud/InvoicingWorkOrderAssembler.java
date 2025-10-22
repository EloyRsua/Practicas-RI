package uo.ri.cws.application.service.invoice.crud;

import java.time.LocalDateTime;

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

    public static WorkOrderRecord toRecord(InvoicingWorkOrderDto dto) {
	if (dto == null) {
	    return null;
	}

	WorkOrderRecord r = new WorkOrderRecord();
	r.id = dto.id;
	r.description = dto.description;
	r.date = dto.date != null ? dto.date : LocalDateTime.now(); // si
								    // quieres
								    // asignar
								    // la fecha
								    // actual
								    // por
								    // defecto
	r.state = dto.state;
	r.amount = dto.amount;

	// Por defecto los IDs de relaciones quedan nulos; se pueden asignar
	// después
	r.invoice_id = null;
	r.mechanic_id = null;
	r.vehicle_id = null;

	return r;
    }
}
