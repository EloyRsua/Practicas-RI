package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public class MechanicRecordAssembler {

    public static MechanicRecord toRecord(ResultSet rs) throws SQLException {
	MechanicRecord m = new MechanicRecord();

	m.id = rs.getString("ID");
	m.version = rs.getLong("VERSION");

	m.name = rs.getString("NAME");
	m.nif = rs.getString("NIF");
	m.surname = rs.getString("SURNAME");

	return m;
    }

}
