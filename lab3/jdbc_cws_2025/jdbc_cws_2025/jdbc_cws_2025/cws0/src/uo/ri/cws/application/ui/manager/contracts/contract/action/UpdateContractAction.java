package uo.ri.cws.application.ui.manager.contracts.contract.action;

import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateContractAction implements Action {

    @Override
    public void execute() throws BusinessException {

	Console.readString("Contract id");

	// Find contract by id
	throw new UnsupportedOperationException("Not yet implemented");

//		Console.println("Contract updated");
    }
}