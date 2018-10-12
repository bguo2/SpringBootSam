package com.example.demo.dtos;

import java.util.Date;

public class UserDto {
    private String name;
    private String email;
    private Date creationDate;

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

    public Date getCreationDate(){
        return creationDate;
    }

    public void setCreationDate(Date value) {
        creationDate = value;
    }
}
