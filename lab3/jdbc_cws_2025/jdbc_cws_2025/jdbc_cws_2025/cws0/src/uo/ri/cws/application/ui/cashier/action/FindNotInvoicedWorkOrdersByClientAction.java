package uo.ri.cws.application.ui.cashier.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindNotInvoicedWorkOrdersByClientAction implements Action {

    @Override
    public void execute() throws BusinessException {
	String nif = Console.readString("Client nif");

	InvoicingService ivc = Factories.service.forCreateInvoiceService();
	List<InvoicingWorkOrderDto> workOrders = ivc.findNotInvoicedWorkOrdersByClientNif(
	    nif);

	Console.println("\nClient's not invoiced work orders\n");

	for (InvoicingWorkOrderDto wo : workOrders) {
	    Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n", wo.id,
		wo.description, wo.date, wo.state, wo.amount);
	}
    }
}
