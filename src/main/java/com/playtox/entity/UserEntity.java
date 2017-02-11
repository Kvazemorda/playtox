package com.playtox.entity;

import javax.persistence.*;

@Entity
@Table
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
    @Column(unique = true) String login;
    @Column String password;
    @Column boolean admin;

    public UserEntity() {
    }

    public UserEntity(String login, String password, boolean admin) {
        this.login = login;
        this.password = password;
        this.admin = admin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;

        UserEntity that = (UserEntity) o;

        if (admin != that.admin) return false;
        if (id != that.id) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }
}
