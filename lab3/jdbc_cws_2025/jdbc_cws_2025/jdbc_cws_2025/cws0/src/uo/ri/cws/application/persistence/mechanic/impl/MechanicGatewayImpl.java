package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway {
    @Override
    public void add(MechanicRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(MechanicRecord t) throws PersistenceException {
	Timestamp now = new Timestamp(System.currentTimeMillis());
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_UPDATE"))) {
		pst.setString(1, t.id);
		pst.setString(2, t.nif);
		pst.setString(3, t.name);
		pst.setString(4, t.surname);
		pst.setLong(5, t.version);
		pst.setTimestamp(6, now);
		pst.setTimestamp(7, now);
		pst.setString(8, "ENABLED");

		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public Optional<MechanicRecord> findById(String id) throws PersistenceException {
	Optional<MechanicRecord> om = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYID"))) {
		pst.setString(1, id);

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			MechanicRecord m = new MechanicRecord();

			// Igual seria buena idea crear una clase MechanicRecordAsembler y que reciba el
			// resultSet para crear un MechanicRecord
			m.id = rs.getString("id");
			m.nif = rs.getString("nif");
			m.name = rs.getString("name");
			m.createdAt = rs.getTimestamp("createdAt")
					.toLocalDateTime();

			om = Optional.of(m);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return om;
    }

    @Override
    public List<MechanicRecord> findAll() throws PersistenceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Optional<MechanicRecord> findByNif(String nif) {
	// TODO Auto-generated method stub
	return Optional.empty();
    }

}
