package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public class MechanicRecordAssembler {

    public static MechanicRecord toRecord(ResultSet rs) throws SQLException {
	if (rs == null) {
	    return null;
	}

	MechanicRecord m = new MechanicRecord();

	m.id = rs.getString("id");
	m.version = rs.getLong("version");

	var createdAt = rs.getTimestamp("createdAt");
	var updatedAt = rs.getTimestamp("updatedAt");

	m.createdAt = (createdAt != null) ? createdAt.toLocalDateTime() : null;
	m.updatedAt = (updatedAt != null) ? updatedAt.toLocalDateTime() : null;

	m.entityState = rs.getString("entityState");
	m.name = rs.getString("name");
	m.nif = rs.getString("nif");
	m.surname = rs.getString("surname");

	return m;
    }
}
