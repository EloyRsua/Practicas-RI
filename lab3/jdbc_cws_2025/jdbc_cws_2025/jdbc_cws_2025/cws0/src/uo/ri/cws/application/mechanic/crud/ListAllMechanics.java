package uo.ri.cws.application.mechanic.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.jdbc.Jdbc;

public class ListAllMechanics {

    private static final String TMECHANICS_FINDALL = "SELECT ID, NAME, " + "SURNAME, NIF, VERSION FROM TMECHANICS";

    public List<MechanicDto> execute() {

	// Process
	try (Connection c = Jdbc.createThreadConnection()) {
	    try (PreparedStatement pst = c.prepareStatement(TMECHANICS_FINDALL)) {
		try (ResultSet rs = pst.executeQuery();) {
		    while (rs.next()) {
			// Console.printf("\t%s %s %s %s %d\n", rs.getString(1), rs.getString(2),
			// rs.getString(3), rs.getString(4), rs.getLong(5));

		    }
		}
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }
}
