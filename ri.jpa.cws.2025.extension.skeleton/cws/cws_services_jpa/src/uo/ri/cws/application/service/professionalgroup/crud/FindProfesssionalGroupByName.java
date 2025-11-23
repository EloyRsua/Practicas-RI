package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindProfesssionalGroupByName
	implements Command<Optional<ProfessionalGroupDto>> {

	ProfessionalGroupRepository repo = Factories.repository
		.forProfessionalGroup();

	String name;

	public FindProfesssionalGroupByName(String id) {
		ArgumentChecks.isNotBlank(id);
		this.name = id;
	}

	@Override
	public Optional<ProfessionalGroupDto> execute() throws BusinessException {
		Optional<ProfessionalGroup> optionalProfessionalGroup = repo
			.findByName(name);
		return optionalProfessionalGroup
			.map(professionalGroup -> DtoAssembler.toDto(professionalGroup));
	}

}
