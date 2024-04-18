package com.PFA2.EduHousing.services.UserService;

import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.model.User;
import com.PFA2.EduHousing.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Autowired
    UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public Integer getUserId(UserDetails userDetails) {
        User user=userRepository.findUserByEmailIgnoreCase(userDetails.getUsername()).orElseThrow(
                ()->new EntityNotFoundException(
                        "no user with the provided email",
                        ErrorCodes.USER_NOT_FOUND
                )
        );
        return user.getId();
    }

    @Override
    public User findUserByEmail(String email) {
        User user=userRepository.findUserByEmailIgnoreCase(email).orElseThrow(
                ()->new EntityNotFoundException(
                        "no user with the provided email",
                        ErrorCodes.USER_NOT_FOUND
                )
        );
        return user;
    }

    @Override
    public Integer getTotalUsers() {
        return userRepository.findAll().size();
    }
}
