package uo.ri.cws.application.invoice.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.jdbc.Jdbc;

public class FindNotInvoicedWorkOrdersByClient {

    private static final String SQL_FIND_NOT_INVOICED = "SELECT a.id, a.description, a.date, a.state, a.amount "
	+ "FROM TWorkOrders a " + "JOIN TVehicles v ON a.vehicle_id = v.id "
	+ "JOIN TClients c ON v.client_id = c.id "
	+ "WHERE a.state = 'FINISHED' AND c.nif LIKE ?";

    private String nif;

    public FindNotInvoicedWorkOrdersByClient(String nif) {
	ArgumentChecks.isNotNull(nif);
	this.nif = nif;
    }

    public List<InvoicingWorkOrderDto> execute() {

	List<InvoicingWorkOrderDto> result = new ArrayList<>();

	try (Connection c = Jdbc.createThreadConnection();
	    PreparedStatement pst = c.prepareStatement(SQL_FIND_NOT_INVOICED)) {

	    pst.setString(1, nif);

	    try (ResultSet rs = pst.executeQuery()) {
		while (rs.next()) {
		    InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
		    dto.id = rs.getString("id");
		    dto.description = rs.getString("description");
		    dto.date = rs.getTimestamp("date")
				 .toLocalDateTime();
		    dto.state = rs.getString("state");
		    dto.amount = rs.getDouble("amount");

		    result.add(dto);
		}
	    }

	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

	return result;
    }
}
