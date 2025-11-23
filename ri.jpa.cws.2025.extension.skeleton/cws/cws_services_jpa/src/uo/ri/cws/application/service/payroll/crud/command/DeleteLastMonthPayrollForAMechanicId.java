package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteLastMonthPayrollForAMechanicId implements Command<Void> {

	private String mechanicId;

	private PayrollRepository payrollRepo = Factories.repository.forPayroll();
	private MechanicRepository mechanicRepo = Factories.repository
		.forMechanic();

	public DeleteLastMonthPayrollForAMechanicId(String mechanicId) {
		ArgumentChecks.isNotBlank(mechanicId);

		this.mechanicId = mechanicId;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Mechanic> mechanic = mechanicRepo.findById(mechanicId);
		BusinessChecks.exists(mechanic, "The mechanic must exist");

		List<Payroll> lista = payrollRepo.findByMechanicIdInMonth(mechanicId);

		for (Payroll p : lista) {
			payrollRepo.remove(p);
		}

		return null;
	}

}
