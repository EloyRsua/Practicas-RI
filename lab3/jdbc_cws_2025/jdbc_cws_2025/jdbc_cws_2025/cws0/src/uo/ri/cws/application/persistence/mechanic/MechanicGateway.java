package uo.ri.cws.application.persistence.mechanic;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public interface MechanicGateway extends Gateway<MechanicRecord> {

    /**
     * 
     * @param nif
     * @return
     */
    public Optional<MechanicRecord> findByNif(String nif);

    public static class MechanicRecord extends Record {

	public String nif;
	public String name;
	public String surname;
    }
}
