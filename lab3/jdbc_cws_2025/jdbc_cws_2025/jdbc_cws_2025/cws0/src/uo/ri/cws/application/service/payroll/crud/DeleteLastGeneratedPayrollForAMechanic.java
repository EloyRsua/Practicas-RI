package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteLastGeneratedPayrollForAMechanic implements Command<Void> {

    String mechanicId;

    MechanicGateway mg = Factories.persistence.forMechanic();
    PayrollGateway pg = Factories.persistence.forPayroll();

    public DeleteLastGeneratedPayrollForAMechanic(String mechanicId) {
	ArgumentChecks.isNotBlank(mechanicId);

	this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<MechanicRecord> omr = mg.findById(mechanicId);
	BusinessChecks.exists(omr, "The given mechanic does not exist");

	List<PayrollRecord> list = pg.findPayrollsByLocalDateAndMechanicId(
	    mechanicId, LocalDate.now()
				 .minusMonths(1));
	for (PayrollRecord pr : list) {
	    pg.remove(pr.id);
	}
	return null;
    }

}
