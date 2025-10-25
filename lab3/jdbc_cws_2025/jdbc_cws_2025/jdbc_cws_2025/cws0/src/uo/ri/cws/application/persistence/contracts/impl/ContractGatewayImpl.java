package uo.ri.cws.application.persistence.contracts.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracts.ContractGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ContractGatewayImpl implements ContractGateway {

    @Override
    public void add(ContractRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(ContractRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<ContractRecord> findById(String id)
	throws PersistenceException {
	Optional<ContractRecord> cr = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TCONTRACTS_FIND_BY_ID"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			cr = Optional.of(ContractRecordAssembler.toRecord(rs));
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return cr;
    }

    @Override
    public List<ContractRecord> findAll() throws PersistenceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<ContractRecord> findActiveContractByMechanicId(String id) {
	List<ContractRecord> list = new ArrayList<ContractGateway.ContractRecord>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence(
		    "TCONTRACT_FIND_ACTIVE_CONTRACTS_BY_MECHANIC_ID"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			list.add(ContractRecordAssembler.toRecord(rs));
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return list;
    }

    @Override
    public List<String> findContractsByMonth(LocalDate monthDate) {
	List<String> ids = new ArrayList<>();
	LocalDate firstDay = monthDate.withDayOfMonth(1);
	LocalDate lastDay = monthDate.withDayOfMonth(monthDate.lengthOfMonth());

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TCONTRACTS_FIND_BY_MONTH"))) {
		pst.setDate(1, Date.valueOf(lastDay));
		pst.setDate(2, Date.valueOf(firstDay));
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			ids.add(rs.getString("id"));
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return ids;
    }

    @Override
    public List<String> findByProfesionalGroup(String id) {
	List<String> list = new ArrayList<>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TCONTRACTS_BY_PROFESSIONALGROUP"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			list.add(rs.getString("id"));
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return list;
    }

    @Override
    public List<ContractRecord> findContractsInForce() {
	List<ContractRecord> list = new ArrayList<ContractGateway.ContractRecord>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TCONTRACT_FIND_ACTIVE_CONTRACTS"))) {
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			list.add(ContractRecordAssembler.toRecord(rs));
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return list;
    }

}
