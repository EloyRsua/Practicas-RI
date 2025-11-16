package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCharges", uniqueConstraints = { @UniqueConstraint(columnNames = { "INVOICE_ID", "PAYMENTMEAN_ID" }) })

public class Charge extends BaseEntity {
    // natural attributes
    private double amount = 0.0;

    // accidental attributes
    @ManyToOne
    private Invoice invoice;
    @ManyToOne
    private PaymentMean paymentMean;

    Charge() {
    }

    public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
	ArgumentChecks.isNotNull(invoice);
	ArgumentChecks.isNotNull(paymentMean);
	ArgumentChecks.isTrue(amount >= 0);
	// store the amount
	// increment the paymentMean accumulated -> paymentMean.pay( amount )
	// link invoice, this and paymentMean

	this.amount = amount;
	paymentMean.pay(amount);
	Associations.Settles.link(invoice, this, paymentMean);
    }

    public double getAmount() {
	return amount;
    }

    public void setAmount(double amount) {
	this.amount = amount;
    }

    public Invoice getInvoice() {
	return invoice;
    }

    public PaymentMean getPaymentMean() {
	return this.paymentMean;
    }

    /* BACKDOOR */ void _setInvoice(Invoice invoice) {
	this.invoice = invoice;
    }

    /* BACKDOOR */ void _setPaymentMean(PaymentMean paymentMean) {
	this.paymentMean = paymentMean;
    }

    /**
     * Unlinks this charge and restores the accumulated to the payment mean
     * 
     * @throws IllegalStateException if the invoice is already settled
     */
    public void rewind() {
	// asserts the invoice is not in PAID status
	// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
	// unlinks invoice, this and paymentMean
    }

}
