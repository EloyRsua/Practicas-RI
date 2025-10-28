package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Intervention {
	// natural attributes
	private LocalDateTime date;
	private int minutes;

	// accidental attributes
	private WorkOrder workOrder;
	private Mechanic mechanic;
	private Set<Substitution> substitutions = new HashSet<>();

	public Intervention(Mechanic mechanic, WorkOrder workOrder,
		LocalDateTime date, int minutes) {
		ArgumentChecks.isNotNull(workOrder);
		ArgumentChecks.isNotNull(mechanic);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isTrue(minutes >= 0);

		this.date = date.truncatedTo(ChronoUnit.MILLIS);
		this.minutes = minutes;
		Associations.Intervenes.link(workOrder, this, mechanic);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		this(mechanic, workOrder, LocalDateTime.now(), minutes);
	}

	public LocalDateTime getDate() {
		return date;
	}

	public int getMinutes() {
		return minutes;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	// Atributo derivado, calculado con el ammount de workorder y de
	// substitution
	public double getAmount() {

		// 1 Sacamos el precio total de la mano de obra
		VehicleType vehicleType = workOrder.getVehicle()
			.getVehicleType();
		double labourPrice = vehicleType.getPricePerHour() * (minutes / 60.0);

		// 2 Sacamos el precio total de TODAS LAS SUSTITUCIONES
		double substitutionsPrice = 0;
		for (Substitution substitution : substitutions) {
			substitutionsPrice = substitutionsPrice + substitution.getAmount();
		}

		// 3 Sumamos los dos precios para tener un total
		return labourPrice + substitutionsPrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, mechanic, minutes);
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
		Intervention other = (Intervention) obj;
		return Objects.equals(date, other.date)
			&& Objects.equals(mechanic, other.mechanic)
			&& minutes == other.minutes;
	}

	@Override
	public String toString() {
		return "Intervention [date=" + date + ", minutes=" + minutes
			+ ", workOrder=" + workOrder + ", mechanic=" + mechanic + "]";
	}

}
