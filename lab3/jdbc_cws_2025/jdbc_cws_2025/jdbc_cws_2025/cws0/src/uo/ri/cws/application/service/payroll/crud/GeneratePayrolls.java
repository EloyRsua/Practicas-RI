package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracts.ContractGateway;
import uo.ri.cws.application.persistence.contracts.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.util.assertion.ArgumentChecks;

public class GeneratePayrolls implements Command<List<PayrollDto>> {

    private LocalDate date;

    private PayrollGateway pg = Factories.persistence.forPayroll();
    private ContractGateway cg = Factories.persistence.forContract();
    private ProfessionalGroupGateway fg = Factories.persistence.forProfessionalGroup();
    private WorkOrderGateway wg = Factories.persistence.forWorkOrder();

    public GeneratePayrolls(LocalDate date) {
	ArgumentChecks.isNotNull(date);
	this.date = date;
    }

    @Override
    public List<PayrollDto> execute() {
	YearMonth previousMonth = YearMonth.from(date.minusMonths(1));
	LocalDate lastDayOfPreviousMonth = previousMonth.atEndOfMonth();

	// Evitar duplicados
	if (!pg.findPayrollsByLocalDate(lastDayOfPreviousMonth)
	       .isEmpty()) {
	    return new ArrayList<>();
	}

	List<String> contractIds = cg.findContractsByMonth(
	    lastDayOfPreviousMonth);

	List<PayrollDto> generatedPayrolls = new ArrayList<>();
	for (String contractId : contractIds) {
	    PayrollDto dto = generatePayrollForContract(contractId,
		lastDayOfPreviousMonth);
	    pg.add(PayrollAssembler.toRecord(dto));
	    generatedPayrolls.add(dto);
	}
	return generatedPayrolls;
    }

    private PayrollDto generatePayrollForContract(String contractId,
	LocalDate date) {
	PayrollDto dto = new PayrollDto();

	ContractRecord cr = cg.findById(contractId)
			      .get();
	ProfessionalGroupRecord pr = fg.findById(cr.professionalGroup_id)
				       .get();

	double baseSalary = cr.anualBaseSalary / 14.0;

	double extraSalary = (date.getMonth() == Month.JUNE
	    || date.getMonth() == Month.DECEMBER) ? baseSalary : 0.0;

	double totalWorkordersAmount = wg.findTotalForMechanicInMonth(
	    cr.mechanic_id, date);
	double productivityEarning = totalWorkordersAmount
	    * pr.productivityRate;

	long years = ChronoUnit.YEARS.between(cr.startDate.toLocalDate(), date);
	double trienniumEarning = (years / 3) * pr.trienniumPayment;

	double grossSalary = baseSalary + extraSalary + trienniumEarning
	    + productivityEarning;

	double taxDeduction = grossSalary * cr.taxRate;
	double nicDeduction = (cr.anualBaseSalary * 0.05) / 12.0;
	double totalDeductions = taxDeduction + nicDeduction;

	double netSalary = grossSalary - totalDeductions;

	dto.id = UUID.randomUUID()
		     .toString();
	dto.version = 1L;
	dto.contractId = contractId;
	dto.date = date;
	dto.baseSalary = baseSalary;
	dto.extraSalary = extraSalary;
	dto.productivityEarning = productivityEarning;
	dto.trienniumEarning = trienniumEarning;
	dto.grossSalary = grossSalary;
	dto.taxDeduction = taxDeduction;
	dto.nicDeduction = nicDeduction;
	dto.totalDeductions = totalDeductions;
	dto.netSalary = netSalary;

	return dto;
    }
}
