package uo.ri.cws.application.ui.manager.payroll.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteLastMonthPayrollOfMechanicAction implements Action {

    PayrollService ps = Factories.service.forPayrollService();

    @Override
    public void execute() throws BusinessException {
	String mechanicId = Console.readString("Mechanic id");

	ps.deleteLastGeneratedOfMechanicId(mechanicId);
	Console.println("Last month's payroll for the mechanic deleted");
    }
}