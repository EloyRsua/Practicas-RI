package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TContractTypes")
public class ContractType extends BaseEntity {

	@Column(unique = true)
	private String name;

	private Double compensationDaysPerYear;

	@OneToMany(mappedBy = "contractType")
	private Set<Contract> contracts = new HashSet<>();

	ContractType() {
	}

	public ContractType(String name, double days) {
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isNotNull(days);
		ArgumentChecks.isTrue(days >= 0);

		this.compensationDaysPerYear = days;
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCompensationDaysPerYear() {
		return compensationDaysPerYear;
	}

	public void setCompensationDaysPerYear(Double compensationDaysPerYear) {
		this.compensationDaysPerYear = compensationDaysPerYear;
	}

	public Set<Contract> getContracts() {
		return new HashSet<Contract>(contracts);
	}

	/* BACKDOOR */Set<Contract> _getContracts() {
		return contracts;
	}

}
