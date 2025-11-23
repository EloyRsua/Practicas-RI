package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

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
@Table(name = "TWorkorders", uniqueConstraints = {
	@UniqueConstraint(columnNames = { "DATE", "VEHICLE_ID" }) })
public class WorkOrder extends BaseEntity {
	public enum WorkOrderState {
		OPEN, ASSIGNED, FINISHED, INVOICED
	}

	// natural attributes
	private LocalDateTime date;
	private String description;

	private double amount = 0.0;
	@Enumerated(EnumType.STRING)
	private WorkOrderState state = WorkOrderState.OPEN;

	// accidental attributes
	@ManyToOne
	private Vehicle vehicle;
	@ManyToOne
	private Mechanic mechanic;
	@ManyToOne
	private Invoice invoice;
	@OneToMany(mappedBy = "workOrder")
	private Set<Intervention> interventions = new HashSet<>();

	WorkOrder() {
	}

	/**
	 * Changes it to INVOICED state given the right conditions This method is
	 * called from Invoice.addWorkOrder(...)
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not FINISHED, or -
	 *                               The work order is not linked with the
	 *                               invoice
	 */

	public void markAsInvoiced() {
		StateChecks.isTrue(state.equals(WorkOrderState.FINISHED));
		StateChecks.isNotNull(invoice, "WorkOrder not linked to an Invoice");

		state = WorkOrderState.INVOICED;
	}

	/**
	 * Given the right conditions unlinks the workorder and the mechanic,
	 * changes the state to FINISHED and computes the amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED
	 *                               state, or
	 */
	public void markAsFinished() {
		// Para poder marcarla como finalizada tiene que estar previamente
		// asignada

		StateChecks.isTrue(state.equals(WorkOrderState.ASSIGNED));

		Associations.Assigns.unlink(mechanic, this);

		// Calculamos el total de la workorder
		double total = 0;

		for (Intervention intervention : interventions) {
			total = total + intervention.getAmount();
		}

		amount = total;

		// La marcamos como terminada
		state = WorkOrderState.FINISHED;
	}

	/**
	 * Changes it back to FINISHED state given the right conditions This method
	 * is called from Invoice.removeWorkOrder(...)
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not INVOICED, or
	 */
	public void markBackToFinished() {
		// Solo se puede poner en finalizada si no esta facturada
		StateChecks.isTrue(state.equals(WorkOrderState.INVOICED));

		this.state = WorkOrderState.FINISHED;
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its state
	 * to ASSIGNED
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in OPEN state,
	 *                               or
	 */
	public void assignTo(Mechanic mechanic) {
		ArgumentChecks.isNotNull(mechanic);
		// Solo se la podemos asignar a un mecanico si esta workorder esta
		// abierta
		StateChecks.isTrue(state.equals(WorkOrderState.OPEN));
		Associations.Assigns.link(mechanic, this);
		state = WorkOrderState.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes its
	 * state back to OPEN
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED
	 *                               state
	 */
	public void unassign() {
		// Solo se la podemos desasignar a un mecanico si esta workorder esta
		// asigfanada
		StateChecks.isTrue(state.equals(WorkOrderState.ASSIGNED));
		Associations.Assigns.unlink(mechanic, this);
		state = WorkOrderState.OPEN;
	}

	/**
	 * In order to assign a work order to another mechanic it first have to be
	 * moved back to OPEN state.
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in FINISHED
	 *                               state
	 */
	public void reopen() {
		// Solo se la podemos reabrir si esta workorder esta terminada
		StateChecks.isTrue(state.equals(WorkOrderState.FINISHED));

		state = WorkOrderState.OPEN;
		amount = 0;
	}

	public WorkOrder(Vehicle vehicle, LocalDateTime date, String description) {
		ArgumentChecks.isNotBlank(description);
		ArgumentChecks.isNotNull(vehicle);
		ArgumentChecks.isNotNull(date);

		this.date = date.truncatedTo(ChronoUnit.MILLIS);
		this.description = description;
		// Las asociaciones siempre lo ultimo
		Associations.Fixes.link(vehicle, this);
	}

	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle, LocalDateTime.now(), description);
	}

	public WorkOrder(Vehicle vehicle) {
		this(vehicle, LocalDateTime.now(), "no-description");
	}

	public WorkOrder(Vehicle vehicle, LocalDateTime date) {
		this(vehicle, date, "no-description");
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	/* BACKDOOR */void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public String getDescription() {
		return description;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public LocalDateTime getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description
			+ ", vehicle=" + vehicle + "]";
	}

	public double getAmount() {
		if (state.equals(WorkOrderState.OPEN)
			|| state.equals(WorkOrderState.ASSIGNED)) {
			return 0;

		}
		return amount;
	}

	public boolean isFinished() {
		if (state.equals(WorkOrderState.FINISHED)) {
			return true;
		}
		return false;
	}

	public boolean isAssigned() {
		if (state.equals(WorkOrderState.ASSIGNED)) {
			return true;
		}
		return false;
	}

	public boolean isOpen() {
		if (state.equals(WorkOrderState.OPEN)) {
			return true;
		}
		return false;
	}

	public boolean isInvoiced() {
		if (state.equals(WorkOrderState.INVOICED)) {
			return true;
		}
		return false;
	}

	public WorkOrderState getState() {
		return state;
	}
}
