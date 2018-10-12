package com.example.demo.controller;

import com.example.demo.dtos.EpRequestDto;
import com.example.demo.dtos.EpResponseDto;
import com.example.demo.managers.EpDaoService;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    @Qualifier("cspdbJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EpDaoService epDaoService;

    @GetMapping("getusers")
    public List<User> getUsers() {
        List<User> users = jdbcTemplate.query("SELECT u.DisplayName AS NAME, u.Email FROM useraccount u WHERE u.DefaultSupportAccountId=51228;", new BeanPropertyRowMapper(User.class));
        return users;
    }

    @PostMapping("epdevices")
    public EpResponseDto getEpDevices(@RequestBody EpRequestDto request) throws Exception {
        return epDaoService.getEpDevices(request);
    }
}
