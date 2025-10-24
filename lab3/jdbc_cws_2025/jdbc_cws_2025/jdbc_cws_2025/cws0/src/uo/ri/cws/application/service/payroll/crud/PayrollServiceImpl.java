package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.util.exception.BusinessException;

public class PayrollServiceImpl implements PayrollService {

    private CommandExecutor executor = new CommandExecutor();

    @Override
    public List<PayrollDto> generateForPreviousMonth()
	throws BusinessException {
	return null;
    }

    @Override
    public List<PayrollDto> generateForPreviousMonthOf(LocalDate present)
	throws BusinessException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void deleteLastGeneratedOfMechanicId(String mechanicId)
	throws BusinessException {
	executor.execute(
	    new DeleteLastGeneratedPayrollForAMechanic(mechanicId));
    }

    @Override
    public int deleteLastGenerated() throws BusinessException {
	return executor.execute(new DeleteLasGeneratedPayroll());
    }

    @Override
    public Optional<PayrollDto> findById(String id) throws BusinessException {
	return executor.execute(new FindPayrollById(id));
    }

    @Override
    public List<PayrollSummaryDto> findAllSummarized()
	throws BusinessException {

	return executor.execute(new FindAllPayrolls());
    }

    @Override
    public List<PayrollSummaryDto> findSummarizedByMechanicId(String id)
	throws BusinessException {

	return executor.execute(new FindPayrollByMechanicId(id));
    }

    @Override
    public List<PayrollSummaryDto> findSummarizedByProfessionalGroupName(
	String name) throws BusinessException {
	return executor.execute(new FindPayrollsByGroupName(name));
    }

}
