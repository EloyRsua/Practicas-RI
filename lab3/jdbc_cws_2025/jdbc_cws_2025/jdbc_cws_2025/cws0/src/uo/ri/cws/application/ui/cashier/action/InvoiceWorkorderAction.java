package uo.ri.cws.application.ui.cashier.action;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class InvoiceWorkorderAction implements Action {

    @Override
    public void execute() throws BusinessException {
	List<String> workOrderIds = new ArrayList<>();

	// Ask the user the work order ids
	do {
	    String id = Console.readString("Workorder id");
	    workOrderIds.add(id);
	} while (moreWorkOrders());

	/// 1. Creamos el objeto de negocio
//	InvoiceWorkorder iw = new InvoiceWorkorder(workOrderIds);
//	iw.execute();

	/// 2. Llamamos ahora al servicio
//	InvoicingService ics = new InvoiceCrudServiceImpl();
//	InvoiceDto dto = ics.create(workOrderIds);
//
//	displayInvoice(dto.number, dto.date, dto.amount - dto.vat, dto.vat,
//	    dto.amount);

	/// 2. Llamamos ahora a la factoria
	InvoicingService ics = Factories.service.forCreateInvoiceService();
	InvoiceDto dto = ics.create(workOrderIds);

	/// Se imprime: ojo en amount tenemos el total incluido el vat, es por
	/// eso que para calcular el total (sin iva) tenemos que restarle el
	/// vat del total (el vat es la cantidad, no el porcentaje)
	displayInvoice(dto.number, dto.date, dto.amount - dto.vat, dto.vat,
	    dto.amount);

    }

    private boolean moreWorkOrders() {
	return Console.readString("more work orders? (y/n) ")
		      .equalsIgnoreCase("y");
    }

    private void displayInvoice(long numberInvoice, LocalDate dateInvoice,
	double totalInvoice, double vat, double totalConIva) {

	Console.printf("Invoice number: %d\n", numberInvoice);
	Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", dateInvoice);
	Console.printf("\tAmount: %.2f €\n", totalInvoice);
	Console.printf("\tVAT: %.1f %% \n", vat);
	Console.printf("\tTotal (including VAT): %.2f €\n", totalConIva);
    }
}