package uo.ri.cws.application.persistence.mechanic;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public interface MechanicGateway extends Gateway<MechanicRecord> {

    /**
     * findByNif: Recupera un mecánico a partir de su NIF.
     *
     * @param nif String - NIF del mecánico a buscar.
     * @return Optional<MechanicRecord> - Contiene el mecánico si existe, o
     *         vacío si no se encuentra.
     *
     *         Ejemplo de uso: Optional<MechanicRecord> mechanic =
     *         mechanicGateway.findByNif("12345678A");
     */
    public Optional<MechanicRecord> findByNif(String nif);

    public static class MechanicRecord extends Record {
	public String nif;
	public String name;
	public String surname;
    }
}
