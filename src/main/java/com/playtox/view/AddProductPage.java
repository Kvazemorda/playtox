package com.playtox.view;

import com.playtox.dao.ProductDAO;
import com.playtox.entity.ProductEntity;
import com.playtox.entity.UserEntity;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.math.BigDecimal;

public class AddProductPage extends VerticalLayout implements View {
    TextField tfName, tfDescription, tfCost, tfQuantity;
    Button buttonAdd;

    ProductDAO productDAO;
    UserEntity userEntity;

    public AddProductPage(ProductDAO productDAO, UserEntity userEntity) {
        this.productDAO = productDAO;
        this.userEntity = userEntity;
        createForm();
    }

    private void createForm(){
        setSpacing(true);
        setMargin(true);
        tfName = new TextField("Name product");
        tfDescription = new TextField("Description");
        tfCost = new TextField("Cost");
        tfQuantity = new TextField("Quantity");
        addComponents(tfName, tfDescription, tfCost, tfQuantity, getButtonAdd());
    }

    protected Button getButtonAdd() {
        buttonAdd = new Button("save product");
        buttonAdd.addClickListener(e -> {
            try{
                if(formsIsNotNull()){
                    ProductEntity productEntity = new ProductEntity(
                            tfName.getValue(),
                            tfDescription.getValue(),
                            new BigDecimal(Double.parseDouble(tfCost.getValue())),
                            Integer.parseInt(tfQuantity.getValue()));
                            saveProduct(productEntity);
                    Notification.show("Product was saved");
                    clearForm();
                }else{
                    Notification.show("All forms must be filled");
                }

            }catch (NumberFormatException exp){
                Notification.show("Cost and Quantity must be number");
            }
        });
        return buttonAdd;
    }

    protected void saveProduct(ProductEntity productEntity){
        productDAO.create(productEntity);
    }

    private void clearForm(){
        tfName.clear();
        tfCost.clear();
        tfDescription.clear();
        tfQuantity.clear();
    }

    protected boolean formsIsNotNull(){
        if(tfName.getValue() != null
                && tfDescription != null
                && tfCost.getValue() != null
                && tfQuantity.getValue() != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
