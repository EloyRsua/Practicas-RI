package uo.ri.cws.application.service.mechanic.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;

public class FindByNifMechanic implements Command<Optional<MechanicDto>> {
    private String nif;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public FindByNifMechanic(String nif) {
	ArgumentChecks.isNotBlank(nif);
	this.nif = nif;
    }

    @Override
    public Optional<MechanicDto> execute() {

	Optional<MechanicRecord> omr = mg.findByNif(nif);
	MechanicDto dto = MechanicDtoAssembler.toDto(omr.get());
	if (dto.equals(null)) {
	    return Optional.empty();
	} else {
	    Optional<MechanicDto> odto = Optional.of(dto);
	    return odto;
	}
    }
}
