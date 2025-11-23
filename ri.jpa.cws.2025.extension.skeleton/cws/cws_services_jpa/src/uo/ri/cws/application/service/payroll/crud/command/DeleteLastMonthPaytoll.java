package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.exception.BusinessException;

public class DeleteLastMonthPaytoll implements Command<Integer> {

	public PayrollRepository repo = Factories.repository.forPayroll();

	@Override
	public Integer execute() throws BusinessException {
		List<Payroll> payrolls = repo.findLastMonthPayrolls();
		for (Payroll p : payrolls) {
			repo.remove(p);
		}
		return payrolls.size();
	}

}
