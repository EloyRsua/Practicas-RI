package uo.ri.cws.application.mechanic.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

    private MechanicDto dto;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public UpdateMechanic(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isNotBlank(dto.surname);
	ArgumentChecks.isNotBlank(dto.id);
	ArgumentChecks.isNotBlank(dto.nif);
	this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {

	Optional<MechanicRecord> om = mg.findById(dto.id);
	BusinessChecks.exists(om, "The mechanic does not exist");
	// Checkear la version en todos los updates !!!
	BusinessChecks.hasVersion(dto.version, om.get().version);
	MechanicRecord m = new MechanicRecord();
	mg.update(m); // MAPEAR DE DTO A RECORD MechanicDtoAsembler un metodo toDto y otro toRecord
	return null;
    }

}
