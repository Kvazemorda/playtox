package com.playtox.view;

import com.playtox.dao.ProductDAO;
import com.playtox.entity.ProductEntity;
import com.playtox.entity.UserEntity;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

import java.math.BigDecimal;

public class EditProductForm extends AddProductPage {
    private ProductForm productForm;
    private UserEntity userEntity;
    private ProductEntity productEntity;

    public EditProductForm(ProductDAO productDAO, UserEntity userEntity, ProductEntity productEntity, ProductForm productForm) {
        super(productDAO, userEntity);
        this.productEntity = productEntity;
        this.productForm = productForm;
        fillForm(productEntity);
    }
    private void fillForm(ProductEntity productEntity){
        tfName.setValue(productEntity.getName());
        tfDescription.setValue(productEntity.getDescription());
        tfCost.setValue(String.valueOf(productEntity.getCost()));
        tfQuantity.setValue(String.valueOf(productEntity.getQuantity()));

    }

    @Override
    protected Button getButtonAdd() {
        buttonAdd = new Button("save product");
        buttonAdd.addClickListener(e -> {
            try{
                if(formsIsNotNull()){
                    productEntity.setName(tfName.getValue());
                    productEntity.setDescription(tfDescription.getValue());
                    productEntity.setCost(new BigDecimal(Double.parseDouble(tfCost.getValue())));
                    productEntity.setQuantity(Integer.parseInt(tfQuantity.getValue()));
                    saveProduct(productEntity);
                    Notification.show("Product was saved");
                }else{
                    Notification.show("All forms must be filled");
                }

            }catch (NumberFormatException exp){
                Notification.show("Cost and Quantity must be number");
            }
        });
        return buttonAdd;
    }

    @Override
    protected void saveProduct(ProductEntity productEntity) {
        productForm.removeAllComponents();
        productDAO.update(productEntity, this.userEntity);
        productForm.createForm();
    }
}
