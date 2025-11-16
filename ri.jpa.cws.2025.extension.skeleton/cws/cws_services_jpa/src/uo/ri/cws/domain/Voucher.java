package uo.ri.cws.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(name = "TVouchers")
public class Voucher extends PaymentMean {
    @Column(unique = true)
    private String code;
    private double available = 0.0;
    @Basic(optional = false)
    private String description;

    Voucher() {

    }

    public Voucher(String code, String description, double available) {
	ArgumentChecks.isNotBlank(code);
	ArgumentChecks.isNotNull(available);
	ArgumentChecks.isTrue(available >= 0);

	this.code = code;
	this.available = available;
	this.description = description;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public double getAvailable() {
	return available;
    }

    public void setAvailable(double available) {
	this.available = available;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * Augments the accumulated (super.pay(amount) ) and decrements the available
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
    public String toString() {
	return "Voucher [code=" + code + ", available=" + available + ", description=" + description + "]";
    }

}