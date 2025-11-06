package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TSpareparts")
public class SparePart extends BaseEntity {
    // natural attributes
    @Column(unique = true)
    private String code;
    @Basic(optional = false)
    private String description;
    private double price;
    private int stock;
    private int minStock;
    private int maxStock;

    // accidental attributes
    @OneToMany(mappedBy = "sparePart")
    private Set<Substitution> substitutions = new HashSet<>();

    SparePart() {
    }

    public SparePart(String code, String description, double price, int stock, int minStock, int maxStock) {
	ArgumentChecks.isNotBlank(code);
	ArgumentChecks.isNotBlank(description);
	ArgumentChecks.isNotNull(price);
	ArgumentChecks.isTrue(price >= 0);
	ArgumentChecks.isNotNull(stock);
	ArgumentChecks.isTrue(stock >= 0);
	ArgumentChecks.isNotNull(minStock);
	ArgumentChecks.isTrue(minStock >= 0);
	ArgumentChecks.isNotNull(maxStock);
	ArgumentChecks.isTrue(maxStock >= 0 && maxStock >= minStock);

	this.code = code;
	this.description = description;
	this.price = price;
	this.stock = stock;
	this.minStock = minStock;
	this.maxStock = maxStock;
    }

    public SparePart(String code, String description, double price) {
	this(code, description, price, 0, 0, 0);
    }

    public SparePart(String code) {
	this(code, "no-description", 0);
    }

    public String getCode() {
	return code;
    }

    public String getDescription() {
	return description;
    }

    public double getPrice() {
	return price;
    }

    public int getStock() {
	return stock;
    }

    public int getMinStock() {
	return minStock;
    }

    public int getMaxStock() {
	return maxStock;
    }

    public Set<Substitution> getSubstitutions() {
	return new HashSet<>(substitutions);
    }

    Set<Substitution> _getSubstitutions() {
	return substitutions;
    }

    @Override
    public String toString() {
	return "SparePart [code=" + code + ", description=" + description + ", price=" + price + ", stock=" + stock + ", minStock=" + minStock + ", maxStock=" + maxStock + "]";
    }

}
