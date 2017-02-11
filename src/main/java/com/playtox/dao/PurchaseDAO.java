package com.playtox.dao;

import com.playtox.entity.ProductEntity;
import com.playtox.entity.PurchaseEntity;
import com.playtox.entity.UserEntity;
import com.playtox.service.MySession;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashSet;

public class PurchaseDAO implements CRUD<PurchaseEntity>{
    private static final Logger log = Logger.getLogger(PurchaseEntity.class);

    @Override
    public void create(PurchaseEntity object) {
        ProductDAO productDAO = new ProductDAO();
        ProductEntity productEntity = object.getProductEntity();
        productEntity.setQuantity(object.getProductEntity().getQuantity() - object.getQuantityOfBuy());
        productDAO.update(productEntity);

        Session session = new MySession().getSession();
        session.beginTransaction();
        session.saveOrUpdate(object);
        session.flush();
        session.getTransaction().commit();
        log.info("Добавлена покупка, ID:" + object.getId()
                + " UserID: " + object.getUserEntity().getId()
                + " UserName: " + object.getUserEntity().getLogin());
    }

    @Override
    public HashSet<PurchaseEntity> getAllObjects() {
        String hql = "select purchase from PurchaseEntity purchase";
        Query query = new MySession().getSession().createQuery(hql);

        return new HashSet<>(query.list());
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
