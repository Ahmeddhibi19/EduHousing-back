package com.PFA2.EduHousing.services.UserService;

import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.model.User;
import com.PFA2.EduHousing.repository.jpa.UserRepository;
import com.PFA2.EduHousing.repository.mongo.MonGoUserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final MonGoUserRepository monGoUserRepository;
    @Autowired
    UserServiceImpl(UserRepository userRepository,MonGoUserRepository monGoUserRepository){
        this.userRepository=userRepository;
        this.monGoUserRepository=monGoUserRepository;
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

    @Override
    @Transactional
    public String deleteUserById(Integer id,String mongoId) {
        if(id==null || mongoId==null){
            log.error("user id is null !!");
            return "id is null";
        }
        userRepository.deleteUserById(id);
        monGoUserRepository.deleteById(mongoId);
        return "user deleted successfully";
    }
}
