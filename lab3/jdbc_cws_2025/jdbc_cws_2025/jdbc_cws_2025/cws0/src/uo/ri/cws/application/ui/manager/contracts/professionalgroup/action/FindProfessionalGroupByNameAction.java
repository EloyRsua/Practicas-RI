package uo.ri.cws.application.ui.manager.contracts.professionalgroup.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindProfessionalGroupByNameAction implements Action {

    ProfessionalGroupCrudService ps = Factories.service.forProfessionalGroupCrudService();

    @Override
    public void execute() throws BusinessException {
	String name = Console.readString("Professional group name");
	Optional<ProfessionalGroupDto> dto = ps.findByName(name);

	Printer.printProfessionalGroup(dto.get());
    }
}