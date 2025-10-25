package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord> {

    /**
     * Devuelve una lista con todas las ordenes de trabajo activas o abiertas de
     * un mecánico
     * 
     * @param id
     * @return Lista con las workorders asignadas a un mecánico
     */
    public List<WorkOrderRecord> findActiveWorkOrderByMechanicId(String id);

    public List<WorkOrderRecord> findNotInvoicedWorkOrders(String nif);

    public static class WorkOrderRecord extends Record {

	public double amount;
	public LocalDateTime date;
	public String description;
	public String state;

	public String invoice_id;
	public String mechanic_id;
	public String vehicle_id;
    }

    public double findTotalForMechanicInMonth(String mechanic_id,
	LocalDate date);
}
