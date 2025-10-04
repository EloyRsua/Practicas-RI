package uo.ri.cws.application.mechanic.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class AddMechanic {

    private static final String SQL_ADD_MECHANIC = "INSERT INTO TMechanics (id, nif, name, surname, version, createdAt, updatedAt, entityState) "
	+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CHECK_MECHANIC_EXISTS = "SELECT nif FROM TMECHANICS WHERE nif = ?";

    private final MechanicDto dto;

    public AddMechanic(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.nif);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isNotBlank(dto.surname);

	// ID y version generados internamente
	dto.id = UUID.randomUUID()
		     .toString();
	dto.version = 1;

	this.dto = dto;
    }

    public MechanicDto execute() throws BusinessException {
	try (Connection c = Jdbc.createThreadConnection()) {
	    checkIfMechanicAlreadyExists(c);
	    return executeQuery(c);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    private void checkIfMechanicAlreadyExists(Connection c)
	throws SQLException, BusinessException {
	try (PreparedStatement pst = c.prepareStatement(
	    SQL_CHECK_MECHANIC_EXISTS)) {
	    pst.setString(1, dto.nif);

	    try (ResultSet rs = pst.executeQuery()) {
		if (rs.next()) {
		    throw new BusinessException(String.format(
			"Ya existe un mecánico con el NIF %s", dto.nif));
		}
	    }
	}
    }

    private MechanicDto executeQuery(Connection c) throws SQLException {
	Timestamp now = new Timestamp(System.currentTimeMillis());

	try (PreparedStatement pst = c.prepareStatement(SQL_ADD_MECHANIC)) {
	    pst.setString(1, dto.id);
	    pst.setString(2, dto.nif);
	    pst.setString(3, dto.name);
	    pst.setString(4, dto.surname);
	    pst.setLong(5, dto.version);
	    pst.setTimestamp(6, now);
	    pst.setTimestamp(7, now);
	    pst.setString(8, "ENABLED");

	    pst.executeUpdate();
	}

	return dto;
    }
}
