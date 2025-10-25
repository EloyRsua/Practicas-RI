package uo.ri.cws.application.service.profesionalGroup;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.professionalgroup.impl.ProfessionalGroupRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddProfessionalGroup implements Command<ProfessionalGroupDto> {

    private ProfessionalGroupDto dto;
    private ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();

    public AddProfessionalGroup(ProfessionalGroupDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isTrue(dto.trienniumPayment > 0);
	ArgumentChecks.isTrue(dto.productivityRate > 0);

	dto.id = UUID.randomUUID()
		     .toString();
	dto.version = 1;

	this.dto = dto;
    }

    @Override
    public ProfessionalGroupDto execute() throws BusinessException {
	Optional<ProfessionalGroupRecord> op = pgg.findByGroupName(dto.name);
	BusinessChecks.doesNotExist(op,
	    "Professionalgroup with thta name already exists");
	pgg.add(ProfessionalGroupRecordAssembler.toRecord(dto));
	return dto;
    }

}
