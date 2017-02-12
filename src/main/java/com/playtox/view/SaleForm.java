package com.playtox.view;

import com.playtox.entity.PurchaseEntity;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class SaleForm extends HorizontalLayout {
    Label labelLoginUser, labelNameProduct, labelDateOfBuy, labelCostOfBuy, labelQuantityOfBuy;
    PurchaseEntity purchaseEntity;

    public SaleForm(PurchaseEntity purchaseEntity) {
        this.purchaseEntity = purchaseEntity;
        createForm();
    }

    private void createForm(){
        setMargin(true);
        setSpacing(true);
        labelLoginUser = new Label(purchaseEntity.getUserEntity().getLogin());
        labelNameProduct = new Label(purchaseEntity.getProductEntity().getName());
        labelDateOfBuy = new Label(String.valueOf(purchaseEntity.getDateOfBuy()));
        labelCostOfBuy = new Label(String.valueOf(purchaseEntity.getCostOfBuy()));
        labelQuantityOfBuy = new Label(String.valueOf(labelQuantityOfBuy));
        addComponents(labelLoginUser, labelNameProduct, labelDateOfBuy, labelCostOfBuy, labelQuantityOfBuy);
    }
}
