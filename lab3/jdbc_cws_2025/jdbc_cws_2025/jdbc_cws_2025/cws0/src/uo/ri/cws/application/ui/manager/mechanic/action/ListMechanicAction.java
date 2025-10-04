package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {
	// Get info
	String nif = Console.readString("nif");
	MechanicCrudService mcs = Factories.service.forMechanicCrudService();
	Optional<MechanicDto> opdto = mcs.findByNif(nif);
	if (!opdto.isPresent()) {
	    Console.println("\nNo mechanic found\n");
	} else if (opdto.isPresent()) {
	    MechanicDto dto = opdto.get();
	    Console.println("\nMechanic information \n");
	    Console.printf("\t%s %s %s %s %d\n", dto.id, dto.name, dto.surname,
		dto.nif, dto.version);

	}

    }
}