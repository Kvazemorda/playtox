package com.playtox.dao;

import com.playtox.entity.ProductEntity;
import com.playtox.entity.UserEntity;
import com.playtox.service.MySession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashSet;

@SpringComponent
@UIScope
public class ProductDAO implements CRUD {
    private static final Logger log = Logger.getLogger(ProductEntity.class);

    @Override
    public void create(Object object) {
        ProductEntity productEntity = (ProductEntity) object;
        Session session = new MySession().getSession();
        try{
            session.beginTransaction();
            session.saveOrUpdate(object);
            session.flush();
            session.getTransaction().commit();
            log.info("Добавлен новый товар, ID:" + productEntity.getId() + " Name:" + productEntity.getName());
        }catch (Exception e){
            log.error(e);
        }finally {
            session.clear();
            session.close();
        }

    }

    @Override
    public HashSet<ProductEntity> getAllObjects() {

        try(Session session = new MySession().getSession()){
            String hql = "select product from ProductEntity product";
            Query query = session.createQuery(hql);
            return new HashSet<>(query.list());

        }catch (Exception exp){
            log.error(exp);
            return new HashSet<>();
        }
    }


    @Override
    public void update(Object object, UserEntity userEntity) {
        ProductEntity productEntity = (ProductEntity) object;
        Session session = new MySession().getSession();
        try{
            session.beginTransaction();
            session.merge(object);
            session.flush();
            session.getTransaction().commit();
            log.info(userEntity.getLogin()
                    + " Изменил товар, ID:" + productEntity.getId()
                    + " Name:" + productEntity.getName());
        }catch (Exception e){
            log.error(e);
        }finally {
            session.clear();
            session.close();
        }

    }

    @Override
    public void update(Object object) {
        ProductEntity productEntity = (ProductEntity) object;
        Session session = new MySession().getSession();
        try{
            session.beginTransaction();
            session.merge(object);
            session.getTransaction().commit();
            log.info("Товар куплен, ID:" + productEntity.getId()
                    + " Name:" + productEntity.getName());
        }catch (Exception exp){
            log.error(exp);
        }finally {
            session.clear();
            session.close();
        }


    }

    @Override
    public void delete(Object object) {
        ProductEntity productEntity = (ProductEntity) object;
        Session session = new MySession().getSession();
        try{
            session.beginTransaction();
            session.delete(productEntity);
            session.getTransaction().commit();
            log.info("Товар удален, ID:" + productEntity.getId()
                    + " Name:" + productEntity.getName());
        }catch (Exception exp){
            log.error(exp);
        }finally {
            session.clear();
            session.close();
        }

    }

}
