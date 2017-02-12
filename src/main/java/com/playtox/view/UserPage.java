package com.playtox.view;

import com.playtox.dao.ProductDAO;
import com.playtox.dao.PurchaseDAO;
import com.playtox.entity.ProductEntity;
import com.playtox.entity.UserEntity;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Theme("valo")
@Service
@SpringUI
public class UserPage extends VerticalLayout implements View {
    UserEntity userEntity;
    ProductDAO productDAO;
    PurchaseDAO purchaseDAO;
    Navigator navigator;

    public UserPage(UserEntity userEntity, ProductDAO productDAO, PurchaseDAO purchaseDAO, Navigator navigator) {
        this.productDAO = productDAO;
        this.purchaseDAO = purchaseDAO;
        this.userEntity = userEntity;
        this.navigator = navigator;
        createForm();
    }



    public void createForm(){
        this.removeAllComponents();
        setHeightUndefined();
        HashSet<ProductEntity> productEntities = getSetOfProducts();
        if(productEntities != null){
            getSetOfProducts().forEach(this::addProductsOnPage);
        }
    }

    private void addProductsOnPage(ProductEntity productEntity){
        addComponents(new ProductForm(productEntity, purchaseDAO, userEntity, productDAO));
    }

    private HashSet<ProductEntity> getSetOfProducts(){
        return productDAO.getAllObjects();
    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
