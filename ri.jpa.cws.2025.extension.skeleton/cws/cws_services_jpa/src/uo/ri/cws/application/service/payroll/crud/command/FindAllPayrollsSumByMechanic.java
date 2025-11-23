package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindAllPayrollsSumByMechanic
	implements Command<List<PayrollSummaryDto>> {

	private String id;

	private PayrollRepository payrollRepo = Factories.repository.forPayroll();
	private MechanicRepository mechanicRepo = Factories.repository
		.forMechanic();

	public FindAllPayrollsSumByMechanic(String id) {
		ArgumentChecks.isNotBlank(id);

		this.id = id;
	}

	@Override
	public List<PayrollSummaryDto> execute() throws BusinessException {
		Optional<Mechanic> mechanic = mechanicRepo.findById(id);
		BusinessChecks.exists(mechanic, "The mechanic must exist");

		List<Payroll> lista = payrollRepo.findByMechanicId(id);

		return DtoAssembler.toSummaryDtoList(lista);
	}

}
