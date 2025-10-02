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
    private MechanicDto dto;

    private static final String TMECHANICS_ADD = "insert into TMechanics" + "(id, nif, name, surname, version, " + "createdAt, updatedAt, entityState) "
	+ "values (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String TMECHANICS_CHECK = "select id from Tmechanics where nif=?";

    public AddMechanic(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.nif);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isNotBlank(dto.surname);
	// No commrpobamos ID y version ya que son datos que generamos nosotros mismos
	dto.id = UUID.randomUUID()
		     .toString();
	dto.version = 1;
	this.dto = dto;
    }

    public MechanicDto execute() throws BusinessException {
	checkIfMechanicAlreadyExists();
	return executeQuery();
    }

    private void checkIfMechanicAlreadyExists() throws BusinessException {
	try (Connection c = Jdbc.createThreadConnection();) {
	    try (PreparedStatement pst = c.prepareStatement(TMECHANICS_CHECK)) {
		pst.setString(1, dto.nif);

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			String id = rs.getString(1);
			if (id.equals(dto.id)) {
			    throw new BusinessException();
			}
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    private MechanicDto executeQuery() {
	// Process
	try (Connection c = Jdbc.createThreadConnection();) {
	    try (PreparedStatement pst = c.prepareStatement(TMECHANICS_ADD)) {
		pst.setString(1, dto.id);
		pst.setString(2, dto.nif);
		pst.setString(3, dto.name);
		pst.setString(4, dto.surname);
		pst.setLong(5, dto.version);
		pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
		pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		pst.setString(8, "ENABLED");

		pst.executeUpdate();

	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
	return dto;
    }
}
