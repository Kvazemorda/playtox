package com.playtox.dao;

import com.playtox.entity.UserEntity;
import com.playtox.exeption.UserIsExist;
import com.playtox.service.MySession;
import com.vaadin.ui.Notification;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserDAO {

    private static final Logger log = Logger.getLogger(UserDAO.class);

    public void saveNewClient(UserEntity userEntity){
        try (Session session = new MySession().getSession()) {
                session.beginTransaction();
                session.saveOrUpdate(userEntity);
                session.flush();
                session.getTransaction().commit();
                log.info("Добавлен новый пользователь " + userEntity.getLogin());
                Notification.show("User was saved");
            }catch (Exception exp){
                exp.printStackTrace();
                log.error(exp);
        }
    }

    public UserEntity authorizationUser(String login, String password){
        try{
        Session session = new MySession().getSession();
            String passwordEncrypt = encryption(password);

            String hql = "select user from UserEntity user " +
                    "where user.login = :login " +
                    "and user.password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("password", passwordEncrypt);
            query.setParameter("login", login);
            if(query.list().size() > 0){
                return (UserEntity) query.list().get(0);
            }else {
                return null;
            }
        }catch (Exception exp){
            exp.printStackTrace();
            log.error(exp);
            return null;
        }
    }

    public HashSet<UserEntity> getAllObjects() {
        try(Session session = new MySession().getSession()){
            String hql = "select user from UserEntity user";
            Query query = session.createQuery(hql);
            return new HashSet<>(query.list());
        }catch (Exception exp){
            return new HashSet<>();
        }
    }


    private String encryption(String password){
        //add md5 encryption;
        return password;
    }

    public boolean userIsExist(String login){
        try(Session session = new MySession().getSession()) {
            String hql = "select user from UserEntity user " +
                    "where user.login = :login";
            Query query = session.createQuery(hql);
            query.setParameter("login", login);
            if (query.list().size() > 0) {
                System.out.println(false);
                return false;
            } else {
                System.out.println(true);
                return true;
            }
        }catch (Exception e){
            log.error(e);
            e.printStackTrace();
            return false;
        }
    }

    private void userIsExist(){
        try {
            throw  new UserIsExist();
        } catch (UserIsExist userIsExist) {
            System.out.println("Пользователь существует");
            userIsExist.printStackTrace();
        }
    }


}
