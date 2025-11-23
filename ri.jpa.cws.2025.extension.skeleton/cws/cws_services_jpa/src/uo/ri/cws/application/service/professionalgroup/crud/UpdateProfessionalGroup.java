package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateProfessionalGroup implements Command<Void> {

	ProfessionalGroupDto dto;

	private ProfessionalGroupRepository repo = Factories.repository
		.forProfessionalGroup();

	public UpdateProfessionalGroup(ProfessionalGroupDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isTrue(dto.trienniumPayment >= 0);
		ArgumentChecks.isTrue(dto.productivityRate >= 0);
		ArgumentChecks.isNotBlank(dto.name);

		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<ProfessionalGroup> optionalProfessionalGroup = repo
			.findByName(dto.name);
		BusinessChecks.exists(optionalProfessionalGroup,
			"The professional group does not exists");

		ProfessionalGroup professionalGroup = optionalProfessionalGroup.get();
		BusinessChecks.hasVersion(dto.version, professionalGroup.getVersion());

		professionalGroup.setProductivityRate(dto.productivityRate);
		professionalGroup.setName(dto.name);
		professionalGroup.setTrieniumPayment(dto.trienniumPayment);

		return null;
	}

}
