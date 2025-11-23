package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService;
import uo.ri.cws.application.service.professionalgroup.crud.command.AddProfessionalGroup;
import uo.ri.cws.application.service.professionalgroup.crud.command.DeleteProfessionalGroup;
import uo.ri.cws.application.service.professionalgroup.crud.command.FindAllProfessionalGroups;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;

public class ProfessionalGroupCrudServiceImpl
	implements ProfessionalGroupCrudService {

	private CommandExecutor executor = Factories.executor.forExecutor();

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
		return executor.execute(new FindProfesssionalGroupByName(id));

	}

	@Override
	public List<ProfessionalGroupDto> findAll() throws BusinessException {
		return executor.execute(new FindAllProfessionalGroups());
	}

}
