package uo.ri.cws.application.service.payroll.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayrollById implements Command<Optional<PayrollDto>> {

	private String id;

	private PayrollRepository repo = Factories.repository.forPayroll();

	public FindPayrollById(String id) {
		ArgumentChecks.isNotBlank(id);

		this.id = id;
	}

	@Override
	public Optional<PayrollDto> execute() throws BusinessException {

		return repo.findById(id)
			.map(payroll -> DtoAssembler.toDto(payroll));
	}

}
