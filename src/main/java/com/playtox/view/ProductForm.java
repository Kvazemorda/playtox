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
    private Label labelTitle, labelDescription, labelCost, labelTitleQuantity,  labelQuanity;
    private TextField textFieldQuantityForBuy;
    private Button buttonBuy, buttonEdit, buttonDelete;
    private VerticalLayout verticalForTitleDesc, verticalForCostQuan;
    private ProductEntity productEntity;
    private PurchaseDAO purchaseDAO;
    private UserEntity userEntity;
    private ProductDAO productDAO;


    public ProductForm(ProductEntity productEntity, PurchaseDAO purchaseDAO,
                       UserEntity userEntity, ProductDAO productDAO) {
        this.productDAO = productDAO;
        this.productEntity = productEntity;
        this.purchaseDAO = purchaseDAO;
        this.userEntity = userEntity;
        createForm();
    }
    public void createForm(){
        setSpacing(true);
        setMargin(true);
        labelTitle = new Label(productEntity.getName());
        labelDescription = new Label(productEntity.getDescription());
        labelDescription.setWidth("40em");
        verticalForTitleDesc = new VerticalLayout();

        labelCost = new Label(productEntity.getCost().toString() + " rub");
        labelTitleQuantity = new Label("quantity:");
        labelQuanity = new Label(String.valueOf(productEntity.getQuantity()));
        textFieldQuantityForBuy = new TextField();
        textFieldQuantityForBuy.setValue("1");
        textFieldQuantityForBuy.setWidth("5em");
        verticalForCostQuan = new VerticalLayout();
        fillLayouts();
        if(userEntity.isAdmin()){
            verticalForTitleDesc.addComponents(getButtonEdit(), getButtonDelete());
        }
        addComponents(verticalForTitleDesc, verticalForCostQuan);
    }

    public void fillLayouts(){
        verticalForTitleDesc.addComponents(labelTitle, labelDescription);
        verticalForCostQuan.addComponents(labelCost,
                new HorizontalLayout(labelTitleQuantity, labelQuanity),
                textFieldQuantityForBuy,
                getButtonBuy());
    }

    private void clearLayouts(){
        verticalForTitleDesc.removeAllComponents();
        verticalForCostQuan.removeAllComponents();
    }

    private void fillEditsComponents(EditProductForm editProductForm){
        verticalForTitleDesc.addComponent(editProductForm);
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
            clearLayouts();
            EditProductForm editProductForm = new EditProductForm(productDAO, userEntity, productEntity, this);
            fillEditsComponents(editProductForm);
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
