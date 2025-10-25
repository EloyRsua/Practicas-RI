package uo.ri.cws.application.service.profesionalGroup;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracts.ContractGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProfessionalGroup implements Command<Void> {

    private String name;
    private ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
    private ContractGateway cg = Factories.persistence.forContract();

    public DeleteProfessionalGroup(String name) {
	ArgumentChecks.isNotBlank(name);
	this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProfessionalGroupRecord> or = pgg.findByGroupName(name);
	BusinessChecks.exists(or, "No profesional groups with that name");
	String id = or.get().id;
	List<String> list = cg.findByProfesionalGroup(id);
	BusinessChecks.isEmpty(list,
	    "ProfessionalGroup has contracts assigned");
	pgg.remove(id);
	return null;
    }

}
