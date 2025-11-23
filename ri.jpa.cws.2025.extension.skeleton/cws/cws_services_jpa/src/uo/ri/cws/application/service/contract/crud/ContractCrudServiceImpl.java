package uo.ri.cws.application.service.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.crud.command.FindAllInForceMechanics;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;

public class ContractCrudServiceImpl implements ContractCrudService {

	private CommandExecutor executor = Factories.executor.forExecutor();

	@Override
	public ContractDto create(ContractDto c) throws BusinessException {
		// NO ASIGNADO
		return null;
	}

	@Override
	public void update(ContractDto dto) throws BusinessException {
		// NO ASIGNADO
	}

	@Override
	public void delete(String id) throws BusinessException {
		// NO ASIGNADO
	}

	@Override
	public void terminate(String contractId) throws BusinessException {
		// NO ASIGNADO
	}

	@Override
	public Optional<ContractDto> findById(String id) throws BusinessException {
		// NO ASIGNADO
		return Optional.empty();
	}

	@Override
	public List<ContractSummaryDto> findByMechanicNif(String nif)
		throws BusinessException {
		// NO ASIGNADO
		return null;
	}

	@Override
	public List<ContractDto> findInforceContracts() throws BusinessException {
		return executor.execute(new FindAllInForceMechanics());
	}

	@Override
	public List<ContractSummaryDto> findAll() throws BusinessException {
		// NO ASIGNADO
		return null;
	}

}
