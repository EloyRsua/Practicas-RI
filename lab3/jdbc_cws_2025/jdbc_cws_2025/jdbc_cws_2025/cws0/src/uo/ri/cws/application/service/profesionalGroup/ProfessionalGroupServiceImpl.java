package uo.ri.cws.application.service.profesionalGroup;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService;
import uo.ri.util.exception.BusinessException;

public class ProfessionalGroupServiceImpl
    implements ProfessionalGroupCrudService {

    CommandExecutor executor = new CommandExecutor();

    @Override
    public ProfessionalGroupDto create(ProfessionalGroupDto dto)
	throws BusinessException {
	return executor.execute(new AddProfessionalGroup(dto));
    }

    @Override
    public void delete(String name) throws BusinessException {
	executor.execute(new DeleteProfessionalGroup(name));
    }

    @Override
    public void update(ProfessionalGroupDto dto) throws BusinessException {
	executor.execute(new UpdateProfessionalGroup(dto));

    }

    @Override
    public Optional<ProfessionalGroupDto> findByName(String id)
	throws BusinessException {
	return executor.execute(new FindProfessionalGroupByName(id));
    }

    @Override
    public List<ProfessionalGroupDto> findAll() throws BusinessException {
	return executor.execute(new FindAllProfessionalGroups());
    }

}
