package uo.ri.cws.application.service.contract.crud;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracts.ContractGateway;
import uo.ri.cws.application.persistence.contracts.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracts.impl.ContractRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.util.exception.BusinessException;

public class FindInForceContracts implements Command<List<ContractDto>> {

    ContractGateway cg = Factories.persistence.forContract();

    @Override
    public List<ContractDto> execute() throws BusinessException {
	List<ContractRecord> list = cg.findContractsInForce();
	List<ContractDto> rlist = new ArrayList<ContractDto>();

	for (ContractRecord r : list) {
	    ContractDto dto = ContractRecordAssembler.toDto(r);
	    rlist.add(dto);
	}
	return rlist;

    }

}
