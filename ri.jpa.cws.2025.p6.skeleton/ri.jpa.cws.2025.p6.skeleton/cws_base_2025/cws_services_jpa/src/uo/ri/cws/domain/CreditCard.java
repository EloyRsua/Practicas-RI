package uo.ri.cws.domain;

import java.time.LocalDate;

public class CreditCard extends PaymentMean {
    private String number;
    private String type;
    private LocalDate validThru;

    public CreditCard(String number, String type, LocalDate validThru) {
	// VALIDAC
	this.number = number;
	this.type = type;
	this.validThru = validThru;
    }

    public String getNumber() {
	return number;
    }

    public String getType() {
	return type;
    }

    public LocalDate getValidThru() {
	return validThru;
    }

    /**
     * A credit card can pay if is not outdated
     */
    @Override
    public boolean canPay(Double amount) {
	// TODO Auto-generated method stub
	return false;
    }

}
