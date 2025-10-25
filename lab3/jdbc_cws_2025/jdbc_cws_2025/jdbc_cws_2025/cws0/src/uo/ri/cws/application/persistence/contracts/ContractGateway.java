package uo.ri.cws.application.persistence.contracts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.contracts.ContractGateway.ContractRecord;

public interface ContractGateway extends Gateway<ContractRecord> {

    public List<ContractRecord> findActiveContractByMechanicId(String id);

    public static class ContractRecord extends Record {
	public double anualBaseSalary;
	public double settlement;
	public LocalDateTime startDate;
	public String state;
	public double taxRate;
	public String contractType_id;
	public String mechanic_id;
	public String professionalGroup_id;

    }

    public List<String> findContractsByMonth(LocalDate previousMonth);
}
