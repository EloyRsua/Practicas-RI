package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TCashes")
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