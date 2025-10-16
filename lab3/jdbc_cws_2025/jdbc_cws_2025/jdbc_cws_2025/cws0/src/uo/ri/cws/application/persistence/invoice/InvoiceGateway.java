package uo.ri.cws.application.persistence.invoice;

import java.time.LocalDate;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public interface InvoiceGateway extends Gateway<InvoiceRecord> {

    public long findNextNumber();

    public static class InvoiceRecord extends Record {

	public double amount;
	public LocalDate date;
	public long number;
	public double vat;
	public String state;
    }

}
