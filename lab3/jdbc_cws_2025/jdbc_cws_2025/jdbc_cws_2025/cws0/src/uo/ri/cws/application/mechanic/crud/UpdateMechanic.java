package uo.ri.cws.application.mechanic.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class UpdateMechanic {
    private static final String SQL_UPDATE_MECHANIC = "update TMechanics set name = ?, surname = ?, version = version + 1, updatedat = ? "
	+ "where id = ? and version = ?";

    private MechanicDto dto;

    public UpdateMechanic(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isNotBlank(dto.surname);
	ArgumentChecks.isNotBlank(dto.id);
	ArgumentChecks.isNotBlank(dto.nif);
	this.dto = dto;
    }

    public void execute() throws BusinessException {

	updateMechanic(dto.id, dto.name, dto.surname, dto.version);

    }

    private void updateMechanic(String id, String name, String surname,
	long version) throws BusinessException {

	try (Connection c = Jdbc.createThreadConnection()) {
	    try (PreparedStatement pst = c.prepareStatement(
		SQL_UPDATE_MECHANIC)) {
		pst.setString(1, name);
		pst.setString(2, surname);
		pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		pst.setString(4, id);
		pst.setLong(5, version);

		int updated = pst.executeUpdate();
		if (updated == 0) {
		    throw new BusinessException(
			"Mechanic was updated by another transaction");
		}
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

}
