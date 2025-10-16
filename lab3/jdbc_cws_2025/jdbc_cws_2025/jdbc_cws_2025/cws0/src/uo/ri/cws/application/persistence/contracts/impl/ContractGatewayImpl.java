package uo.ri.cws.application.persistence.contracts.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	// TODO Auto-generated method stub
	return Optional.empty();
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
		    if (rs.next()) {
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
