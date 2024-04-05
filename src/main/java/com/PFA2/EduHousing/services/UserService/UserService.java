package com.PFA2.EduHousing.services.UserService;

import com.PFA2.EduHousing.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    public Integer getUserId(UserDetails userDetails);
    public User findUserByEmail(String email);

}
