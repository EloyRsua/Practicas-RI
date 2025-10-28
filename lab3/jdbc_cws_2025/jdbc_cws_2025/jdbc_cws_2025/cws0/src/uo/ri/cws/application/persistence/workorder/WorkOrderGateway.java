package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord> {

    /**
     * findActiveWorkOrderByMechanicId: Obtiene las órdenes de trabajo activas
     * (ASIGNADAS o ABIERTAS) asignadas a un mecánico específico.
     *
     * @param id String - Identificador del mecánico.
     * @return List<WorkOrderRecord> - Lista de órdenes de trabajo activas.
     *
     *         Ejemplo de uso: List<WorkOrderRecord> activeWOs =
     *         workOrderGateway.findActiveWorkOrderByMechanicId("M001");
     */
    public List<WorkOrderRecord> findActiveWorkOrderByMechanicId(String id);

    /**
     * findNotInvoicedWorkOrders: Obtiene las órdenes de trabajo finalizadas que
     * aún no han sido facturadas para un cliente identificado por su NIF.
     *
     * @param nif String - NIF del cliente.
     * @return List<WorkOrderRecord> - Lista de órdenes de trabajo no
     *         facturadas.
     *
     *         Ejemplo de uso: List<WorkOrderRecord> pendingWOs =
     *         workOrderGateway.findNotInvoicedWorkOrders("12345678A");
     */
    public List<WorkOrderRecord> findNotInvoicedWorkOrders(String nif);

    /**
     * findTotalForMechanicInMonth: Calcula la suma total de importes de las
     * órdenes de trabajo facturadas por un mecánico en un mes específico.
     *
     * @param mechanic_id String - Identificador del mecánico.
     * @param date        LocalDate - Fecha de referencia para el mes.
     * @return double - Total de importes facturados en ese mes.
     *
     *         Ejemplo de uso: double total =
     *         workOrderGateway.findTotalForMechanicInMonth("M001",
     *         LocalDate.of(2024, 5, 1));
     */
    public double findTotalForMechanicInMonth(String mechanic_id,
	LocalDate date);

    public static class WorkOrderRecord extends Record {
	public double amount;
	public LocalDateTime date;
	public String description;
	public String state;

	public String invoice_id;
	public String mechanic_id;
	public String vehicle_id;
    }
}
