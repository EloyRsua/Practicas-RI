package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
public class VehicleType extends BaseEntity {
	// natural attributes
	@Column(unique = true)
	private String name;
	private double pricePerHour;

	// accidental attributes
	@OneToMany(mappedBy = "vehicle")
	private Set<Vehicle> vehicles = new HashSet<>();

	VehicleType() {
	}

	public VehicleType(String name, double pricePerHour) {
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isNotNull(pricePerHour);
		ArgumentChecks.isTrue(pricePerHour >= 0);
		this.name = name;
		this.pricePerHour = pricePerHour;
	}

	public VehicleType(String name) {
		this(name, 1.0);
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	/* BACKDOOR */Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	public String getName() {
		return name;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	@Override
	public String toString() {
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour
			+ "]";
	}

}
