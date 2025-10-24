package uo.ri.cws.application.service.payroll.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayrollById implements Command<Optional<PayrollDto>> {

    private String id;

    PayrollGateway pg = Factories.persistence.forPayroll();

    public FindPayrollById(String id) {
	ArgumentChecks.isNotBlank(id);
	this.id = id;
    }

    @Override
    public Optional<PayrollDto> execute() throws BusinessException {
	Optional<PayrollRecord> opr = pg.findById(id);
	Optional<PayrollDto> maybeDto = opr.map(PayrollAssembler::toDto);
	return maybeDto;
    }

}
