package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public interface PayrollGateway extends Gateway<PayrollRecord> {

    public List<PayrollRecord> findPayrollsByProfessionalGroup(String name);

    public List<PayrollRecord> findPayrollsByMechanicId(String mechanicId);

    public List<PayrollRecord> findPayrollsByLocalDate(LocalDate date);

    public class PayrollRecord extends Record {
	public String contract_id;
	public LocalDate date;
	public double base_Salary;
	public double extra_Salary;
	public double productivity_Earning;
	public double triennium_Earning;
	public double nic_Deduction;
	public double tax_Deduction;
    }

    public List<PayrollRecord> findPayrollsByLocalDateAndMechanicId(
	String mechanicId, LocalDate date);
}
