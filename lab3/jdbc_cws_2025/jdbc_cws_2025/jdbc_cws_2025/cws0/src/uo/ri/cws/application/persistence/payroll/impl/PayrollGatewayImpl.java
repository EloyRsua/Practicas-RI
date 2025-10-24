package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class PayrollGatewayImpl implements PayrollGateway {

    @Override
    public void add(PayrollRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(PayrollRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<PayrollRecord> findById(String id)
	throws PersistenceException {
	Optional<PayrollRecord> om = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FINDBYID"))) {
		pst.setString(1, id);

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			PayrollRecord m = PayrollRecordAssembler.toRecord(rs);
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
    public List<PayrollRecord> findAll() throws PersistenceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<PayrollRecord> findPayrollsByProfessionalGroup(String name) {
	List<PayrollRecord> list = new ArrayList<PayrollRecord>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FINDBY_GROUP_NAME"))) {
		pst.setString(1, name);

		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			PayrollRecord pr = PayrollRecordAssembler.toRecord(rs);
			list.add(pr);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return list;

    }

    @Override
    public List<PayrollRecord> findPayrollsByMechanicId(String mechanicId) {
	// TODO Auto-generated method stub
	return null;
    }
}
