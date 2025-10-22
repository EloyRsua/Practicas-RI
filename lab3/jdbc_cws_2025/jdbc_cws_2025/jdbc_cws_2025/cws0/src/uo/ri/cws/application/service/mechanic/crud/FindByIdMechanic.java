package uo.ri.cws.application.service.mechanic.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;

public class FindByIdMechanic implements Command<Optional<MechanicDto>> {
    private String id;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public FindByIdMechanic(String id) {
	ArgumentChecks.isNotBlank(id);
	this.id = id;
    }

    @Override
    public Optional<MechanicDto> execute() {

	Optional<MechanicRecord> omr = mg.findById(id);
	MechanicDto dto = MechanicDtoAssembler.toDto(omr.get());
	if (dto.equals(null)) {
	    return Optional.empty();
	} else {
	    Optional<MechanicDto> odto = Optional.of(dto);
	    return odto;
	}
    }
}
