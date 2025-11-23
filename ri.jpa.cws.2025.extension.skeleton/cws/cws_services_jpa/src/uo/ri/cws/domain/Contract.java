package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(name = "TContracts", uniqueConstraints = {
	@UniqueConstraint(columnNames = { "MECHANIC_ID", "STARTDATE" }) })
public class Contract extends BaseEntity {
	private LocalDate startDate;

	@Basic(optional = false)
	private LocalDate endDate = null;

	@Basic(optional = false)
	private Double annualBaseSalary;
	private Double settlement;
	private Double taxRate;

	@Enumerated(EnumType.STRING)
	private ContractState state;

	// Atributos accidentales
	@ManyToOne
	private Mechanic mechanic;

	@ManyToOne
	private ContractType contractType;

	@ManyToOne
	private ProfessionalGroup professionalGroup;

	@OneToMany(mappedBy = "contract")
	private Set<Payroll> payrolls = new HashSet<>();

	public Contract(Mechanic mechanic, ContractType type,
		ProfessionalGroup group, LocalDate signingDate, LocalDate endDate,
		double annualSalary, Double settlement, Double taxRate) {

		ArgumentChecks.isNotNull(mechanic);
		ArgumentChecks.isNotNull(type);
		ArgumentChecks.isNotNull(group);
		ArgumentChecks.isNotNull(signingDate);

		ArgumentChecks.isTrue(annualSalary >= 0);
		ArgumentChecks.isTrue(taxRate >= 0);
		ArgumentChecks.isTrue(settlement >= 0);

		this.startDate = signingDate.with(TemporalAdjusters.firstDayOfMonth());
		this.annualBaseSalary = annualSalary;
		this.taxRate = taxRate;
		this.settlement = settlement;

		if (isFixedTerm(type)) {
			ArgumentChecks.isNotNull(endDate);
			ArgumentChecks.isTrue(endDate.isAfter(signingDate));
			this.endDate = endDate.with(TemporalAdjusters.lastDayOfMonth());
		} else {
			this.endDate = null;
		}

		Associations.Binds.link(mechanic, this);
		Associations.Categorizes.link(this, group);
		Associations.Defines.link(this, type);

		this.state = ContractState.IN_FORCE;
	}

	private boolean isFixedTerm(ContractType type) {
		return "FIXED_TERM".equals(type.getName());
	}

	public Contract() {

	}

	public Contract(Mechanic mechanic, ContractType type,
		ProfessionalGroup group, LocalDate signingDate, double annualSalary) {

		ArgumentChecks.isNotNull(mechanic);
		ArgumentChecks.isNotNull(type);
		ArgumentChecks.isNotNull(group);
		ArgumentChecks.isNotNull(signingDate);
		ArgumentChecks.isTrue(annualSalary > 0);

		// Para contratos FIXED_TERM → error obligatorio
		if ("FIXED_TERM".equals(type.getName())) {
			throw new IllegalArgumentException(
				"Fixed term contracts require an end date");
		}

		this.startDate = signingDate.with(TemporalAdjusters.firstDayOfMonth());
		this.endDate = null; // siempre para indefinidos
		this.annualBaseSalary = annualSalary;
		this.settlement = 0.0;
		// Asociaciones
		Associations.Binds.link(mechanic, this);
		Associations.Categorizes.link(this, group);
		Associations.Defines.link(this, type);

		// Estado inicial
		this.state = ContractState.IN_FORCE;
	}

	public Contract(Mechanic mechanic, ContractType type,
		ProfessionalGroup group, LocalDate signingDate, LocalDate endDate,
		double baseSalary) {

		this(mechanic, type, group, signingDate, endDate, baseSalary, 0.0, 0.0);

		calculateTaxRate(baseSalary);
	}

	private void calculateTaxRate(double baseSalary) {
		if (baseSalary <= 12450) {
			this.taxRate = 0.19;
		} else if (baseSalary <= 20200) {
			this.taxRate = 0.24;
		} else if (baseSalary <= 35200) {
			this.taxRate = 0.30;
		} else if (baseSalary <= 60000) {
			this.taxRate = 0.37;
		} else if (baseSalary <= 300000) {
			this.taxRate = 0.45;
		} else {
			this.taxRate = 0.47;
		}
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Double getAnnualBaseSalary() {
		return annualBaseSalary;
	}

	public void setAnnualBaseSalary(Double annualBaseSalary) {
		this.annualBaseSalary = annualBaseSalary;
	}

	public Double getSettlement() {
		return settlement;
	}

	public void setSettlement(Double settlement) {
		this.settlement = settlement;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public ContractState getState() {
		return state;
	}

	public void setState(ContractState state) {
		this.state = state;
	}

	public boolean isInForce() {
		return this.state == ContractState.IN_FORCE;
	}

	public boolean isTerminated() {
		return this.state == ContractState.TERMINATED;
	}

	public void terminate(LocalDate endDate) {
		ArgumentChecks.isNotNull(endDate);
		StateChecks.isTrue(isInForce(),
			"Contract must be in force to terminate");
		// Si la fecha es anterior al inicio, lanzar IllegalArgumentException
		// (ArgumentChecks)
		ArgumentChecks.isFalse(endDate.isBefore(startDate),
			"End date cannot be before start date");

		// Ajusta la fecha al último día del mes
		LocalDate adjustedEnd = endDate
			.with(TemporalAdjusters.lastDayOfMonth());
		this.endDate = adjustedEnd;
		this.state = ContractState.TERMINATED;

		// Contar solo las nóminas cuya fecha sea anterior o igual a la fecha de
		// fin ajustada
		int payrollCount = 0;
		for (Payroll p : payrolls) {
			LocalDate pd = p.getDate();
			if (pd != null && (!pd.isAfter(adjustedEnd))) {
				payrollCount++;
			}
		}

		// Años computables según nóminas completas (12 nóminas = 1 año)
		int computableYears = payrollCount / 12;

		if (computableYears <= 0) {
			this.settlement = 0.0;
			return;
		}

		double dailySalary = this.annualBaseSalary / 365.0;
		this.settlement = computableYears * dailySalary
			* this.contractType.getCompensationDaysPerYear();
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	/* BACKDOOR */ void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public ContractType getContractType() {
		return contractType;
	}

	/* BACKDOOR */void _setContractType(ContractType contractType) {
		this.contractType = contractType;
	}

	public ProfessionalGroup getProfessionalGroup() {
		return professionalGroup;
	}

	/* BACKDOOR */void _setProfessionalGroup(
		ProfessionalGroup professionalGroup) {
		this.professionalGroup = professionalGroup;
	}

	public Set<Payroll> getPayrolls() {
		return new HashSet<Payroll>(payrolls);
	}

	/* BACKDOOR */Set<Payroll> _getPayrolls() {
		return payrolls;
	}

	@Override
	public String toString() {
		return "Contract [id=" + getId() + ", mechanic=" + mechanic.getNif()
			+ ", startDate=" + startDate + ", state=" + state + "]";
	}

}
