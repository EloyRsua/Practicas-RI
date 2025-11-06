package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class FindAllMechanics implements Command<List<MechanicDto>> {

    private MechanicRepository repo = Factories.repository.forMechanic();

    @Override
    public List<MechanicDto> execute() {
	// Validaciones
	List<Mechanic> mechanics = repo.findAll();
	return uo.ri.cws.application.service.mechanic.crud.DtoAssembler.toMechanicDtoList(mechanics);
    }

}
