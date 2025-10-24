package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ProfessionalGroupGatewayImpl implements ProfessionalGroupGateway {

    @Override
    public void add(ProfessionalGroupRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(ProfessionalGroupRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<ProfessionalGroupRecord> findById(String id)
	throws PersistenceException {
	Optional<ProfessionalGroupRecord> or = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FINDBYID"))) {
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
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Optional<ProfessionalGroupRecord> findByGroupName(String name) {
	Optional<ProfessionalGroupRecord> or = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FINDBY_GROUP_NAME"))) {
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
