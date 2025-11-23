package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayrollsByProfGroup
	implements Command<List<PayrollSummaryDto>> {

	private String name;

	private PayrollRepository payrollRepo = Factories.repository.forPayroll();
	private ProfessionalGroupRepository profRepo = Factories.repository
		.forProfessionalGroup();

	public FindPayrollsByProfGroup(String name) {
		ArgumentChecks.isNotBlank(name);

		this.name = name;
	}

	@Override
	public List<PayrollSummaryDto> execute() throws BusinessException {
		Optional<ProfessionalGroup> group = profRepo.findByName(name);
		BusinessChecks.exists(group, "The professional must exist");

		List<Payroll> payrolls = payrollRepo.findByProfessionalGroupName(name);

		return DtoAssembler.toSummaryDtoList(payrolls);
	}

}
