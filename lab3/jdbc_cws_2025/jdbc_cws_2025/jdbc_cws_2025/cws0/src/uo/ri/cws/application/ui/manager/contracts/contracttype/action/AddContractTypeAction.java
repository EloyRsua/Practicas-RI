package uo.ri.cws.application.ui.manager.contracts.contracttype.action;

import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddContractTypeAction implements Action {

    @Override
    public void execute() throws BusinessException {
	Console.readString("Contract type name");
	Console.readDouble("Compensation days");

	throw new UnsupportedOperationException("Not yet implemented");

	// Console.println("Contract type registered");
    }

}