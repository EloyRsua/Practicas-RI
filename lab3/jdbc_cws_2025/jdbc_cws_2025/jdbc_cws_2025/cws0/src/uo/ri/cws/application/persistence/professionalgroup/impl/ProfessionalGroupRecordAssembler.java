package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;

public class ProfessionalGroupRecordAssembler {

    public static ProfessionalGroupRecord toRecord(ResultSet rs)
	throws SQLException {
	ProfessionalGroupRecord p = new ProfessionalGroupRecord();
	p.id = rs.getString("ID");
	p.name = rs.getString("NAME");
	p.productivityRate = rs.getDouble("PRODUCTIVITYRATE");
	p.trienniumPayment = rs.getDouble("TRIENNIUMPAYMENT");
	p.version = rs.getLong("VERSION");
	return p;
    }

    public static ProfessionalGroupRecord toRecord(ProfessionalGroupDto dto) {
	ProfessionalGroupRecord record = new ProfessionalGroupRecord();
	record.id = dto.id;
	record.version = dto.version;
	record.name = dto.name;
	record.trienniumPayment = dto.trienniumPayment;
	record.productivityRate = dto.productivityRate;

	// Campos adicionales que no están en el DTO
	record.createdAt = LocalDateTime.now();
	record.updatedAt = LocalDateTime.now();
	record.entityState = "ENABLED";

	return record;
    }

    public static ProfessionalGroupDto toDto(ProfessionalGroupRecord record) {
	ProfessionalGroupDto dto = new ProfessionalGroupDto();
	dto.id = record.id;
	dto.version = record.version;
	dto.name = record.name;
	dto.trienniumPayment = record.trienniumPayment;
	dto.productivityRate = record.productivityRate;
	return dto;
    }
}
