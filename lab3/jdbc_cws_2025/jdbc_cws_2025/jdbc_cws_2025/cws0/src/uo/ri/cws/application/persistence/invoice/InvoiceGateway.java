package uo.ri.cws.application.persistence.invoice;

import java.time.LocalDate;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public interface InvoiceGateway extends Gateway<InvoiceRecord> {

    /**
     * getLastNumber: Obtiene el último número de factura registrado en la base
     * de datos y devuelve el siguiente disponible.
     *
     * @return long - Número de factura siguiente disponible.
     *
     *         Ejemplo de uso: long nextNumber = invoiceGateway.getLastNumber();
     */
    public long getLastNumber();

    public class InvoiceRecord extends Record {
	public Double amount;
	public LocalDate date;
	public long number;
	public String state;
	public Double vat;
    }

}
