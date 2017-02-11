package com.playtox.dao;

import com.playtox.entity.ProductEntity;
import com.playtox.entity.UserEntity;
import com.playtox.service.MySession;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashSet;

public class ProductDAO implements CRUD<ProductEntity> {
    private static final Logger log = Logger.getLogger(ProductEntity.class);

    @Override
    public void create(ProductEntity object) {
        Session session = new MySession().getSession();
        session.beginTransaction();
        session.saveOrUpdate(object);
        session.flush();
        session.getTransaction().commit();
        log.info("Добавлен новый товар, ID:" + object.getId() + " Name:" + object.getName());
    }

    @Override
    public HashSet<ProductEntity> getAllObjects() {
        String hql = "select product from ProductEntity product";
        Query query = new MySession().getSession().createQuery(hql);

        return new HashSet<>(query.list());
    }


    @Override
    public void update(ProductEntity object, UserEntity userEntity) {
        Session session = new MySession().getSession();
        session.beginTransaction();
        session.merge(object);
        session.flush();
        session.getTransaction().commit();
        log.info(userEntity.getLogin()
                + " Изменил товар, ID:" + object.getId()
                + " Name:" + object.getName());
    }

    @Override
    public void update(ProductEntity object) {
        Session session = new MySession().getSession();
        session.beginTransaction();
        session.merge(object);
        session.flush();
        session.getTransaction().commit();
        log.info("Товар куплен, ID:" + object.getId()
                + " Name:" + object.getName());

    }

    @Override
    public void delete(ProductEntity object) {
        Session session = new MySession().getSession();
        session.beginTransaction();
        session.delete(object);
        session.flush();
        session.getTransaction().commit();
    }

}
