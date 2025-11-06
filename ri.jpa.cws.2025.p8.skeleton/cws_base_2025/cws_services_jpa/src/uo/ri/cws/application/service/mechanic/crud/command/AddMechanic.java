package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicDto dto;

    private MechanicRepository repo = Factories.repository.forMechanic();

    public AddMechanic(MechanicDto dto) {
	this.dto = dto;
    }

    @Override
    public MechanicDto execute() throws BusinessException {
	// VALIDACIONES

	Mechanic mechanic = new Mechanic(dto.nif, dto.surname, dto.name);
	repo.add(mechanic);

	return DtoAssembler.toDto(mechanic);
    }

}
