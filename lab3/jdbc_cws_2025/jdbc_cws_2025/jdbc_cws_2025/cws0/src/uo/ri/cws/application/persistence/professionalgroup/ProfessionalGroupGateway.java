package uo.ri.cws.application.persistence.professionalgroup;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public interface ProfessionalGroupGateway
    extends Gateway<ProfessionalGroupRecord> {

    /**
     * findByGroupName: Recupera un grupo profesional a partir de su nombre.
     *
     * @param name String - Nombre del grupo profesional a buscar.
     * @return Optional<ProfessionalGroupRecord> - Contiene el grupo profesional
     *         si existe, o vacío si no se encuentra.
     *
     *         Ejemplo de uso: Optional<ProfessionalGroupRecord> group =
     *         professionalGroupGateway.findByGroupName("Mecánicos Senior");
     */
    public Optional<ProfessionalGroupRecord> findByGroupName(String name);

    public class ProfessionalGroupRecord extends Record {
	public String name;
	public double productivityRate;
	public double trienniumPayment;
    }
}
