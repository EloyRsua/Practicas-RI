package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private String mechanicId;
    private MechanicRepository repo = Factories.repository.forMechanic();

    public DeleteMechanic(String mechanicId) {
	this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<Mechanic> optional = repo.findById(mechanicId);
	BusinessChecks.exists(optional, "The mechanic does not exist");

	Mechanic mechanic = optional.get();
	BusinessChecks.isTrue(mechanic.getAssigned()
				      .isEmpty(),
	    "The mechanic has workotders");
	BusinessChecks.isTrue(mechanic.getInterventions()
				      .isEmpty(),
	    "The mechanic has interventions");

	repo.remove(mechanic);
	return null;
    }

}
