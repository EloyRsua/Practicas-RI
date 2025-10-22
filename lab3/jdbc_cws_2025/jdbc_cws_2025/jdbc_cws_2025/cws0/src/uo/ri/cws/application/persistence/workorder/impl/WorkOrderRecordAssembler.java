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

	var date = rs.getTimestamp("date");
	w.date = (date != null) ? date.toLocalDateTime() : null;

	w.amount = rs.getDouble("amount");
	w.description = rs.getString("description");
	w.state = rs.getString("state");

	return w;
    }
}
