package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.util.exception.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {

    private CommandExecutor executuor = new CommandExecutor();

    @Override
    public MechanicDto create(MechanicDto dto) throws BusinessException {
	return executuor.execute(new AddMechanic(dto));
    }

    @Override
    public void delete(String mechanicId) throws BusinessException {
	executuor.execute(new DeleteMechanic(mechanicId));

    }

    @Override
    public void update(MechanicDto dto) throws BusinessException {
	executuor.execute(new UpdateMechanic(dto));
    }

    @Override
    public Optional<MechanicDto> findById(String id) throws BusinessException {
	return executuor.execute(new FindByIdMechanic(id));
    }

    @Override
    public Optional<MechanicDto> findByNif(String nif)
	throws BusinessException {
	return executuor.execute(new FindByNifMechanic(nif));
    }

    @Override
    public List<MechanicDto> findAll() throws BusinessException {
	return executuor.execute(new ListAllMechanics());
    }

}
