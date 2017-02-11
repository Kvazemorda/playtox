package com.playtox.dao;

import com.playtox.entity.UserEntity;

import java.util.HashSet;

public interface CRUD<T> {

    public void create(T object);
    public HashSet<T> getAllObjects();
    public void update(T object, UserEntity userEntity);
    public void update(T object);
    public void delete(T object);

}
