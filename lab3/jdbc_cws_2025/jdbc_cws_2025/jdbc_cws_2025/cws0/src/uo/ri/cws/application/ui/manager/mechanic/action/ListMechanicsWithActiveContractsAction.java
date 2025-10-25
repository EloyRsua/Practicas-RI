package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListMechanicsWithActiveContractsAction implements Action {

    @Override
    public void execute() throws BusinessException {
	Console.println("\nList of mechanics\n");

	MechanicCrudService mcs = Factories.service.forMechanicCrudService();
	ContractCrudService ccs = Factories.service.forContractCrudService();
	List<ContractDto> rlist = ccs.findInforceContracts();

	List<MechanicDto> mechanics = new ArrayList<MechanicDto>();
	for (ContractDto dto : rlist) {
	    MechanicDto mechanic = mcs.findById(dto.mechanic.id)
				      .get();
	    mechanics.add(mechanic);
	}
	for (MechanicDto dto : mechanics) {
	    Printer.printMechanic(dto);
	}
    }

}
