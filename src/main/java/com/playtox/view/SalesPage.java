package com.playtox.view;


import com.playtox.dao.PurchaseDAO;
import com.playtox.entity.PurchaseEntity;
import com.playtox.entity.UserEntity;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;

public class SalesPage extends VerticalLayout implements View {
    UserEntity userEntity;
    PurchaseDAO purchaseDAO;

    public SalesPage(UserEntity userEntity, PurchaseDAO purchaseDAO) {
        this.userEntity = userEntity;
        this.purchaseDAO = purchaseDAO;
        createPage();
    }

    public void createPage(){
        HashSet<PurchaseEntity> set = getSetOfPurchase();
        if(set != null){
            Table table = new Table();
            table.addContainerProperty("Date", Date.class, null);
            table.addContainerProperty("NameUser", String.class, null);
            table.addContainerProperty("NameProduct", String.class, null);
            table.addContainerProperty("Cost", BigDecimal.class, null);
            table.addContainerProperty("Quantity", Integer.class, null);
            int count = 1;
            for(PurchaseEntity purchaseEntity: set){
                //addComponent(new SaleForm(purchaseEntity));
                table.addItem(new Object[]{purchaseEntity.getDateOfBuy(),
                        purchaseEntity.getUserEntity().getLogin(),
                        purchaseEntity.getProductEntity().getName(),
                        purchaseEntity.getCostOfBuy(),
                        purchaseEntity.getQuantityOfBuy()},count);
                count++;
            }
            table.setPageLength(table.size());
            table.setHeight("20em");
            addComponent(table);
        }
    }

    private HashSet<PurchaseEntity> getSetOfPurchase(){
        return purchaseDAO.getAllObjects();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
