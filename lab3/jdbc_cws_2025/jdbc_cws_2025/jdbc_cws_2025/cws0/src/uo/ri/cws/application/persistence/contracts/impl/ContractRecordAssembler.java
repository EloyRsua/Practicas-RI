package uo.ri.cws.application.persistence.contracts.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.contracts.ContractGateway.ContractRecord;

public class ContractRecordAssembler {

    public static ContractRecord toRecord(ResultSet rs) throws SQLException {
	if (rs == null) {
	    return null;
	}

	ContractRecord c = new ContractRecord();

	// Campos heredados de Record
	c.id = rs.getString("id");
	c.version = rs.getLong("version");
	c.entityState = rs.getString("entityState");

	var createdAt = rs.getTimestamp("createdAt");
	var updatedAt = rs.getTimestamp("updatedAt");

	c.createdAt = (createdAt != null) ? createdAt.toLocalDateTime() : null;
	c.updatedAt = (updatedAt != null) ? updatedAt.toLocalDateTime() : null;

	// Campos propios de ContractRecord
	c.anualBaseSalary = rs.getDouble("annualBaseSalary");
	c.settlement = rs.getDouble("settlement");

	var startDate = rs.getDate("startDate");
	var endDate = rs.getDate("endDate"); // No está en ContractRecord, pero
					     // si lo agregas en el futuro
	// puedes mapearlo aquí también

	// Convertimos de java.sql.Date a LocalDateTime (manteniendo coherencia
	// con otros registros)
	c.startDate = (startDate != null) ? startDate.toLocalDate()
						     .atStartOfDay()
	    : null;

	c.state = rs.getString("state");
	c.taxRate = rs.getDouble("taxRate");

	c.contractType_id = rs.getString("contractType_id");
	c.mechanic_id = rs.getString("mechanic_id");
	c.professionalGroup_id = rs.getString("professionalGroup_id");

	return c;
    }
}
