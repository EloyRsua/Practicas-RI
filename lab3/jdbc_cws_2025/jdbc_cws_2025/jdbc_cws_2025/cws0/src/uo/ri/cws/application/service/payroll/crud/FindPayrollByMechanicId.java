package uo.ri.cws.application.service.payroll.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayrollByMechanicId
    implements Command<List<PayrollSummaryDto>> {

    private String id;
    private PayrollGateway pg = Factories.persistence.forPayroll();
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public FindPayrollByMechanicId(String id) {
	ArgumentChecks.isNotBlank(id);
	this.id = id;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
	Optional<MechanicRecord> omr = mg.findById(id);
	BusinessChecks.exists(omr, "No mechanic exists with that id");

	List<PayrollRecord> rlist = pg.findPayrollsByMechanicId(id);
	List<PayrollSummaryDto> slist = new ArrayList<PayrollSummaryDto>();
	for (PayrollRecord pr : rlist) {
	    PayrollSummaryDto sdto = PayrollAssembler.toSummaryDto(pr);
	    slist.add(sdto);
	}
	return slist;
    }

}
