package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class DeleteLasGeneratedPayroll implements Command<Integer> {

    PayrollGateway pg = Factories.persistence.forPayroll();

    @Override
    public Integer execute() throws BusinessException {
	List<PayrollRecord> list = pg.findPayrollsByLocalDate(LocalDate.now()
								       .minusMonths(
									   1));
	for (PayrollRecord pr : list) {
	    pg.remove(pr.id);
	}

	return list.size();
    }

}
