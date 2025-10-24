package uo.ri.cws.application.service.payroll.crud;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;

public class PayrollAssembler {

    public static PayrollDto toDto(PayrollRecord r) {
	if (r == null) {
	    return null;
	}

	PayrollDto dto = new PayrollDto();
	dto.id = r.id;
	dto.version = r.version;
	dto.contractId = r.contract_id;
	dto.date = r.date;

	// Earnings
	dto.baseSalary = r.base_Salary;
	dto.extraSalary = r.extra_Salary;
	dto.productivityEarning = r.productivity_Earning;
	dto.trienniumEarning = r.triennium_Earning;

	// Deductions
	dto.taxDeduction = r.tax_Deduction;
	dto.nicDeduction = r.nic_Deduction;

	// Net wage (podrías calcularlos si no vienen en el record)
	dto.grossSalary = dto.baseSalary + dto.extraSalary
	    + dto.productivityEarning + dto.trienniumEarning;
	dto.totalDeductions = dto.taxDeduction + dto.nicDeduction;
	dto.netSalary = dto.grossSalary - dto.totalDeductions;

	return dto;
    }

    public static PayrollSummaryDto toSummaryDto(PayrollRecord r) {
	if (r == null) {
	    return null;
	}

	PayrollSummaryDto dto = new PayrollSummaryDto();
	dto.id = r.id;
	dto.date = r.date;

	// Calculamos el salario neto igual que en toDto()
	double grossSalary = r.base_Salary + r.extra_Salary
	    + r.productivity_Earning + r.triennium_Earning;
	double totalDeductions = r.tax_Deduction + r.nic_Deduction;
	dto.netSalary = grossSalary - totalDeductions;

	return dto;
    }

    public static PayrollRecord toRecord(PayrollDto dto) {
	if (dto == null) {
	    return null;
	}

	PayrollRecord r = new PayrollRecord();
	r.id = dto.id;
	r.version = dto.version;
	r.contract_id = dto.contractId;
	r.date = dto.date;

	// Earnings
	r.base_Salary = dto.baseSalary;
	r.extra_Salary = dto.extraSalary;
	r.productivity_Earning = dto.productivityEarning;
	r.triennium_Earning = dto.trienniumEarning;

	// Deductions
	r.tax_Deduction = dto.taxDeduction;
	r.nic_Deduction = dto.nicDeduction;

	// Campos heredados de Record
	r.createdAt = null;
	r.updatedAt = null;
	r.entityState = null;

	return r;
    }
}
