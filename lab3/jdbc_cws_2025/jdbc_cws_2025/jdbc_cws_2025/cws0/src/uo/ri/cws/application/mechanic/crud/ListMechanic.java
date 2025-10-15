package uo.ri.cws.application.mechanic.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListMechanic implements Command<Optional<MechanicDto>> {

    private String nif;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public ListMechanic(String nif) {
	ArgumentChecks.isNotBlank(nif);

	this.nif = nif;
    }

    @Override
    public Optional<MechanicDto> execute() throws BusinessException {
	Optional<MechanicRecord> om = mg.findByNif(nif);
	MechanicDto dto = MechanicDtoAssembler.toDto(om.get());
	if (dto.equals(null)) {
	    return Optional.empty();
	} else {
	    Optional<MechanicDto> odto = Optional.of(dto);
	    return odto;
	}
    }

}
