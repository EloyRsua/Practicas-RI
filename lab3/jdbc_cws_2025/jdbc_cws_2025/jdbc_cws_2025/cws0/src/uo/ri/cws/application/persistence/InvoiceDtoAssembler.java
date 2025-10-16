package uo.ri.cws.application.persistence;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;

public class InvoiceDtoAssembler {

    /**
     * Convierte un InvoicingWorkOrderRecord en un InvoicingWorkOrderDto
     * 
     * @param r
     * @return
     */
    public static InvoicingWorkOrderDto toDto(InvoicingWorkOrderRecord r) {
	InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
	dto.id = r.id;
	dto.description = r.description;
	dto.date = r.date;
	dto.state = r.state;
	dto.amount = r.amount;
	return dto;
    }

    /**
     * Convierte una lista de InvoicingWorkOrderRecord en una lista de
     * InvoicingWorkOrderDto
     * 
     * @param records
     * @return
     */
    public static List<InvoicingWorkOrderDto> toDtoList(List<InvoicingWorkOrderRecord> records) {
	return records.stream()
		      .map(InvoiceDtoAssembler::toDto)
		      .toList();
    }

    public static InvoiceRecord toRecord(InvoiceDto dto) {
	InvoiceRecord ir = new InvoiceRecord();
	ir.id = dto.id;
	ir.version = dto.version;
	ir.amount = dto.amount;
	ir.vat = dto.vat;
	ir.number = dto.number;
	ir.date = dto.date;
	ir.state = dto.state;
	// Campos adicionales que no están en el DTO
	ir.createdAt = LocalDateTime.now();
	ir.updatedAt = LocalDateTime.now();
	ir.entityState = "ENABLED";

	return ir;
    }

}
