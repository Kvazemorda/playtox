package com.playtox.dao;

import com.playtox.entity.ProductEntity;
import com.playtox.entity.PurchaseEntity;
import com.playtox.entity.UserEntity;
import com.playtox.service.MySession;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class PurchaseDAO implements CRUD<PurchaseEntity>{
    private static final Logger log = Logger.getLogger(PurchaseEntity.class);

    @Override
    public void create(PurchaseEntity object) {
        ProductDAO productDAO = new ProductDAO();
        ProductEntity productEntity = object.getProductEntity();
        productEntity.setQuantity(object.getProductEntity().getQuantity() - object.getQuantityOfBuy());
        productDAO.update(productEntity);

        Session session = new MySession().getSession();
        try{
            session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
            log.info("Добавлена покупка, ID:" + object.getId()
                    + " UserID: " + object.getUserEntity().getId()
                    + " UserName: " + object.getUserEntity().getLogin());
        }catch (Exception exp){
            log.error(exp);
        }finally {
            session.clear();
            session.close();
        }

    }

    @Override
    public HashSet<PurchaseEntity> getAllObjects() {
        try(Session session = new MySession().getSession()){
            String hql = "select purchase from PurchaseEntity purchase";
            Query query = session.createQuery(hql);
            return new HashSet<>(query.list());
        }catch (Exception ex){
            ex.printStackTrace();
            log.error(ex);
            return new HashSet<>();
        }
    }

    @Override
    public void update(PurchaseEntity object, UserEntity userEntity) {

    }

    @Override
    public void update(PurchaseEntity object) {

    }

    @Override
    public void delete(PurchaseEntity object) {

    }
}
