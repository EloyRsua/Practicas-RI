package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public class WorkOrderRecordAssembler {

    public static WorkOrderRecord toRecord(ResultSet rs) throws SQLException {
	if (rs == null) {
	    return null;
	}

	WorkOrderRecord w = new WorkOrderRecord();
	w.id = rs.getString("id");
	w.version = rs.getLong("version");

	// Timestamps → LocalDateTime (pueden ser nulos en BD)
	var createdAt = rs.getTimestamp("createdAt");
	var updatedAt = rs.getTimestamp("updatedAt");
	var date = rs.getTimestamp("date");

	w.createdAt = (createdAt != null) ? createdAt.toLocalDateTime() : null;
	w.updatedAt = (updatedAt != null) ? updatedAt.toLocalDateTime() : null;
	w.date = (date != null) ? date.toLocalDateTime() : null;

	w.entityState = rs.getString("entityState");
	w.amount = rs.getDouble("amount");
	w.description = rs.getString("description");
	w.state = rs.getString("state");

	w.invoice_id = rs.getString("invoice_id");
	w.mechanic_id = rs.getString("mechanic_id");
	w.vehicle_id = rs.getString("vehicle_id");

	return w;
    }
}
