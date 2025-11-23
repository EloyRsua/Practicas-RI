package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.domain.ContractType;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;

public class ContractTypeJpaRepository extends BaseJpaRepository<ContractType>
	implements ContractTypeRepository {

	@Override
	public Optional<ContractType> findByName(String name) {
		// NO ASIGNADO
		return null;
	}

}
