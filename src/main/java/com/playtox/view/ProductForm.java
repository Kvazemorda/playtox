package com.playtox.view;

import com.playtox.dao.ProductDAO;
import com.playtox.dao.PurchaseDAO;
import com.playtox.entity.ProductEntity;
import com.playtox.entity.PurchaseEntity;
import com.playtox.entity.UserEntity;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.Date;

@Theme("valo")
public class ProductForm extends HorizontalLayout implements View {
    Label labelTitle, labelDescription, labelCost, labelTitleQuantity,  labelQuanity;
    TextField textFieldQuantityForBuy;
    Button buttonBuy, buttonEdit, buttonDelete;
    VerticalLayout verticalForTitleDesc, verticslForCostQuan;
    ProductEntity productEntity;
    PurchaseDAO purchaseDAO;
    UserEntity userEntity;
    ProductDAO productDAO;


    public ProductForm(ProductEntity productEntity, PurchaseDAO purchaseDAO,
                       UserEntity userEntity, ProductDAO productDAO) {
        this.productDAO = productDAO;
        this.productEntity = productEntity;
        this.purchaseDAO = purchaseDAO;
        this.userEntity = userEntity;
        createForm();
    }
    private void createForm(){
        setSpacing(true);
        setMargin(true);
        labelTitle = new Label(productEntity.getName());
        labelDescription = new Label(productEntity.getDescription());
        labelDescription.setWidth("40em");
        verticalForTitleDesc = new VerticalLayout(labelTitle, labelDescription);

        labelCost = new Label(productEntity.getCost().toString() + " rub");
        labelTitleQuantity = new Label("quantity:");
        labelQuanity = new Label(String.valueOf(productEntity.getQuantity()));
        textFieldQuantityForBuy = new TextField();
        textFieldQuantityForBuy.setValue("1");
        textFieldQuantityForBuy.setWidth("5em");
        verticslForCostQuan = new VerticalLayout(labelCost,
                 new HorizontalLayout(labelTitleQuantity, labelQuanity), textFieldQuantityForBuy, getButtonBuy());
        if(userEntity.isAdmin()){
            verticalForTitleDesc.addComponents(getButtonEdit(), getButtonDelete());
        }
        addComponents(verticalForTitleDesc, verticslForCostQuan);
    }


    private Button getButtonBuy(){
        buttonBuy = new Button("Buy");
        buttonBuy.addClickListener(e ->{
            try{
                int cost = Integer.parseInt(textFieldQuantityForBuy.getValue());
                if(productEntity.getQuantity() > 0 && productEntity.getQuantity() >= cost){
                    PurchaseEntity purchaseEntity =
                            new PurchaseEntity(userEntity, productEntity, new Date(), productEntity.getCost(), cost);
                    purchaseDAO.create(purchaseEntity);
                    labelQuanity.setValue(String.valueOf(productEntity.getQuantity()));
                }else{
                    Notification.show("You want better than we have");
                }
            }catch (NumberFormatException exp){
                Notification.show("In field of quantity is not number");
            }
        });


        return buttonBuy;
    }

    public Button getButtonEdit() {
        buttonEdit = new Button("Edit");
        buttonEdit.addClickListener(e ->{
            Window window = new Window("Edit product");
            EditProductForm editProductForm = new EditProductForm(productDAO, userEntity);
            window.setContent(editProductForm);
            window.center();
            

        });
        return buttonEdit;
    }

    public Button getButtonDelete() {
        buttonDelete = new Button("Delete");
        buttonDelete.addClickListener(e -> productDAO.delete(productEntity));

        return buttonDelete;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
