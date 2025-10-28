package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public interface PayrollGateway extends Gateway<PayrollRecord> {

    /**
     * findPayrollsByProfessionalGroup: Obtiene todas las nóminas asociadas a un
     * grupo profesional.
     *
     * @param name String - Nombre del grupo profesional.
     * @return List<PayrollRecord> - Lista de nóminas del grupo profesional.
     *
     *         Ejemplo de uso: List<PayrollRecord> payrolls =
     *         payrollGateway.findPayrollsByProfessionalGroup("Mecánicos
     *         Senior");
     */
    public List<PayrollRecord> findPayrollsByProfessionalGroup(String name);

    /**
     * findPayrollsByMechanicId: Obtiene todas las nóminas asociadas a un
     * mecánico específico.
     *
     * @param mechanicId String - Identificador del mecánico.
     * @return List<PayrollRecord> - Lista de nóminas del mecánico.
     *
     *         Ejemplo de uso: List<PayrollRecord> payrolls =
     *         payrollGateway.findPayrollsByMechanicId("M001");
     */
    public List<PayrollRecord> findPayrollsByMechanicId(String mechanicId);

    /**
     * findPayrollsByLocalDate: Obtiene todas las nóminas generadas en un mes
     * específico.
     *
     * @param date LocalDate - Fecha representativa del mes.
     * @return List<PayrollRecord> - Lista de nóminas en el mes indicado.
     *
     *         Ejemplo de uso: List<PayrollRecord> payrolls =
     *         payrollGateway.findPayrollsByLocalDate(LocalDate.of(2024, 9, 1));
     */
    public List<PayrollRecord> findPayrollsByLocalDate(LocalDate date);

    /**
     * findPayrollsByLocalDateAndMechanicId: Obtiene las nóminas de un mecánico
     * en un mes específico.
     *
     * @param mechanicId String - Identificador del mecánico.
     * @param date       LocalDate - Fecha representativa del mes.
     * @return List<PayrollRecord> - Lista de nóminas del mecánico en ese mes.
     *
     *         Ejemplo de uso: List<PayrollRecord> payrolls =
     *         payrollGateway.findPayrollsByLocalDateAndMechanicId("M001",
     *         LocalDate.of(2024, 9, 1));
     */
    public List<PayrollRecord> findPayrollsByLocalDateAndMechanicId(
	String mechanicId, LocalDate date);

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
}
