package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.UserApi;
import com.PFA2.EduHousing.services.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersControllere implements UserApi {
    private final UserService userService;
    @Autowired
    public UsersControllere(UserService userService){
        this.userService=userService;
    }
    @Override
    public Integer totalUser() {
        return userService.getTotalUsers();
    }
}
