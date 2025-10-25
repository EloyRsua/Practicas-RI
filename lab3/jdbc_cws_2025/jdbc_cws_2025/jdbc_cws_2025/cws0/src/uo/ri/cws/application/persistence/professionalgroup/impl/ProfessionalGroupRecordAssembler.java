package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public class ProfessionalGroupRecordAssembler {

    public static ProfessionalGroupRecord toRecord(ResultSet rs)
	throws SQLException {
	ProfessionalGroupRecord p = new ProfessionalGroupRecord();
	p.id = rs.getString("ID");
	p.createdAt = rs.getTimestamp("CREATEDAT")
			.toLocalDateTime();
	p.updatedAt = rs.getTimestamp("UPDATEDAT")
			.toLocalDateTime();
	p.entityState = rs.getString("ENTITYSTATE");
	p.name = rs.getString("NAME");
	p.productivityRate = rs.getDouble("PRODUCTIVITYRATE"); // coincide con
							       // la DB
	p.trienniumPayment = rs.getDouble("TRIENNIUMPAYMENT"); // coincide con
							       // la DB
	p.version = rs.getLong("VERSION");
	return p;
    }

}
