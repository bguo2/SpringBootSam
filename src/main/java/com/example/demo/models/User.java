package com.example.demo.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class User {
    @Size(min = 2)
    private String name;

    @Email
    private String email;

    public User() {

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        email = value;
    }
}
