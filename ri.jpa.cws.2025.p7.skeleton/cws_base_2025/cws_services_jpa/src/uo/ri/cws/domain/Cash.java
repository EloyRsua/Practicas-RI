package uo.ri.cws.domain;

import jakarta.persistence.Entity;

@Entity
public class Cash extends PaymentMean {

    Cash() {

    }

    public Cash(Client client) {
	// Se asocia con clientes
	Associations.Holds.link(this, client);
    }

    /**
     * A cash can always pay
     */
    @Override
    public boolean canPay(Double amount) {
	return true;
    }

}