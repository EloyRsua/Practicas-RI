package uo.ri.cws.application.mechanic.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class DeleteMechanic {

    private static final String SQL_DELETE_MECHANIC = "DELETE FROM TMECHANICS WHERE ID = ?";
    private static final String SQL_CHECK_MECHANIC_EXISTS = "SELECT * FROM TMECHANICS WHERE id=?";
    private static final String SQL_CHECK_WORK_ORDERS = "SELECT * FROM TWORKORDERS WHERE mechanic_id=? AND (state='ASSIGNED' OR state='OPEN')";
    private static final String SQL_CHECK_INTERVENTIONS = "SELECT * FROM TINTERVENTIONS WHERE mechanic_id=?";
    private static final String SQL_CHECK_CONTRACTS_INFORCE = "SELECT * FROM TCONTRACTS WHERE mechanic_id=? AND state='IN_FORCE' or state='TERMINATED'";
    private final String idMechanic;

    public DeleteMechanic(String mechanicId) {
	ArgumentChecks.isNotBlank(mechanicId);
	this.idMechanic = mechanicId;
    }

    public void execute() throws BusinessException {
	try (Connection c = Jdbc.createThreadConnection()) {
	    checkConditions(c);
	    executeQuery(c);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    private void executeQuery(Connection c) throws SQLException {
	try (PreparedStatement pst = c.prepareStatement(SQL_DELETE_MECHANIC)) {
	    pst.setString(1, idMechanic);
	    pst.executeUpdate();
	}
    }

    private void checkConditions(Connection c)
	throws BusinessException, SQLException {
	checkIfExists(c);
	checkWorkOrders(c);
	checkContractsInForce(c);
	checkInterventions(c);
    }

    private void checkIfExists(Connection c)
	throws BusinessException, SQLException {
	try (PreparedStatement pst = c.prepareStatement(
	    SQL_CHECK_MECHANIC_EXISTS)) {
	    pst.setString(1, idMechanic);
	    try (ResultSet rs = pst.executeQuery()) {
		if (!rs.next()) {
		    throw new BusinessException(
			"No existe un mecánico con id " + idMechanic);
		}
	    }
	}
    }

    private void checkWorkOrders(Connection c)
	throws BusinessException, SQLException {
	try (
	    PreparedStatement pst = c.prepareStatement(SQL_CHECK_WORK_ORDERS)) {
	    pst.setString(1, idMechanic);
	    try (ResultSet rs = pst.executeQuery()) {
		if (rs.next()) {
		    throw new BusinessException(
			"No se pueden borrar mecánicos con órdenes de trabajo activas, id: "
			    + idMechanic);
		}
	    }
	}
    }

    private void checkContractsInForce(Connection c)
	throws BusinessException, SQLException {
	try (PreparedStatement pst = c.prepareStatement(
	    SQL_CHECK_CONTRACTS_INFORCE)) {
	    pst.setString(1, idMechanic);
	    try (ResultSet rs = pst.executeQuery()) {
		if (rs.next()) {
		    throw new BusinessException(
			"No se pueden borrar mecánicos con contratos activos, id: "
			    + idMechanic);
		}
	    }
	}
    }

    private void checkInterventions(Connection c)
	throws BusinessException, SQLException {
	try (PreparedStatement pst = c.prepareStatement(
	    SQL_CHECK_INTERVENTIONS)) {
	    pst.setString(1, idMechanic);
	    try (ResultSet rs = pst.executeQuery()) {
		if (rs.next()) {
		    throw new BusinessException(
			"No se pueden borrar mecánicos con intervenciones, id: "
			    + idMechanic);
		}
	    }
	}
    }
}
