package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

    private MechanicDto dto;
    private MechanicRepository repo = Factories.repository.forMechanic();

    public UpdateMechanic(MechanicDto dto) {
	this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<Mechanic> om = repo.findById(dto.id);
	BusinessChecks.exists(om, "The mechanic does not exist");

	Mechanic m = om.get();
	m.setName(dto.name);
	m.setSurname(dto.surname);
	m.updatedNow();

	return null;
    }

}
