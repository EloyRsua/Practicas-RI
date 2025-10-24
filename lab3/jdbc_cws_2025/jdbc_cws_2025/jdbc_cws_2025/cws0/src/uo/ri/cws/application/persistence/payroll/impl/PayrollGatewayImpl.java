package uo.ri.cws.application.persistence.payroll.impl;

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
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_REMOVE"))) {
		pst.setString(1, id);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

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
	List<PayrollRecord> list = new ArrayList<PayrollRecord>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FIND_ALL"))) {
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
	List<PayrollRecord> list = new ArrayList<PayrollRecord>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FINDBY_MECHANIC_ID"))) {
		pst.setString(1, mechanicId);

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
    public List<PayrollRecord> findPayrollsByLocalDate(LocalDate date) {
	List<PayrollRecord> list = new ArrayList<PayrollRecord>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FIND_BY_DATE"))) {
		pst.setDate(1, Date.valueOf(date.withDayOfMonth(1)));
		pst.setDate(2,
		    Date.valueOf(date.withDayOfMonth(date.lengthOfMonth())));

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
    public List<PayrollRecord> findPayrollsByLocalDateAndMechanicId(
	String mechanicId, LocalDate date) {
	List<PayrollRecord> list = new ArrayList<PayrollRecord>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence(
		    "TPAYROLLS_FIND_BY_DATE_AND_MECHANICID"))) {
		pst.setDate(1, Date.valueOf(date.withDayOfMonth(1)));
		pst.setDate(2,
		    Date.valueOf(date.withDayOfMonth(date.lengthOfMonth())));
		pst.setString(3, mechanicId);

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
}
