package uo.ri.cws.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
public class CreditCard extends PaymentMean {
	@Column(unique = true)
	private String number;
	private String type;
	private LocalDate validThru;

	public CreditCard(String number, String type, LocalDate validThru) {
		ArgumentChecks.isNotBlank(number);
		ArgumentChecks.isNotBlank(type);
		ArgumentChecks.isNotNull(validThru);

		this.number = number;
		this.type = type;
		this.validThru = validThru;
	}

	CreditCard() {
	}

	/**
	 * A credit card can pay if is not outdated
	 */
	@Override
	public boolean canPay(Double amount) {
		return validThru.isAfter(LocalDate.now());
	}

	@Override
	public void pay(double amount) {
		StateChecks.isTrue(canPay(amount));
		super.pay(amount);
	}

	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type
			+ ", validThru=" + validThru + "]";
	}

	public String getNumber() {
		return this.number;
	}

	public String getType() {
		return this.type;
	}

	public LocalDate getValidThru() {
		return this.validThru;
	}

}