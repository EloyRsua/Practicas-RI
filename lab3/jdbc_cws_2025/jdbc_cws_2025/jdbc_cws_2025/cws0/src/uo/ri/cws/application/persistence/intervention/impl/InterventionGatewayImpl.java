package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class InterventionGatewayImpl implements InterventionGateway {

    @Override
    public List<InterventionRecord> findInterventionsByMechanicId(String id) {
	List<InterventionRecord> list = new ArrayList<InterventionRecord>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence(
		    "TINTERVENTION_FIND_INTERVENTION_BY_MECHANIC_ID"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			list.add(InterventionRecordAssembler.toRecord(rs));
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return list;
    }

    @Override
    public void add(InterventionRecord t) throws PersistenceException {
	// No asignado

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// No asignado

    }

    @Override
    public void update(InterventionRecord t) throws PersistenceException {
	// No asignado

    }

    @Override
    public Optional<InterventionRecord> findById(String id)
	throws PersistenceException {
	// No asignado
	return Optional.empty();
    }

    @Override
    public List<InterventionRecord> findAll() throws PersistenceException {
	// No asignado
	return null;
    }

}
