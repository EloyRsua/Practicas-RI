package uo.ri.cws.application.persistence.professionalgroup;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.Record;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public interface ProfessionalGroupGateway
    extends Gateway<ProfessionalGroupRecord> {

    public Optional<ProfessionalGroupRecord> findByGroupName(String name);

    public class ProfessionalGroupRecord extends Record {
	public String name;
	public double productivityRate;
	public double trienniumPayment;
    }

}
