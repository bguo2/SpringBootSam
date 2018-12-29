package com.example.demo.managers;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.dtos.UserDto;
import com.example.demo.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserDaoService {

    private UserRepository userRepo;

    @Autowired
    public UserDaoService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public List<UserDto> getUsers() {
        List<User> users =  userRepo.getUsers();
        if(users == null)
            return null;
        List<UserDto> userDtos = new ArrayList<UserDto>();
        ModelMapper mapper = new ModelMapper();
        for (User user: users) {
            UserDto dto = mapper.map(user, UserDto.class);
            dto.setCreationDate(new Date());
            userDtos.add(dto);
        }

        return userDtos;
    }

    public UserDto getUser(String email) {
        User user = userRepo.getUser(email);
        if(user == null)
            return null;
        UserDto userDto = new ModelMapper().map(user, UserDto.class);
        return userDto;
    }

    public UserDto createUser(User user) {
        userRepo.createUser(user);
        UserDto userDto = new ModelMapper().map(user, UserDto.class);
        return userDto;
    }
}
