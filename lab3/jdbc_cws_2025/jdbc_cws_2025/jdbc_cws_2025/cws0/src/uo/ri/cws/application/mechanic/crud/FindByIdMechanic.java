package uo.ri.cws.application.mechanic.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.jdbc.Jdbc;

public class FindByIdMechanic {
    private static final String SQL_FIND_MECHANIC_BY_ID = "SELECT id, version, nif, name, surname FROM TMechanics WHERE id = ?";
    private String id;

    public FindByIdMechanic(String id) {
	ArgumentChecks.isNotBlank(id);
	this.id = id;
    }

    public Optional<MechanicDto> execute() {
	try (Connection c = Jdbc.createThreadConnection()) {
	    try (PreparedStatement pst = c.prepareStatement(
		SQL_FIND_MECHANIC_BY_ID)) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (!rs.next()) {
			return Optional.empty();
		    }

		    MechanicDto dto = new MechanicDto();
		    dto.id = rs.getString("id");
		    dto.version = rs.getLong("version");
		    dto.nif = rs.getString("nif");
		    dto.name = rs.getString("name");
		    dto.surname = rs.getString("surname");
		    return Optional.of(dto);
		}
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }
}
