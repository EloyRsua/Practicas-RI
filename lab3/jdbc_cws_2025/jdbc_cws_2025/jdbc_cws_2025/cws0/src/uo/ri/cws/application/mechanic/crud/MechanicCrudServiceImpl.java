package uo.ri.cws.application.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.util.exception.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {

    private CommandExecutor exectuor = new CommandExecutor();

    @Override
    public MechanicDto create(MechanicDto dto) throws BusinessException {
	AddMechanic am = new AddMechanic(dto);
	return am.execute();
    }

    @Override
    public void delete(String mechanicId) throws BusinessException {
	DeleteMechanic dm = new DeleteMechanic(mechanicId);
	dm.execute();

    }

    @Override
    public void update(MechanicDto dto) throws BusinessException {
	exectuor.execute(new UpdateMechanic(dto));
    }

    @Override
    public Optional<MechanicDto> findById(String id) throws BusinessException {
	FindByIdMechanic fbi = new FindByIdMechanic(id);
	return fbi.execute();
    }

    @Override
    public Optional<MechanicDto> findByNif(String nif) throws BusinessException {
	ListMechanic lm = new ListMechanic(nif);
	return lm.execute();
    }

    @Override
    public List<MechanicDto> findAll() throws BusinessException {
	ListAllMechanics lam = new ListAllMechanics();
	return lam.execute();
    }

}
