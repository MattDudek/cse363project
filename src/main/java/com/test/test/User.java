package com.test.test;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    public User(ResultSet resultSet) throws SQLException {
        name = resultSet.getString("name");
        email = resultSet.getString("email");
        id = resultSet.getInt("id");

    }

    public User() {
        id = 0;
        name = null;
        email = null;

    }
}
