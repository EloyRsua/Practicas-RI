package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProfessionalGroup implements Command<Void> {

	private String name;

	private ProfessionalGroupRepository repo = Factories.repository
		.forProfessionalGroup();

	public DeleteProfessionalGroup(String name) {
		ArgumentChecks.isNotBlank(name);

		this.name = name;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<ProfessionalGroup> opProfessionalGroup = repo.findByName(name);
		BusinessChecks.exists(opProfessionalGroup,
			"The professional must exist");

		ProfessionalGroup professionalGroup = opProfessionalGroup.get();
		BusinessChecks.isTrue(professionalGroup.getContracts()
			.isEmpty(), "The professional group cannot have contracts");

		repo.remove(professionalGroup);

		return null;
	}

}
