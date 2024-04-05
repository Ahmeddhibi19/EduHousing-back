package com.PFA2.EduHousing.services.mongoUserService;

import com.PFA2.EduHousing.model.User;
import com.PFA2.EduHousing.model.chat.MongoUser;

import java.util.List;

public interface MongoUserService {
    public void disconnect(User user);
    public List<MongoUser> findConnectedUsers();
}
