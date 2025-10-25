package uo.ri.cws.application.service.profesionalGroup;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.professionalgroup.impl.ProfessionalGroupRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindProfessionalGroupByName
    implements Command<Optional<ProfessionalGroupDto>> {

    String name;
    ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();

    public FindProfessionalGroupByName(String name) {
	ArgumentChecks.isNotBlank(name);
	this.name = name;
    }

    @Override
    public Optional<ProfessionalGroupDto> execute() throws BusinessException {
	Optional<ProfessionalGroupRecord> omr = pgg.findByGroupName(name);
	Optional<ProfessionalGroupDto> maybeDto = omr.map(
	    ProfessionalGroupRecordAssembler::toDto);
	return maybeDto;
    }

}
