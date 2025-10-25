package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ProfessionalGroupGatewayImpl implements ProfessionalGroupGateway {

    @Override
    public void add(ProfessionalGroupRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFESSIONALGROUPS_CREATE"))) {
		pst.setString(1, t.id);
		pst.setString(2, t.name);
		pst.setDouble(3, t.productivityRate);
		pst.setDouble(4, t.trienniumPayment);
		pst.setLong(5, t.version);
		pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
		pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		pst.setString(8, "ENABLED");

		pst.executeUpdate();

	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public void remove(String id) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFESSIONALGROUPS_REMOVE"))) {
		pst.setString(1, id);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public void update(ProfessionalGroupRecord t) throws PersistenceException {
	Timestamp now = new Timestamp(System.currentTimeMillis());
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFESSIONALGROUP_UPDATE"))) {
		pst.setDouble(1, t.trienniumPayment);
		pst.setDouble(2, t.productivityRate);
		pst.setTimestamp(3, now);
		pst.setString(4, t.name);

		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public Optional<ProfessionalGroupRecord> findById(String id)
	throws PersistenceException {
	Optional<ProfessionalGroupRecord> or = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFGROUPS_FINDBYID"))) {
		pst.setString(1, id);

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			ProfessionalGroupRecord r = ProfessionalGroupRecordAssembler.toRecord(
			    rs);
			or = Optional.of(r);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return or;
    }

    @Override
    public List<ProfessionalGroupRecord> findAll() throws PersistenceException {
	List<ProfessionalGroupRecord> lp = new ArrayList<>();

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDALL"))) {
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			ProfessionalGroupRecord p = ProfessionalGroupRecordAssembler.toRecord(
			    rs);
			lp.add(p);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return lp;
    }

    @Override
    public Optional<ProfessionalGroupRecord> findByGroupName(String name) {
	Optional<ProfessionalGroupRecord> or = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFGROUPS_FINDBY_GROUP_NAME"))) {
		pst.setString(1, name);

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			ProfessionalGroupRecord r = ProfessionalGroupRecordAssembler.toRecord(
			    rs);
			or = Optional.of(r);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return or;
    }

}
