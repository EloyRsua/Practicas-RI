package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
public class Client extends BaseEntity {

    @Column(unique = true)
    private String nif;
    @Basic(optional = false)
    private String name;
    @Basic(optional = false)
    private String surname;
    private String email;
    private String phone;
    private Address address;

    @OneToMany(mappedBy = "client")
    private Set<Vehicle> vehicles = new HashSet<Vehicle>();
    @OneToMany(mappedBy = "client")
    private Set<PaymentMean> paymentMeans = new HashSet<PaymentMean>();

    public Client(String nif, String name, String surname, String email, String phone, Address address) {
	ArgumentChecks.isNotBlank(nif);
	ArgumentChecks.isNotBlank(name);
	ArgumentChecks.isNotBlank(surname);
	ArgumentChecks.isNotBlank(email);
	ArgumentChecks.isNotBlank(phone);
	ArgumentChecks.isNotNull(address);

	this.nif = nif;
	this.name = name;
	this.surname = surname;
	this.email = email;
	this.phone = phone;
	this.address = address;
    }

    public Client(String nif, String name, String surname) {
	this(nif, name, surname, "no-email", "no-phone", new Address("no-street", "no-city", "no-zip"));
    }

    Client() {
    }

    public String getNif() {
	return nif;
    }

    public String getName() {
	return name;
    }

    public String getSurname() {
	return surname;
    }

    public String getEmail() {
	return email;
    }

    public String getPhone() {
	return phone;
    }

    public Address getAddress() {
	return address;
    }

    /* BACKDOOR */ Set<Vehicle> _getVehicles() {
	return vehicles;
    }

    public Set<Vehicle> getVehicles() {
	return new HashSet<>(vehicles);
    }

    Set<PaymentMean> _getPaymentMeans() {
	return paymentMeans;
    }

    public Set<PaymentMean> getPaymentMeans() {
	return new HashSet<>(paymentMeans);
    }

    @Override
    public int hashCode() {
	return Objects.hash(nif);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Client other = (Client) obj;
	return Objects.equals(nif, other.nif);
    }

    @Override
    public String toString() {
	return "Client [nif=" + nif + ", name=" + name + ", surname=" + surname + ", email=" + email + ", phone=" + phone + ", address=" + address + "]";
    }

}
