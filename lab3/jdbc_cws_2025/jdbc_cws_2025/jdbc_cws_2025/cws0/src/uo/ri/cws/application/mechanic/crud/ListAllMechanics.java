package uo.ri.cws.application.mechanic.crud;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class ListAllMechanics implements Command<List<MechanicDto>> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private List<MechanicDto> listOfMechanics;

    @Override
    public List<MechanicDto> execute() {
	List<MechanicRecord> mrs = mg.findAll();
	for (MechanicRecord mr : mrs) {
	    listOfMechanics.add(MechanicDtoAssembler.toDto(mr));
	}
	return listOfMechanics;

    }
}
