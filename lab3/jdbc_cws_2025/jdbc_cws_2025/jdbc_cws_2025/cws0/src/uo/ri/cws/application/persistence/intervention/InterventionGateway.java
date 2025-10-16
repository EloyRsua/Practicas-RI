package uo.ri.cws.application.persistence.intervention;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {
    public List<InterventionRecord> findInterventionsByMechanicId(String id);

    public static class InterventionRecord extends Record {
	public LocalDateTime date;
	public int minutes;
	public String mechanic_id;
	public String workOrder_id;
    }
}
