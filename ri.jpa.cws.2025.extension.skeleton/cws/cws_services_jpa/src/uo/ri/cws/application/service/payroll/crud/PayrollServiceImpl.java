package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.crud.command.DeleteLastMonthPayrollForAMechanicId;
import uo.ri.cws.application.service.payroll.crud.command.DeleteLastMonthPaytoll;
import uo.ri.cws.application.service.payroll.crud.command.FindAllPayrollsSum;
import uo.ri.cws.application.service.payroll.crud.command.FindAllPayrollsSumByMechanic;
import uo.ri.cws.application.service.payroll.crud.command.FindPayrollById;
import uo.ri.cws.application.service.payroll.crud.command.FindPayrollsByProfGroup;
import uo.ri.cws.application.service.payroll.crud.command.GenerateForPreviousMonthOf;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;

public class PayrollServiceImpl implements PayrollService {

	private CommandExecutor executor = Factories.executor.forExecutor();

	@Override
	public List<PayrollDto> generateForPreviousMonth()
		throws BusinessException {
		// NO ASIGNADO
		return null;
	}

	@Override
	public List<PayrollDto> generateForPreviousMonthOf(LocalDate present)
		throws BusinessException {
		return executor.execute(new GenerateForPreviousMonthOf(present));
	}

	@Override
	public void deleteLastGeneratedOfMechanicId(String mechanicId)
		throws BusinessException {
		executor.execute(new DeleteLastMonthPayrollForAMechanicId(mechanicId));

	}

	@Override
	public int deleteLastGenerated() throws BusinessException {
		return executor.execute(new DeleteLastMonthPaytoll());
	}

	@Override
	public Optional<PayrollDto> findById(String id) throws BusinessException {

		return executor.execute(new FindPayrollById(id));
	}

	@Override
	public List<PayrollSummaryDto> findAllSummarized()
		throws BusinessException {
		return executor.execute(new FindAllPayrollsSum());
	}

	@Override
	public List<PayrollSummaryDto> findSummarizedByMechanicId(String id)
		throws BusinessException {
		return executor.execute(new FindAllPayrollsSumByMechanic(id));
	}

	@Override
	public List<PayrollSummaryDto> findSummarizedByProfessionalGroupName(
		String name) throws BusinessException {
		return executor.execute(new FindPayrollsByProfGroup(name));
	}

}
