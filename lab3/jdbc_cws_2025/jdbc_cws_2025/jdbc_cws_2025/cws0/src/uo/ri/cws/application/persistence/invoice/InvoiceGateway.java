package uo.ri.cws.application.persistence.invoice;

import java.time.LocalDate;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public interface InvoiceGateway extends Gateway<InvoiceRecord> {

    public long getLastNumber();

    public class InvoiceRecord extends Record {
	public Double amount;
	public LocalDate date;
	public long number;
	public String state;
	public Double vat;
    }

}
