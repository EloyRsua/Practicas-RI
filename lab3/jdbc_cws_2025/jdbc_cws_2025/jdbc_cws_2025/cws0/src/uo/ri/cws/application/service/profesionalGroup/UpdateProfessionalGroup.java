package uo.ri.cws.application.service.profesionalGroup;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.professionalgroup.impl.ProfessionalGroupRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateProfessionalGroup implements Command<Void> {

    ProfessionalGroupGateway pg = Factories.persistence.forProfessionalGroup();
    private ProfessionalGroupDto dto;

    public UpdateProfessionalGroup(ProfessionalGroupDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isTrue(dto.trienniumPayment > 0);
	ArgumentChecks.isTrue(dto.productivityRate > 0);

	this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProfessionalGroupRecord> pr = pg.findByGroupName(dto.name);
	BusinessChecks.exists(pr, "The professional group does not exist");
	BusinessChecks.hasVersion(dto.version, pr.get().version);

	pg.update(ProfessionalGroupRecordAssembler.toRecord(dto));

	return null;
    }

}
