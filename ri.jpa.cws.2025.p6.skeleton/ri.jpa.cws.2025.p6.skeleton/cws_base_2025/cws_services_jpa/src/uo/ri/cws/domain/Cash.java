package uo.ri.cws.domain;

public class Cash extends PaymentMean {

    /**
     * A cash can always pay
     */
    @Override
    public boolean canPay(Double amount) {
	return true;
    }

    public Cash(Client client) {
	Associations.Holds.link(this, client);
    }

    @Override
    public String toString() {
	return "Cash [toString()=" + super.toString() + "]";
    }

}
