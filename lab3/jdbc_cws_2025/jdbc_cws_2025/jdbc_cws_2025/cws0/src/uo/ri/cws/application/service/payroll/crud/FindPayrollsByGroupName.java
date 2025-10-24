package uo.ri.cws.application.service.payroll.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayrollsByGroupName
    implements Command<List<PayrollSummaryDto>> {

    private String name;
    PayrollGateway pg = Factories.persistence.forPayroll();
    ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();

    public FindPayrollsByGroupName(String name) {
	ArgumentChecks.isNotBlank(name);
	this.name = name;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
	Optional<ProfessionalGroupRecord> ogr = pgg.findByGroupName(name);
	BusinessChecks.exists(ogr,
	    "No professional group exists with that name");

	List<PayrollRecord> rlist = pg.findPayrollsByProfessionalGroup(name);
	List<PayrollSummaryDto> slist = new ArrayList<PayrollSummaryDto>();
	for (PayrollRecord pr : rlist) {
	    PayrollSummaryDto sdto = PayrollAssembler.toSummaryDto(pr);
	    slist.add(sdto);
	}
	return slist;
    }

}
