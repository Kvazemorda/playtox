package com.playtox.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class ProductEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
    @Column String name;
    @Column String description;
    @Column BigDecimal cost;
    @Column int quantity;

    public ProductEntity() {
    }

    public ProductEntity(String name, String description, BigDecimal cost, int quantity) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity)) return false;

        ProductEntity that = (ProductEntity) o;

        if (id != that.id) return false;
        if (quantity != that.quantity) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + " " + cost + " на складе " + quantity;
    }
}
