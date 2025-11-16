package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TPayrolls")
public class Payroll extends BaseEntity {

	// natural attributes
	private LocalDate date;
	private double baseSalary;
	private double extraSalary;
	private double productivityEarning;
	private double trienniumEarning;
	private double taxDeduction;
	private double nicDeduction;
	private double totalDeductions;
	private double netSalary;
	private double grossSalary;

	// accidental Attributes
	@ManyToOne
	private Contract contract;

	Payroll() {
	}

	public Payroll(LocalDate date, double baseSalary, double extraSalary,
		double productivityEarning, double trienniumEarning,
		double taxDeduction, double nicDeduction, double totalDeductions,
		double netSalary, double grossSalary, Contract contract) {
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isTrue(baseSalary >= 0);
		ArgumentChecks.isTrue(extraSalary >= 0);
		ArgumentChecks.isTrue(productivityEarning >= 0);
		ArgumentChecks.isTrue(trienniumEarning >= 0);
		ArgumentChecks.isTrue(taxDeduction >= 0);
		ArgumentChecks.isTrue(nicDeduction >= 0);
		ArgumentChecks.isTrue(totalDeductions >= 0);
		ArgumentChecks.isTrue(netSalary >= 0);
		ArgumentChecks.isTrue(grossSalary >= 0);
		ArgumentChecks.isNotNull(contract);
		ArgumentChecks.isTrue(!date.isBefore(contract.getStartDate()));

		this.date = date;
		this.baseSalary = baseSalary;
		this.extraSalary = extraSalary;
		this.productivityEarning = productivityEarning;
		this.trienniumEarning = trienniumEarning;
		this.taxDeduction = taxDeduction;
		this.nicDeduction = nicDeduction;
		this.totalDeductions = totalDeductions;
		this.netSalary = netSalary;
		this.grossSalary = grossSalary;

		Associations.Generates.link(contract, this);
	}

	public Payroll(Contract contract, LocalDate date) {
		this(date, calculateBaseSalary(contract, date),
			calculateExtraSalary(contract, date),
			calculateProductivityEarning(contract, date),
			calculateTrienniumEarning(contract, date), 0.0, 0.0, 0.0, 0.0, 0.0,
			contract);

		// Deducciones
		double grossSalary = baseSalary + extraSalary + productivityEarning
			+ trienniumEarning;
		double taxDeduction = grossSalary * contract.getTaxRate();
		double nicDeduction = (contract.getAnnualBaseSalary() * 0.05) / 12.0;
		double totalDeductions = taxDeduction + nicDeduction;
		double netSalary = grossSalary - totalDeductions;

		this.grossSalary = grossSalary;
		this.taxDeduction = taxDeduction;
		this.nicDeduction = nicDeduction;
		this.totalDeductions = totalDeductions;
		this.netSalary = netSalary;

	}

	private static double calculateBaseSalary(Contract c, LocalDate d) {
		return c.getAnnualBaseSalary() / 14;
	}

	private static double calculateExtraSalary(Contract c, LocalDate d) {
		double extraSalary = 0.0;
		if (d.getMonth()
			.equals(Month.JUNE)
			|| d.getMonth()
				.equals(Month.DECEMBER)) {
			extraSalary = c.getAnnualBaseSalary() / 14;
		}
		return extraSalary;
	}

	private static double calculateProductivityEarning(Contract c,
		LocalDate d) {
		double productivityEarning = 0.0;
		double productivityRate = c.getProfessionalGroup()
			.getProductivityRate();
		for (Intervention i : c.getMechanic()
			._getInterventions()) {
			WorkOrder w = i.getWorkOrder();
			if (w.isInvoiced() && w.getDate()
				.getYear() == d.getYear() && w.getDate()
					.getMonthValue() == d.getMonthValue()) {
				productivityEarning += w.getAmount();
			}
		}
		productivityEarning *= productivityRate;
		return productivityEarning;
	}

	private static double calculateTrienniumEarning(Contract c, LocalDate d) {
		double trienniumSalary = c.getProfessionalGroup()
			.getTrienniumPayment();
		long years = ChronoUnit.YEARS.between(c.getStartDate(), d);
		double trienniumEarning = (years / 3) * trienniumSalary;
		return trienniumEarning;
	}

	public double getMonthlyBaseSalary() {
		return baseSalary;
	}

	public Contract getContract() {
		return contract;
	}

	void _setContract(Contract contract) {
		this.contract = contract;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public double getExtraSalary() {
		return extraSalary;
	}

	public void setExtraSalary(double extraSalary) {
		this.extraSalary = extraSalary;
	}

	public double getProductivityEarning() {
		return productivityEarning;
	}

	public void setProductivityEarning(double productivityEarning) {
		this.productivityEarning = productivityEarning;
	}

	public double getTrienniumEarning() {
		return trienniumEarning;
	}

	public void setTrienniumEarning(double trienniumEarning) {
		this.trienniumEarning = trienniumEarning;
	}

	public double getTaxDeduction() {
		return taxDeduction;
	}

	public void setTaxDeduction(double taxDeduction) {
		this.taxDeduction = taxDeduction;
	}

	public double getNicDeduction() {
		return nicDeduction;
	}

	public void setNicDeduction(double nicDeduction) {
		this.nicDeduction = nicDeduction;
	}

	public double getTotalDeductions() {
		return totalDeductions;
	}

	public void setTotalDeductions(double totalDeductions) {
		this.totalDeductions = totalDeductions;
	}

	public double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}

	public double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(double grossSalary) {
		this.grossSalary = grossSalary;
	}

	@Override
	public String toString() {
		return "Payroll [date=" + date + ", baseSalary=" + baseSalary
			+ ", extraSalary=" + extraSalary + ", productivityEarning="
			+ productivityEarning + ", trienniumEarning=" + trienniumEarning
			+ ", taxDeduction=" + taxDeduction + ", nicDeduction="
			+ nicDeduction + ", totalDeductions=" + totalDeductions
			+ ", netSalary=" + netSalary + ", grossSalary=" + grossSalary
			+ ", contract=" + contract + "]";
	}
}
