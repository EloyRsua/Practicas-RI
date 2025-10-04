package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {
	MechanicDto dto = new MechanicDto();

	dto.id = Console.readString("Type mechanic id to update");
	dto.name = Console.readString("Name");
	dto.surname = Console.readString("Surname");

	MechanicCrudService mcs = Factories.service.forMechanicCrudService();
	mcs.update(dto);
	Console.println("\nMechanic updated successfully\n");
    }
}
