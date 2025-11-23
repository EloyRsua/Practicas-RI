package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class PayrollJpaRepository extends BaseJpaRepository<Payroll>
	implements PayrollRepository {

	@Override
	public List<Payroll> findByContract(String contractId) {
		// NO ASIGNADO
		return null;
	}

	@Override
	public List<Payroll> findLastMonthPayrolls() {
		LocalDate currentDate = LocalDate.now();
		LocalDate previousMonth = currentDate.minusMonths(1);

		LocalDate startDate = previousMonth.withDayOfMonth(1);
		LocalDate endDate = previousMonth
			.withDayOfMonth(previousMonth.lengthOfMonth());

		return Jpa.getManager()
			.createNamedQuery("Payroll.findByDate", Payroll.class)
			.setParameter(2, startDate)
			.setParameter(1, endDate)
			.getResultList();
	}

	@Override
	public Optional<Payroll> findLastPayrollByMechanicId(String mechanicId) {
		// NO ASIGNADO
		return null;
	}

	@Override
	public List<Payroll> findByProfessionalGroupName(String name) {
		return Jpa.getManager()
			.createNamedQuery("Payroll.findByProfessionalGroupName",
				Payroll.class)
			.setParameter(1, name)
			.getResultList();
	}

	@Override
	public List<Payroll> findByMechanicId(String mId) {
		return Jpa.getManager()
			.createNamedQuery("Payroll.findByMechanicId", Payroll.class)
			.setParameter(1, mId)
			.getResultList();
	}

	@Override
	public Optional<Payroll> findByContractIdAndDate(String id,
		LocalDate date) {
		// NO ASIGNADO
		return null;
	}

	@Override
	public List<Payroll> findByMechanicIdInMonth(String mechanicId) {
		LocalDate currentDate = LocalDate.now();
		LocalDate previousMonth = currentDate.minusMonths(1);

		LocalDate startDate = previousMonth.withDayOfMonth(1);
		LocalDate endDate = previousMonth
			.withDayOfMonth(previousMonth.lengthOfMonth());

		return Jpa.getManager()
			.createNamedQuery("Payroll.findByMechanicIdInMonth", Payroll.class)
			.setParameter(1, startDate)
			.setParameter(2, endDate)
			.setParameter(3, mechanicId)
			.getResultList();
	}

	@Override
	public List<Payroll> findByDate(LocalDate lastDay) {
		return Jpa.getManager()
			.createNamedQuery("Payroll.findByEndDate", Payroll.class)
			.setParameter(1, lastDay)
			.getResultList();
	}
}
