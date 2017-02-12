package com.playtox.dao;

import com.playtox.entity.ProductEntity;
import com.playtox.entity.UserEntity;
import com.playtox.service.MySession;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ProductDAO implements CRUD {
    @Autowired
    private static final Logger log = Logger.getLogger(ProductDAO.class);

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
            e.printStackTrace();
            log.error(e);
        }finally {
            session.clear();
            session.close();
        }

    }

    @Override
    public HashSet<ProductEntity> getAllObjects() {

        try(Session session = new MySession().getSession()){
            String hql = "select product from ProductEntity product " +
                    "where product.isDelete = false";
            Query query = session.createQuery(hql);
            return new HashSet<>(query.list());

        }catch (Exception exp){
            exp.printStackTrace();
            log.error(exp);
            return new HashSet<>();
        }
    }


    @Override
    public void update(Object object, UserEntity userEntity) {
        ProductEntity productEntity = (ProductEntity) object;
        try (Session session = new MySession().getSession()) {
            session.beginTransaction();
            session.update(object);
            session.flush();
            session.getTransaction().commit();
           log.info(userEntity.getLogin()
                    + " Изменил товар, ID:" + productEntity.getId()
                    + " Name:" + productEntity.getName());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
    }

    @Override
    public void update(Object object) {
        ProductEntity productEntity = (ProductEntity) object;
        Session session = new MySession().getSession();
        try{
            session.beginTransaction();
            session.persist(object);
            session.getTransaction().commit();
            log.info("Товар куплен, ID:" + productEntity.getId()
                    + " Name:" + productEntity.getName());
        }catch (Exception exp){
            exp.printStackTrace();
            log.error(exp);
        }finally {
            session.clear();
            session.close();
        }
    }

    @Override
    public void delete(Object object) {
        ProductEntity productEntity = (ProductEntity) object;
        productEntity.setDelete(true);
        Session session = new MySession().getSession();
        try{
            session.beginTransaction();
            session.merge(productEntity);
            session.getTransaction().commit();
            log.info("Товар удален, ID:" + productEntity.getId()
                    + " Name:" + productEntity.getName());
        }catch (Exception exp){
            exp.printStackTrace();
            log.error(exp);
        }finally {
            session.clear();
            session.close();
        }

    }

}
