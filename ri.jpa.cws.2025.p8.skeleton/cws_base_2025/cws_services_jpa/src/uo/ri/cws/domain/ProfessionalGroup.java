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
@Table(name = "TProfessionalGroups")
public class ProfessionalGroup extends BaseEntity {
	@Column(unique = true)
	private String name;

	private Double trieniumPayment;

	private Double productivityRate;

	@OneToMany(mappedBy = "professionalGroup")
	private Set<Contract> contracts = new HashSet<>();

	ProfessionalGroup() {

	}

	public ProfessionalGroup(String name, Double trienniumSalary,
		Double productivityPlus) {
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isNotNull(trienniumSalary);
		ArgumentChecks.isNotNull(productivityPlus);
		ArgumentChecks.isTrue(trienniumSalary > 0);
		ArgumentChecks.isTrue(productivityPlus > 0);

		this.name = name;
		this.productivityRate = productivityPlus;
		this.trieniumPayment = trienniumSalary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getTrieniumPayment() {
		return trieniumPayment;
	}

	public void setTrieniumPayment(Double trieniumPayment) {
		this.trieniumPayment = trieniumPayment;
	}

	public Double getProductivityRate() {
		return productivityRate;
	}

	public void setProductivityRate(Double productivityRate) {
		this.productivityRate = productivityRate;
	}

	public Set<Contract> getContracts() {
		return new HashSet<Contract>(contracts);
	}

	/* BACKDOOR */Set<Contract> _getContracts() {
		return contracts;
	}

}
