package uo.ri.cws.application.service.mechanic.crud;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicGateway mg = Factories.persistence.forMechanic();

    private final MechanicDto dto;

    public AddMechanic(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.nif);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isNotBlank(dto.surname);

	// ID y version generados internamente
	dto.id = UUID.randomUUID()
		     .toString();
	dto.version = 1;

	this.dto = dto;
    }

    @Override
    public MechanicDto execute() throws BusinessException {
	Optional<MechanicRecord> om = mg.findByNif(dto.nif);
	BusinessChecks.doesNotExist(om, "The mechanic already exists");
	MechanicRecord m = MechanicDtoAssembler.toRecord(dto);
	mg.add(m);
	return dto;
    }

}
