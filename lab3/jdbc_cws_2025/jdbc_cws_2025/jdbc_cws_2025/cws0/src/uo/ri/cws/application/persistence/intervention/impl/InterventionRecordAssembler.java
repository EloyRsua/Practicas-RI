package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public class InterventionRecordAssembler {

    public static InterventionRecord toRecord(ResultSet rs)
	throws SQLException {
	if (rs == null) {
	    return null;
	}

	InterventionRecord i = new InterventionRecord();

	// Campos heredados de Record
	i.id = rs.getString("id");
	i.version = rs.getLong("version");
	i.entityState = rs.getString("entityState");

	var createdAt = rs.getTimestamp("createdAt");
	var updatedAt = rs.getTimestamp("updatedAt");

	i.createdAt = (createdAt != null) ? createdAt.toLocalDateTime() : null;
	i.updatedAt = (updatedAt != null) ? updatedAt.toLocalDateTime() : null;

	// Campos propios de InterventionRecord
	var date = rs.getTimestamp("date");
	i.date = (date != null) ? date.toLocalDateTime() : null;

	i.minutes = rs.getInt("minutes"); // INTEGER → int
	i.mechanic_id = rs.getString("mechanic_id");
	i.workOrder_id = rs.getString("workOrder_id");

	return i;
    }
}
