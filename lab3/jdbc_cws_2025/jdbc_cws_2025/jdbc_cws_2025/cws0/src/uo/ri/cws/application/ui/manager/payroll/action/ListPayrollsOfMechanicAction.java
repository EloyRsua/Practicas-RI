package uo.ri.cws.application.ui.manager.payroll.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListPayrollsOfMechanicAction implements Action {

    PayrollService ps = Factories.service.forPayrollService();

    @Override
    public void execute() throws BusinessException {
	String mechanicId = Console.readString("Mechanic id");
	List<PayrollSummaryDto> payrolls = ps.findSummarizedByMechanicId(
	    mechanicId);

	if (payrolls.isEmpty()) {
	    Console.println("No payrolls found for this mechanic");
	    return;
	}

	for (PayrollSummaryDto dto : payrolls) {
	    Printer.printPayrollSummary(dto);
	}
    }
}