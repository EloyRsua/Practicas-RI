package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway {

    @Override
    public void add(InvoiceRecord t) throws PersistenceException {
	try {
	    Connection connection = Jdbc.getCurrentConnection();
	    String idInvoice = UUID.randomUUID()
				   .toString();
	    try (PreparedStatement pst = connection.prepareStatement(
		Queries.getSQLSentence("TINVOICES_INSERT"))) {
		pst.setString(1, idInvoice);
		pst.setLong(2, t.number);
		pst.setDate(3, java.sql.Date.valueOf(t.date));
		pst.setDouble(4, t.vat);
		pst.setDouble(5, t.amount);
		pst.setString(6, "NOT_YET_PAID");
		pst.setLong(7, 1L);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(InvoiceRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<InvoiceRecord> findById(String id)
	throws PersistenceException {
	// TODO Auto-generated method stub
	return Optional.empty();
    }

    @Override
    public List<InvoiceRecord> findAll() throws PersistenceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public long getLastNumber() {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TINVOICES_SELECT_LAST_NUMBER"))) {
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			return rs.getLong(1) + 1;
		    }
		}
	    }
	    return 1L;
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

}
