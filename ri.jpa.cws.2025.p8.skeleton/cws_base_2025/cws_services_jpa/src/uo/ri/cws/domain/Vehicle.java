package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVehicles")
public class Vehicle extends BaseEntity {
    @Column(unique = true)
    private String plateNumber;
    @Basic(optional = false)
    private String make;
    @Basic(optional = false)
    private String model;

    @ManyToOne
    private Client client;
    @ManyToOne
    private VehicleType vehicletype;
    @OneToMany(mappedBy = "vehicle")
    private Set<WorkOrder> workOrders = new HashSet<WorkOrder>();

    Vehicle() {
    }

    public Vehicle(String plateNumber, String make, String model) {
	ArgumentChecks.isNotBlank(plateNumber);
	ArgumentChecks.isNotBlank(make);
	ArgumentChecks.isNotBlank(model);
	this.plateNumber = plateNumber;
	this.make = make;
	this.model = model;
    }

    public Vehicle(String plateNumber) {
	this(plateNumber, "no-make", "no-model");
    }

    public String getPlateNumber() {
	return plateNumber;
    }

    public String getMake() {
	return make;
    }

    public String getModel() {
	return model;
    }

    public Client getClient() {
	return client;
    }

    /* BACKDOOR */void _setClient(Client client) {
	this.client = client;
    }

    public VehicleType getVehicleType() {
	return vehicletype;
    }

    /* BACKDOOR */void _setVehicleType(VehicleType vehicle) {
	this.vehicletype = vehicle;
    }

    public Set<WorkOrder> getWorkOrders() {
	return new HashSet<>(workOrders);
    }

    /* BACKDOOR */Set<WorkOrder> _getWorkOrders() {
	return workOrders;
    }

    @Override
    public String toString() {
	return "Vehicle [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model + "]";
    }

}
