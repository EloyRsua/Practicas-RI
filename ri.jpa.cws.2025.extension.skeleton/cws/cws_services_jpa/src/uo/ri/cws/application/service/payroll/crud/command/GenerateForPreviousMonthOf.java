package uo.ri.cws.application.service.payroll.crud.command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class GenerateForPreviousMonthOf implements Command<List<PayrollDto>> {

	private LocalDate present;

	private PayrollRepository payrollRepo = Factories.repository.forPayroll();
	private ContractRepository contractRepo = Factories.repository
		.forContract();

	public GenerateForPreviousMonthOf(LocalDate present) {
		ArgumentChecks.isNotNull(present);

		this.present = present;
	}

	@Override
	public List<PayrollDto> execute() throws BusinessException {
		List<Payroll> generatedPayrolls = new ArrayList<>();

		// Calcular el último día del mes anterior
		LocalDate endOfPreviousMonth = present.minusMonths(1)
			.withDayOfMonth(present.minusMonths(1)
				.lengthOfMonth());

		// Comprobar si ya existen nóminas para esa fecha
		List<Payroll> existingPayrolls = payrollRepo
			.findByDate(endOfPreviousMonth);
		if (!existingPayrolls.isEmpty()) {
			// Convertir la lista vacía a DTOs (no generar nuevas nóminas)
			return DtoAssembler.toDtoList(generatedPayrolls);
		}

		// Obtener todos los contratos activos durante este mes
		List<Contract> activeContracts = contractRepo
			.findAllInForceThisMonth(present);

		// Generar las nóminas y guardarlas
		for (Contract contract : activeContracts) {
			Payroll payroll = new Payroll(contract, endOfPreviousMonth);
			payrollRepo.add(payroll);
			generatedPayrolls.add(payroll);
		}

		// Devolver la lista de nóminas en formato DTO
		return DtoAssembler.toDtoList(generatedPayrolls);
	}

}
