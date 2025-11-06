package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.cws.application.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

	private String mechanicId;

	public DeleteMechanic(String mechanicId) {
		this.mechanicId = mechanicId;
	}

	public Void execute() throws BusinessException {

		return null;
	}

}
