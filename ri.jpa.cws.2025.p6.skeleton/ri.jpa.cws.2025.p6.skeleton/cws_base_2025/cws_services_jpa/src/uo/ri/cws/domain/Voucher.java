package uo.ri.cws.domain;

import java.util.Objects;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class Voucher extends PaymentMean {
    private String code;
    private double available = 0.0;
    private String description;

    public Voucher(String code, String description, double available) {
	ArgumentChecks.isNotBlank(code);
	ArgumentChecks.isNotNull(available);
	ArgumentChecks.isTrue(available >= 0);

	this.code = code;
	this.available = available;
	this.description = description;
    }

    /**
     * Augments the accumulated (super.pay(amount) ) and decrements the
     * available
     * 
     * @throws IllegalStateException if not enough available to pay
     */
    @Override
    public void pay(double amount) {
	StateChecks.isTrue(canPay(amount));
	super.pay(amount);
	available -= amount;
    }

    /**
     * A voucher can pay if it has enough available to pay the amount
     */
    @Override
    public boolean canPay(Double amount) {
	if (amount <= available) {
	    return true;
	}
	return false;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hash(code);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!super.equals(obj)) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Voucher other = (Voucher) obj;
	return Objects.equals(code, other.code);
    }

}