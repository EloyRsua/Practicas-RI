package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

    /**
     * update: Marca una orden de trabajo como facturada y actualiza su versión.
     *
     * @param t WorkOrderRecord - Objeto con los datos de la orden de trabajo a
     *          actualizar.
     * @throws PersistenceException - Si ocurre un error durante la
     *                              actualización.
     *
     *                              Ejemplo de uso: workOrder.invoice_id =
     *                              "I001"; workOrderGateway.update(workOrder);
     */
    @Override
    public void update(WorkOrderRecord t) throws PersistenceException {
	Timestamp now = new Timestamp(System.currentTimeMillis());
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TWORKORDERS_UPDATE"))) {

		pst.setTimestamp(1, now);
		pst.setString(2, t.invoice_id);
		pst.setString(3, t.id);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    /**
     * findById: Recupera una orden de trabajo a partir de su identificador
     * único.
     *
     * @param id String - Identificador de la orden de trabajo.
     * @return Optional<WorkOrderRecord> - Contiene la orden si existe, o vacío
     *         si no se encuentra.
     *
     *         Ejemplo de uso: Optional<WorkOrderRecord> wo =
     *         workOrderGateway.findById("WO001");
     */
    @Override
    public Optional<WorkOrderRecord> findById(String id)
	throws PersistenceException {
	Optional<WorkOrderRecord> om = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TWORKORDERS_FIND_BY_ID"))) {
		pst.setString(1, id);

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			WorkOrderRecord m = WorkOrderRecordAssembler.toRecord(
			    rs);
			om = Optional.of(m);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return om;
    }

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
    @Override
    public List<WorkOrderRecord> findActiveWorkOrderByMechanicId(String id) {
	List<WorkOrderRecord> listOfWorkorders = new ArrayList<>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence(
		    "TWORKORDERS_FIND_ACTIVE_BY_MECHANIC_ID"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			WorkOrderRecord wr = WorkOrderRecordAssembler.toRecord(
			    rs);
			listOfWorkorders.add(wr);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return listOfWorkorders;
    }

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
    @Override
    public List<WorkOrderRecord> findNotInvoicedWorkOrders(String nif) {
	List<WorkOrderRecord> result = new ArrayList<>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TWORKORDERS_FIND_NOT_INVOICED"))) {

		pst.setString(1, nif);

		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			WorkOrderRecord wr = WorkOrderRecordAssembler.toRecord(
			    rs);
			result.add(wr);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return result;
    }

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
    @Override
    public double findTotalForMechanicInMonth(String mechanic_id,
	LocalDate date) {
	double total = 0;
	YearMonth monthAndYear = YearMonth.from(date);

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence(
		    "TWORKORDERS_TOTAL_FOR_MECHANIC_FOR_MONTH"))) {
		pst.setString(1, mechanic_id);
		pst.setDate(2, Date.valueOf(monthAndYear.atDay(1)));
		pst.setDate(3, Date.valueOf(monthAndYear.atEndOfMonth()));

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			total = rs.getDouble("total");
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return total;
    }

    @Override
    public void add(WorkOrderRecord t) throws PersistenceException {
	// No asignado

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// No asignado

    }

    @Override
    public List<WorkOrderRecord> findAll() throws PersistenceException {
	// No asignado
	return null;
    }

}
