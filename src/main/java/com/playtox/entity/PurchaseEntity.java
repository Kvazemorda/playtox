package com.playtox.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
public class PurchaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;
    @ManyToOne private UserEntity userEntity;
    @ManyToOne private ProductEntity productEntity;
    @Temporal(TemporalType.DATE) private Date dateOfBuy;
    @Column private BigDecimal costOfBuy;
    @Column private int quantityOfBuy;

    public PurchaseEntity() {
    }

    public PurchaseEntity(UserEntity userEntity, ProductEntity productEntity, Date dateOfBuy, BigDecimal costOfBuy, int quantityOfBuy) {
        this.userEntity = userEntity;
        this.productEntity = productEntity;
        this.dateOfBuy = dateOfBuy;
        this.costOfBuy = costOfBuy;
        this.quantityOfBuy = quantityOfBuy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public Date getDateOfBuy() {
        return dateOfBuy;
    }

    public void setDateOfBuy(Date dateOfBuy) {
        this.dateOfBuy = dateOfBuy;
    }

    public int getQuantityOfBuy() {
        return quantityOfBuy;
    }

    public void setQuantityOfBuy(int quantityOfBuy) {
        this.quantityOfBuy = quantityOfBuy;
    }

    public BigDecimal getCostOfBuy() {
        return costOfBuy;
    }

    public void setCostOfBuy(BigDecimal costOfBuy) {
        this.costOfBuy = costOfBuy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseEntity)) return false;

        PurchaseEntity purchaseEntity = (PurchaseEntity) o;

        if (id != purchaseEntity.id) return false;
        if (quantityOfBuy != purchaseEntity.quantityOfBuy) return false;
        if (dateOfBuy != null ? !dateOfBuy.equals(purchaseEntity.dateOfBuy) : purchaseEntity.dateOfBuy != null) return false;
        if (productEntity != null ? !productEntity.equals(purchaseEntity.productEntity) : purchaseEntity.productEntity != null)
            return false;
        if (userEntity != null ? !userEntity.equals(purchaseEntity.userEntity) : purchaseEntity.userEntity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userEntity != null ? userEntity.hashCode() : 0;
        result = 31 * result + (productEntity != null ? productEntity.hashCode() : 0);
        result = 31 * result + (dateOfBuy != null ? dateOfBuy.hashCode() : 0);
        result = 32 * result + (costOfBuy != null ? costOfBuy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseEntity{" +
                "userEntity=" + userEntity +
                ", productEntity=" + productEntity +
                ", quantityOfBuy=" + quantityOfBuy +
                '}';
    }
}
