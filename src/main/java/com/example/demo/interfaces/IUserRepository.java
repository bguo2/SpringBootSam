package com.example.demo.interfaces;
import com.example.demo.models.User;
import org.springframework.stereotype.Component;

import java.util.List;

public interface IUserRepository {
    List<User> getUsers();
}
