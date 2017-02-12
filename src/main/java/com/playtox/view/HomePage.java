package com.playtox.view;

import com.playtox.dao.ProductDAO;
import com.playtox.dao.PurchaseDAO;
import com.playtox.dao.UserDAO;
import com.playtox.entity.UserEntity;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@SpringUI(path = "")
public class HomePage extends UI{
    VerticalLayout layout;
    HorizontalLayout horizontalTop;
    Button buttonProduct, buttonSales, buttonAddProduct;
    LoginPage loginPage;
    Navigator navigator;
    UserPage userPage;
    SalesPage salesPage;
    public static final String LOGIN = "login";
    public static final String SALES = "sales";
    public static final String ADD_PRODUCT = "add";


    @Autowired
    UserDAO userDAO;
    @Autowired
    ProductDAO productDAO;
    @Autowired
    PurchaseDAO purchaseDAO;

        @Override
        protected void init(VaadinRequest vaadinRequest) {
            CssLayout viewLayout = new CssLayout();
            horizontalTop = new HorizontalLayout();
            layout = new VerticalLayout(horizontalTop, viewLayout);
            loginPage = new LoginPage();
            navigator = new Navigator(this, viewLayout);
            navigator.addView("", loginPage);
            layout.setMargin(true);
            layout.setSpacing(true);
            layout.setComponentAlignment(horizontalTop, Alignment.TOP_CENTER);
            layout.setComponentAlignment(viewLayout, Alignment.MIDDLE_CENTER);
            setContent(layout);
            this.navigator.setErrorView(loginPage);
        }

    public class LoginPage extends VerticalLayout implements View {
        private Label labelLogin, labelPassword;
        private TextField textLogin;
        private PasswordField passwordField;
        private Button buttonLogin, buttonRegistration;
        private HorizontalLayout horizontalForButton;

        public LoginPage(){
            setSpacing(true);
            setMargin(true);
            labelLogin = new Label("Login");
            textLogin = new TextField();
            textLogin.setValue("User2");
            labelPassword = new Label("Password");
            passwordField = new PasswordField();
            passwordField.setValue("3215");
            horizontalForButton = new HorizontalLayout(getButtonLogin(), getButtonRegistration());
            addComponents(labelLogin, textLogin, labelPassword, passwordField, horizontalForButton);
        }

        public Button getButtonLogin() {
            buttonLogin = new Button("LogIN");
            buttonLogin.addClickListener(e -> {
                if(textLogin.getValue() != null && passwordField.getValue() != null) {
                    UserEntity userEntity = userDAO.authorizationUser(textLogin.getValue(), passwordField.getValue());
                    if (userEntity != null) {
                        userPage = new UserPage(userEntity, productDAO, purchaseDAO, navigator);
                        if (userEntity.isAdmin()) {
                            addComponentInHorizontal(userEntity);
                            SalesPage salesPage = new SalesPage(userEntity, purchaseDAO);
                            AddProductPage addProductPage = new AddProductPage(productDAO, userEntity);
                            navigator.addView(SALES, salesPage);
                            navigator.addView(ADD_PRODUCT, addProductPage);
                        }
                        try {
                            navigator.addView(LOGIN, userPage);
                            navigator.navigateTo(LOGIN);
                        } catch (IllegalArgumentException exp) {
                            exp.printStackTrace();
                            navigator.navigateTo("");
                        }
                    } else {
                        Notification.show("USER NOT EXIST");
                    }
                }else {
                    Notification.show("Fields is empty");
                }
            });

            return buttonLogin;
        }


        public Button getButtonRegistration() {
            buttonRegistration = new Button("SignUP");
            buttonRegistration.addClickListener(e ->{
                if(textLogin.getValue() != null && passwordField.getValue() != null){
                    if(!userDAO.userIsExist(textLogin.getValue())){
                        userDAO.saveNewClient(new UserEntity(textLogin.getValue(), passwordField.getValue()));
                        navigator.addView(LOGIN, userPage);
                        Notification.show("User was saved");
                        navigator.navigateTo(LOGIN);
                    }
                }
            });
            return buttonRegistration;
        }

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        }
    }

    public Button getButtonAddProduct(){
        buttonAddProduct = new Button("add product");
        buttonAddProduct.addClickListener(e -> navigator.navigateTo(ADD_PRODUCT));
        return buttonAddProduct;
    }

    protected void addComponentInHorizontal(UserEntity userEntity){
        if(userIsAdmin(userEntity)){
            horizontalTop.setMargin(true);
            horizontalTop.addComponents(getButtonProduct(), getButtonSales(), getButtonAddProduct());
           // layout.addComponent(horizontalTop);
        }
    }

    public Button getButtonProduct() {
        buttonProduct = new Button("Products");
        buttonProduct.addClickListener(e ->{
            userPage.createForm();
            navigator.navigateTo(HomePage.LOGIN);
        });
        return buttonProduct;
    }

    public Button getButtonSales() {
        buttonSales = new Button("Sales");
        buttonSales.addClickListener(e -> navigator.navigateTo(HomePage.SALES));
        return buttonSales;
    }

    private boolean userIsAdmin(UserEntity userEntity){
        return userEntity.isAdmin();
    }
}
