package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TMechanics")

public class Mechanic extends BaseEntity {
	// natural attributes
	@Column(unique = true)
	private String nif;
	@Basic(optional = false)
	private String surname;
	@Basic(optional = false)
	private String name;

	// accidental attributes
	@OneToMany(mappedBy = "mechanic")
	private Set<WorkOrder> assigned = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<Intervention> interventions = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<Contract> contracts = new HashSet<>();

	Mechanic() {
	}

	public Mechanic(String nif, String surname, String name) {
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isNotBlank(surname);
		ArgumentChecks.isNotBlank(nif);

		this.nif = nif;
		this.surname = surname;
		this.name = name;
	}

	public Mechanic(String nif) {
		this(nif, "no_surname", "no_name");
	}

	public String getNif() {
		return nif;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>(assigned);
	}

	/* BACKDOOR */Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	/* BACKDOOR */Set<Intervention> _getInterventions() {
		return interventions;
	}

	/* BACKDOOR */Set<Contract> _getContracts() {
		return contracts;
	}

	public Set<Contract> getContracts() {
		return new HashSet<>(contracts);
	}

	/**
	 * Metodo axuiliar de modelo extendido
	 * 
	 * @return Contratos con estado INFORCE de todos los contratos asociados a
	 *         un mecanico
	 */
	/**
	 * Devuelve el contrato en vigor del mecánico, asegurando que solo quede
	 * uno. Si hay más de uno en vigor, conserva únicamente el más reciente.
	 */
	public Optional<Contract> getContractInForce() {

		if (contracts.isEmpty()) {
			return Optional.empty();
		}

		Contract active = findLatestActiveContract();

		if (active == null) {
			return Optional.empty();
		}

		closeOtherActiveContracts(active);

		return Optional.of(active);
	}

	/**
	 * Busca el contrato en vigor más reciente.
	 */
	private Contract findLatestActiveContract() {
		Contract latest = null;

		for (Contract c : contracts) {
			if (!c.isInForce()) {
				continue;
			}

			if (latest == null || c.getStartDate()
				.isAfter(latest.getStartDate())) {
				latest = c;
			}
		}
		return latest;
	}

	/**
	 * Finaliza todos los contratos en vigor excepto el indicado.
	 */
	private void closeOtherActiveContracts(Contract keep) {
		LocalDate today = LocalDate.now();

		for (Contract c : contracts) {
			if (c.isInForce() && c != keep) {
				c.terminate(today);
			}
		}
	}

	@Override
	public String toString() {
		return "Mechanic [nif=" + nif + ", surname=" + surname + ", name="
			+ name + "]";
	}

}
