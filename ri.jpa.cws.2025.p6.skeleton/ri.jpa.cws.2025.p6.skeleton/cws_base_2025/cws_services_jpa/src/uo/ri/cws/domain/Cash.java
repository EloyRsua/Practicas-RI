package uo.ri.cws.domain;

public class Cash extends PaymentMean {

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