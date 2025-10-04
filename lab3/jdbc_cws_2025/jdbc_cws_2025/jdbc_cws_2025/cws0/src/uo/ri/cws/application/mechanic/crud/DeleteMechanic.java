package uo.ri.cws.application.mechanic.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class DeleteMechanic {
    private static final String TMECHANICS_DELETE = "DELETE FROM TMECHANICS "
	+ "WHERE ID = ?";

    private static final String TMECHANICHS_EXIST = "SELECT* FROM TMECHANICS WHERE ID=?";
    private static final String TMECHANICHS_WO = "SELECT* FROM TMECHANICS WHERE ID=?";
    private String idMechanic;

    public DeleteMechanic(String mechanicId) {
	ArgumentChecks.isNotBlank(mechanicId);
	this.idMechanic = mechanicId;
    }

    public void execute() throws BusinessException {
	checkConditions();
	executeQuery();
    }

    private void executeQuery() {
	// Process
	try (Connection c = Jdbc.createThreadConnection();) {
	    try (
		PreparedStatement pst = c.prepareStatement(TMECHANICS_DELETE)) {
		pst.setString(1, idMechanic);
		pst.executeUpdate();
	    }

	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    /**
     * @throws BusinessException if: - the mechanic does not exist - the
     *                           mechanic has workorders assigned - the mechanic
     *                           has interventions done - the mechanic has
     *                           contracts
     */
    private void checkConditions() throws BusinessException {
	try (Connection c = Jdbc.createThreadConnection();) {
	    try (
		PreparedStatement pst = c.prepareStatement(TMECHANICHS_EXIST)) {
		pst.setString(1, idMechanic);

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			throw new BusinessException();
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }
}
