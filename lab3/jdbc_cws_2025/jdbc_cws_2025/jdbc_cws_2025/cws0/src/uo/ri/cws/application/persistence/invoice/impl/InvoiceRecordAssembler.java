package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public class InvoiceRecordAssembler {

    public static InvoiceRecord toRecord(ResultSet rs) throws SQLException {
	if (rs == null) {
	    return null;
	}

	InvoiceRecord i = new InvoiceRecord();

	// Campos heredados de Record
	i.id = rs.getString("id");
	i.version = rs.getLong("version");

	var createdAt = rs.getTimestamp("createdAt");
	var updatedAt = rs.getTimestamp("updatedAt");

	i.createdAt = (createdAt != null) ? createdAt.toLocalDateTime() : null;
	i.updatedAt = (updatedAt != null) ? updatedAt.toLocalDateTime() : null;

	i.entityState = rs.getString("entityState");

	// Campos propios de InvoiceRecord
	var date = rs.getDate("date");
	i.date = (date != null) ? date.toLocalDate() : null;

	i.number = rs.getLong("number");
	i.state = rs.getString("state");

	double amount = rs.getDouble("amount");
	i.amount = rs.wasNull() ? null : amount;

	double vat = rs.getDouble("vat");
	i.vat = rs.wasNull() ? null : vat;

	return i;
    }
}
