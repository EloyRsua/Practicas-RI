package uo.ri.cws.application.service.payroll.crud;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.util.exception.BusinessException;

public class FindAllPayrolls implements Command<List<PayrollSummaryDto>> {

    PayrollGateway pg = Factories.persistence.forPayroll();

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
	List<PayrollSummaryDto> slist = new ArrayList<PayrollSummaryDto>();
	for (PayrollRecord pr : pg.findAll()) {
	    slist.add(PayrollAssembler.toSummaryDto(pr));
	}
	return slist;

    }

}
