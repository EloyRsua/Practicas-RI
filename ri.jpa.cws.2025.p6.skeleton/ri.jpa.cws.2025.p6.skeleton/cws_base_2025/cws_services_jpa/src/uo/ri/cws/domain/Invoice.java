package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class Invoice {
	public enum InvoiceState {
		NOT_YET_PAID, PAID
	}

	private static final LocalDate DATE_LIMIT = LocalDate.of(2012, 7, 1);

	private static final double LOWER_PERCENTAGE = 0.18;

	private static final double HIGHER_PERCENTAGE = 0.21;
	// natural attributes
	private Long number;
	private LocalDate date;
	private double amount;
	private double vat;
	private InvoiceState state = InvoiceState.NOT_YET_PAID;

	// accidental attributes
	private Set<WorkOrder> workOrders = new HashSet<>();
	private Set<Charge> charges = new HashSet<>();

	public Invoice(Long number) {
		this(number, LocalDate.now(), new ArrayList<WorkOrder>());
	}

	public Invoice(Long number, LocalDate date) {
		this(number, date, new ArrayList<WorkOrder>());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	// full constructor
	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isTrue(number >= 0);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotNull(workOrders);

		this.number = number;
		this.date = date;

		for (WorkOrder w : workOrders) {
			addWorkOrder(w);
		}
	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		double total = 0.0;

		for (WorkOrder w : workOrders) {
			total += w.getAmount();
		}

		if (date.isBefore(DATE_LIMIT)) {
			this.vat = LOWER_PERCENTAGE;
		} else {
			this.vat = HIGHER_PERCENTAGE;
		}

		this.amount = total * (1 + vat);
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount
	 * and vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 * @throws IllegalStateException if the workorder status is not FINISHED
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		ArgumentChecks.isNotNull(workOrder);

		StateChecks.isTrue(isNotSettled());
		StateChecks.isTrue(workOrder.isFinished());

		Associations.Bills.link(this, workOrder);
		workOrder.markAsInvoiced();
		workOrders.add(workOrder);
		computeAmount();
	}

	/**
	 * Removes a work order from the invoice, updates the workorder state and
	 * recomputes amount and vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException    if the invoice status is not
	 *                                  NOT_YET_PAID
	 * @throws IllegalArgumentException if the invoice does not contain the
	 *                                  workorder
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		ArgumentChecks.isNotNull(workOrder);

		StateChecks.isTrue(isNotSettled());
		ArgumentChecks.isTrue(workOrders.contains(workOrder));

		Associations.Bills.unlink(this, workOrder);
		workOrder.markBackToFinished();
		workOrders.remove(workOrder);
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * 
	 * @throws IllegalStateException if - Is already settled - Or the amounts
	 *                               paid with charges to payment means do not
	 *                               cover the total of the invoice
	 */
	public void settle() {
		// Tiene que estar todavia en estado "NO pagada"
		StateChecks.isTrue(isNotSettled());

		// Calculamos el amount de las charges y comprobamos que cubre la
		// factura
		double total = 0.0;
		for (Charge c : charges) {
			total += c.getAmount();
		}
		StateChecks.isTrue(total >= amount);

		// Pasamos el estado a Pagado
		this.state = InvoiceState.PAID;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public double getAmount() {
		return amount;
	}

	public boolean isNotSettled() {
		if (state.equals(InvoiceState.NOT_YET_PAID)) {
			return true;
		}
		return false;
	}

}
