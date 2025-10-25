package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

    @Override
    public void add(WorkOrderRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(WorkOrderRecord t) throws PersistenceException {
	Timestamp now = new Timestamp(System.currentTimeMillis());
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TWORKORDERS_UPDATE"))) {

		pst.setTimestamp(1, now);
		pst.setString(2, t.invoice_id);
		pst.setString(3, t.id);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public Optional<WorkOrderRecord> findById(String id)
	throws PersistenceException {
	Optional<WorkOrderRecord> om = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TWORKORDERS_FIND_BY_ID"))) {
		pst.setString(1, id);

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			WorkOrderRecord m = WorkOrderRecordAssembler.toRecord(
			    rs);
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
    public List<WorkOrderRecord> findAll() throws PersistenceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<WorkOrderRecord> findActiveWorkOrderByMechanicId(String id) {
	List<WorkOrderRecord> listOfWorkorders = new ArrayList<WorkOrderRecord>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence(
		    "TWORKORDERS_FIND_ACTIVE_BY_MECHANIC_ID"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			WorkOrderRecord wr = WorkOrderRecordAssembler.toRecord(
			    rs);
			listOfWorkorders.add(wr);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return listOfWorkorders;
    }

    @Override
    public List<WorkOrderRecord> findNotInvoicedWorkOrders(String nif) {
	List<WorkOrderRecord> result = new ArrayList<>();
	try

	{
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TWORKORDERS_FIND_NOT_INVOICED"))) {

		pst.setString(1, nif);

		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			WorkOrderRecord wr = WorkOrderRecordAssembler.toRecord(
			    rs);
			result.add(wr);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return result;
    }

    @Override
    public double findTotalForMechanicInMonth(String mechanic_id,
	LocalDate date) {
	double total = 0;
	YearMonth monthAndYear = YearMonth.from(date);

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence(
		    "TWORKORDERS_TOTAL_FOR_MECHANIC_FOR_MONTH"))) {
		pst.setString(1, mechanic_id);
		pst.setDate(2, Date.valueOf(monthAndYear.atDay(1)));
		pst.setDate(3, Date.valueOf(monthAndYear.atEndOfMonth()));

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			total = rs.getDouble("total");
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return total;
    }

}
