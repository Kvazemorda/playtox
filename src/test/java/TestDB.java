import com.playtox.dao.ProductDAO;
import com.playtox.dao.PurchaseDAO;
import com.playtox.dao.UserDAO;
import com.playtox.entity.ProductEntity;
import com.playtox.entity.PurchaseEntity;
import com.playtox.entity.UserEntity;
import com.playtox.service.HibernateSessionFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;


public class TestDB {

    public static void main(String[] args) {

        UserEntity userEntity = new UserEntity("User1", "3215", false);
        UserEntity userEntity2 = new UserEntity("User2", "3215", true);
        UserEntity userEntity3 = new UserEntity("User3", "3215", false);
        UserEntity userEntity4 = new UserEntity("User4", "3216", false);
        UserEntity userEntity5 = new UserEntity("User5", "3215", false);

        UserDAO userDAO = new UserDAO();
        userDAO.saveNewClient(userEntity);
        userDAO.saveNewClient(userEntity2);
        userDAO.saveNewClient(userEntity3);
        userDAO.saveNewClient(userEntity4);
        userDAO.saveNewClient(userEntity5);

        ProductDAO productDAO = new ProductDAO();

        ArrayList<UserEntity> userEntities = new ArrayList<>(new UserDAO().getAllObjects());
        for(int i = 1; i < 21; i++){
            productDAO.create(new ProductEntity("productName" + i, "description " + i, new BigDecimal(i*100*Math.random()), i));
        }
        ArrayList<ProductEntity> productEntities = new ArrayList<>(new ProductDAO().getAllObjects());
        userExist(userEntities.get(2));
        listProduct();
        changeProduct(productEntities);
        changeProductOfUser(productEntities, userEntities);
        buyProduct(productEntities, userEntities);
        HibernateSessionFactory.shutdown();

    }

    public static void userExist(UserEntity userEntity) {
        UserDAO userDAO = new UserDAO();
        System.out.println("user must be false " + userDAO.authorizationUser("User1", "3216"));
        System.out.println("user must be true " + userDAO.authorizationUser("User1", "3215"));
        System.out.println("user must be true " + userDAO.authorizationUser("User5", "3215"));
    }
    public static void listProduct(){
        ProductDAO productDAO = new ProductDAO();
        if(productDAO.getAllObjects().size() == 20){
            System.out.println("get all products is " + true);
        }else {
            System.out.println("get all products is " + false);
        }

    }

    public static void changeProduct(ArrayList<ProductEntity> productEntities){
        ProductDAO productDAO = new ProductDAO();


        productEntities.get(2).setCost(new BigDecimal(321.00));
        String name = productEntities.get(2).getName();
        productDAO.update(productEntities.get(2));
        for (ProductEntity productEntity: new ProductDAO().getAllObjects()){

            if(productEntity.getName().equals("productName15")) {
                if (productEntity.getCost().doubleValue() == new BigDecimal(321.00).doubleValue()) {
                    System.out.println("change product is true");
                } else {
                    System.out.println("----->change product is false");
                }
            }
        }

    }

    public static void changeProductOfUser(ArrayList<ProductEntity> productEntities, ArrayList<UserEntity> userEntities){
        ProductDAO productDAO = new ProductDAO();

        productEntities.get(2).setCost(new BigDecimal(600));
        productEntities.get(2).setQuantity(60);
        productDAO.update(productEntities.get(2), userEntities.get(0));

        for (ProductEntity productEntity: new ProductDAO().getAllObjects()) {
            if (productEntity.getName().equals("productName15")) {
                if (productEntity.getCost().doubleValue() == new BigDecimal(600).doubleValue()) {
                    if (productEntity.getQuantity() == 60) {
                        System.out.println("change product by USER is true");
                    } else {
                        System.out.println("----->change product by USER is false");
                    }
                }

            }
        }
    }

    public static void buyProduct(ArrayList<ProductEntity> productEntities, ArrayList<UserEntity> userEntities){
        PurchaseEntity purchaseEntity = new PurchaseEntity(userEntities.get(3),
                productEntities.get(2),
                new Date(),
                productEntities.get(2).getCost(),
                5);
        PurchaseDAO purchaseDAO = new PurchaseDAO();
        purchaseDAO.create(purchaseEntity);
        new PurchaseDAO().getAllObjects().forEach(System.out::println);

    }
}
