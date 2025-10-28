package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Vehicle {
    private String plateNumber;
    private String make;
    private String model;

    private Client client;
    private VehicleType vehicle;
    private Set<WorkOrder> workOrders = new HashSet<WorkOrder>();

    public Vehicle(String plateNumber, String make, String model) {
	ArgumentChecks.isNotBlank(plateNumber);
	ArgumentChecks.isNotBlank(make);
	ArgumentChecks.isNotBlank(model);
	this.plateNumber = plateNumber;
	this.make = make;
	this.model = model;
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
	return vehicle;
    }

    /* BACKDOOR */void _setVehicleType(VehicleType vehicle) {
	this.vehicle = vehicle;
    }

    public Set<WorkOrder> getWorkOrders() {
	return new HashSet<>(workOrders);
    }

    /* BACKDOOR */Set<WorkOrder> _getWorkOrders() {
	return workOrders;
    }

    @Override
    public int hashCode() {
	return Objects.hash(plateNumber);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Vehicle other = (Vehicle) obj;
	return Objects.equals(plateNumber, other.plateNumber);
    }

    @Override
    public String toString() {
	return "Vehicle [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model + "]";
    }

}
