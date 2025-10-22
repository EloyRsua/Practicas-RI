package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracts.ContractGateway;
import uo.ri.cws.application.persistence.contracts.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private ContractGateway mc = Factories.persistence.forContract();
    private WorkOrderGateway mw = Factories.persistence.forWorkOrder();
    private InterventionGateway mi = Factories.persistence.forIntervention();

    private final String idMechanic;

    public DeleteMechanic(String mechanicId) {
	ArgumentChecks.isNotBlank(mechanicId);
	this.idMechanic = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<MechanicRecord> om = mg.findById(idMechanic);
	BusinessChecks.exists(om, "The mechanic does not exist");
	List<WorkOrderRecord> listWo = mw.findActiveWorkOrderByMechanicId(
	    idMechanic);
	BusinessChecks.isEmpty(listWo,
	    "There are workorders assigned to this mechanic");
	List<ContractRecord> listCo = mc.findActiveContractByMechanicId(
	    idMechanic);
	BusinessChecks.isEmpty(listCo,
	    "There are active contracts  assigned to this mechanic");
	List<InterventionRecord> listInt = mi.findInterventionsByMechanicId(
	    idMechanic);
	BusinessChecks.isEmpty(listInt,
	    "There are interventins assigned to this mechanic");

	mg.remove(idMechanic);
	return null;
    }

}
