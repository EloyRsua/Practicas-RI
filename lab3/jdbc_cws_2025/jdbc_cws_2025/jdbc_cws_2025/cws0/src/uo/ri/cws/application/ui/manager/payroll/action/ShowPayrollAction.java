package uo.ri.cws.application.ui.manager.payroll.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ShowPayrollAction implements Action {

    @Override
    public void execute() throws BusinessException {
	String payrollId = Console.readString("Payroll id");
	PayrollService ps = Factories.service.forPayrollService();
	Optional<PayrollDto> opdto = ps.findById(payrollId);
	PayrollDto dto = opdto.get();
	Printer.printPayrollDetails(dto);

    }
}
