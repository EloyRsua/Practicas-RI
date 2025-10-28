package uo.ri.cws.application.persistence.contracts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.contracts.ContractGateway.ContractRecord;

public interface ContractGateway extends Gateway<ContractRecord> {

    /**
     * findActiveContractByMechanicId: Recupera los contratos activos (en vigor
     * o terminados) de un mecánico específico.
     *
     * @param id String - Identificador del mecánico.
     * @return List<ContractRecord> - Lista de contratos activos del mecánico.
     *
     *         Ejemplo de uso: List<ContractRecord> activeContracts =
     *         contractGateway.findActiveContractByMechanicId("M001");
     */
    public List<ContractRecord> findActiveContractByMechanicId(String id);

    public static class ContractRecord extends Record {
	public double anualBaseSalary;
	public double settlement;
	public LocalDateTime startDate;
	public LocalDateTime endDate;
	public String state;
	public double taxRate;
	public String contractType_id;
	public String mechanic_id;
	public String professionalGroup_id;
    }

    /**
     * findContractsByMonth: Obtiene los IDs de los contratos que han estado
     * activos durante un mes específico.
     *
     * @param previousMonth LocalDate - Fecha representativa del mes a
     *                      consultar.
     * @return List<String> - Lista de IDs de contratos activos en ese mes.
     *
     *         Ejemplo de uso: List<String> contracts =
     *         contractGateway.findContractsByMonth(LocalDate.of(2024, 9, 1));
     */
    public List<String> findContractsByMonth(LocalDate previousMonth);

    /**
     * findByProfesionalGroup: Obtiene los IDs de los contratos asociados a un
     * grupo profesional.
     *
     * @param id String - Identificador del grupo profesional.
     * @return List<String> - Lista de IDs de contratos asociados al grupo.
     *
     *         Ejemplo de uso: List<String> contracts =
     *         contractGateway.findByProfesionalGroup("PG001");
     */
    public List<String> findByProfesionalGroup(String id);

    /**
     * findContractsInForce: Recupera todos los contratos que están actualmente
     * en vigor.
     *
     * @return List<ContractRecord> - Lista de contratos en vigor.
     *
     *         Ejemplo de uso: List<ContractRecord> contractsInForce =
     *         contractGateway.findContractsInForce();
     */
    public List<ContractRecord> findContractsInForce();
}
