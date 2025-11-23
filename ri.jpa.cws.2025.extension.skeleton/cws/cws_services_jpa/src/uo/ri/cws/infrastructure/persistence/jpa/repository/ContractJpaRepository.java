package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ContractJpaRepository extends BaseJpaRepository<Contract>
	implements ContractRepository {

	@Override
	public List<Contract> findAll() {
		// NO ASIGNADO
		return null;
	}

	@Override
	public List<Contract> findAllInForce() {
		// NO ASIGNADO
		return null;
	}

	@Override
	public List<Contract> findByMechanicId(String id) {
		// NO ASIGNADO
		return null;
	}

	@Override
	public List<Contract> findByProfessionalGroupId(String id) {
		// NO ASIGNADO
		return null;
	}

	@Override
	public List<Contract> findByContractTypeId(String id) {
		// NO ASIGNADO
		return null;
	}

	@Override
	public List<Contract> findAllInForceThisMonth(LocalDate present) {
		LocalDate endOfPreviousMonth = present.minusMonths(1)
			.withDayOfMonth(present.minusMonths(1)
				.lengthOfMonth());

		return Jpa.getManager()
			.createNamedQuery("Contract.findAllInForceThisMonth",
				Contract.class)
			.setParameter(1, endOfPreviousMonth)
			.getResultList();
	}

	@Override
	public List<Contract> findInforceContracts() {
		return Jpa.getManager()
			.createNamedQuery("Contract.findInForce", Contract.class)
			.getResultList();
	}

}
