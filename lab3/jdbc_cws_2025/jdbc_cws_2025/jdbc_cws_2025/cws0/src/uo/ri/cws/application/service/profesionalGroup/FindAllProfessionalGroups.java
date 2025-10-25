package uo.ri.cws.application.service.profesionalGroup;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.professionalgroup.impl.ProfessionalGroupRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.exception.BusinessException;

public class FindAllProfessionalGroups
    implements Command<List<ProfessionalGroupDto>> {

    ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();

    @Override
    public List<ProfessionalGroupDto> execute() throws BusinessException {
	List<ProfessionalGroupDto> dtos = new ArrayList<ProfessionalGroupDto>();
	List<ProfessionalGroupRecord> records = pgg.findAll();
	for (ProfessionalGroupRecord r : records) {
	    dtos.add(ProfessionalGroupRecordAssembler.toDto(r));
	}
	return dtos;
    }

}
