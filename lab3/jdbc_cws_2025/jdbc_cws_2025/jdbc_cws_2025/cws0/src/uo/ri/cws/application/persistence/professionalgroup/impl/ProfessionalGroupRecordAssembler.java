package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public class ProfessionalGroupRecordAssembler {

    public static ProfessionalGroupRecord toRecord(ResultSet rs)
	throws SQLException {
	ProfessionalGroupRecord r = new ProfessionalGroupRecord();

	r.id = rs.getString("ID");
	r.version = rs.getLong("VERSION");

	r.name = rs.getString("NAME");
	r.productivityRate = rs.getDouble("PRODUCTIVITYRATE");
	r.trienniumPayment = rs.getDouble("TRIENNIUMPAYMENT");

	return r;
    }
}
