package com.playtox.dao;

import com.playtox.entity.UserEntity;
import com.playtox.exeption.UserIsExist;
import com.playtox.service.MySession;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashSet;


public class UserDAO {

    private static final Logger log = Logger.getLogger(UserDAO.class);

    public void saveNewClient(UserEntity userEntity){
        if(!userIsExist(userEntity.getLogin())){
            userIsExist();
        }else {
            Session session = new MySession().getSession();
            session.beginTransaction();
            session.saveOrUpdate(userEntity);
            session.flush();
            session.getTransaction().commit();
            log.info("Добавлен новый пользователь " + userEntity.getLogin());
        }
    }

    public boolean authorizationUser(String login, String password){
        String passwordEncrypt = encryption(password);

        String hql = "select user from UserEntity user " +
                "where user.login = :login " +
                "and user.password = :password";
        Query query = new MySession().getSession().createQuery(hql);
        query.setParameter("password", passwordEncrypt);
        query.setParameter("login", login);
        if(query.list().size() > 0){
            return true;
        }else {
            return false;
        }
    }

    public HashSet<UserEntity> getAllObjects() {
        String hql = "select user from UserEntity user";
        Query query = new MySession().getSession().createQuery(hql);

        return new HashSet<>(query.list());
    }


    private String encryption(String password){
        //add md5 encryption;
        return password;
    }

    public boolean userIsExist(String login){
        String hql = "select user from UserEntity user " +
                "where user.login = :login" ;
        Query query = new MySession().getSession().createQuery(hql);
        query.setParameter("login", login);
        if(query.list().size() > 0){
            return false;
        }else{
            return true;
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
