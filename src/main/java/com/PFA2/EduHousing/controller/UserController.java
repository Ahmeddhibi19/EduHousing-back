package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.dto.Studentdto;
import com.PFA2.EduHousing.model.User;
import com.PFA2.EduHousing.model.chat.MongoUser;
import com.PFA2.EduHousing.services.UserService.UserService;
import com.PFA2.EduHousing.services.mongoUserService.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private MongoUserService mongoUserService;
    @Autowired
    public UserController(MongoUserService mongoUserService){
        this.mongoUserService=mongoUserService;
    }
    @MessageMapping("/user.disconnetUser")
    @SendTo("/user/topic")
    public User disconnect(@Payload User user){

        mongoUserService.disconnect(user);
        return user;
    }
    @GetMapping("/users")
    public ResponseEntity<List<MongoUser>> findConnectedUsers(){
        return ResponseEntity.ok(mongoUserService.findConnectedUsers());
    }
}
