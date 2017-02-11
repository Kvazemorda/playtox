package com.playtox.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class MySession {
    public Session getSession(){
        try{
            return HibernateSessionFactory.getSessionFactory().getCurrentSession();
        }catch (HibernateException e){
            return HibernateSessionFactory.getSessionFactory().openSession();
        }
    }
}
