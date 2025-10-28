package uo.ri.cws.application.service.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.util.exception.BusinessException;

public class ContractServiceImpl implements ContractCrudService {

    CommandExecutor executor = new CommandExecutor();

    @Override
    public ContractDto create(ContractDto c) throws BusinessException {
	// No asignado
	return null;
    }

    @Override
    public void update(ContractDto dto) throws BusinessException {
	// No asignado

    }

    @Override
    public void delete(String id) throws BusinessException {
	// No asignado

    }

    @Override
    public void terminate(String contractId) throws BusinessException {
	// No asignado

    }

    @Override
    public Optional<ContractDto> findById(String id) throws BusinessException {
	// No asignado
	return Optional.empty();
    }

    @Override
    public List<ContractSummaryDto> findByMechanicNif(String nif)
	throws BusinessException {
	// No asignado
	return null;
    }

    @Override
    public List<ContractDto> findInforceContracts() throws BusinessException {
	return executor.execute(new FindInForceContracts());
    }

    @Override
    public List<ContractSummaryDto> findAll() throws BusinessException {
	// No asignado
	return null;
    }

}
