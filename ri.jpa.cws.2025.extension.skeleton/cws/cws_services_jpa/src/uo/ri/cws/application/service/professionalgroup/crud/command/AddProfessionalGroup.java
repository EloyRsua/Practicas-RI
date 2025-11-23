package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddProfessionalGroup implements Command<ProfessionalGroupDto> {
	private ProfessionalGroupDto dto;

	private ProfessionalGroupRepository repo = Factories.repository
		.forProfessionalGroup();

	public AddProfessionalGroup(ProfessionalGroupDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isTrue(dto.trienniumPayment >= 0);
		ArgumentChecks.isTrue(dto.productivityRate >= 0);
		ArgumentChecks.isNotBlank(dto.name);

		this.dto = dto;

	}

	@Override
	public ProfessionalGroupDto execute() throws BusinessException {
		Optional<ProfessionalGroup> opprofesionalGroup = repo
			.findByName(dto.name);
		BusinessChecks.doesNotExist(opprofesionalGroup,
			"The professionalGroup already exists");

		ProfessionalGroup pg = new ProfessionalGroup(dto.name,
			dto.trienniumPayment, dto.productivityRate);
		repo.add(pg);
		return DtoAssembler.toDto(pg);
	}

}
