package uo.ri.cws.application.service.invoice.crud;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;

public class InvoiceAssembler {

    public static InvoiceDto toDto(InvoiceRecord r) {
        if (r == null) {
            return null;
        }

        InvoiceDto dto = new InvoiceDto();
        dto.id = r.id;
        dto.version = r.version;
        dto.amount = (r.amount != null) ? r.amount : 0.0;
        dto.vat = (r.vat != null) ? r.vat : 0.0;
        dto.number = r.number;
        dto.date = r.date;
        dto.state = r.state;

        return dto;
    }

    public static InvoiceRecord toRecord(InvoiceDto dto) {
        if (dto == null) {
            return null;
        }

        InvoiceRecord r = new InvoiceRecord();
        r.id = dto.id;
        r.version = dto.version;
        r.amount = dto.amount;
        r.vat = dto.vat;
        r.number = dto.number;
        r.date = dto.date;
        r.state = dto.state;

        // Campos heredados de Record
        r.createdAt = null;
        r.updatedAt = null;
        r.entityState = null;

        return r;
    }
}
