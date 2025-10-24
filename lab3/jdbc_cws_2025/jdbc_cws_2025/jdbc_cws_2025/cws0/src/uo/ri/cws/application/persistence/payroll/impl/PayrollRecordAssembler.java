package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public class PayrollRecordAssembler {

    public static PayrollRecord toRecord(ResultSet rs) throws SQLException {
	PayrollRecord r = new PayrollRecord();

	r.id = rs.getString("ID");
	r.version = rs.getLong("VERSION");
	r.contract_id = rs.getString("CONTRACT_ID");

	java.sql.Date sqlDate = rs.getDate("DATE");
	r.date = (sqlDate != null) ? sqlDate.toLocalDate() : null;

	// Earnings
	r.base_Salary = rs.getDouble("BASESALARY");
	r.extra_Salary = rs.getDouble("EXTRASALARY");
	r.productivity_Earning = rs.getDouble("PRODUCTIVITYEARNING");
	r.triennium_Earning = rs.getDouble("TRIENNIUMEARNING");

	// Deductions
	r.tax_Deduction = rs.getDouble("TAXDEDUCTION");
	r.nic_Deduction = rs.getDouble("NICDEDUCTION");

	return r;
    }
}
