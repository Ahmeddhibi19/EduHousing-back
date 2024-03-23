package com.PFA2.EduHousing.services.auth;

import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<com.PFA2.EduHousing.model.User> user=userRepository.findUserByEmail(email);
        if(user.isEmpty()){
            throw new EntityNotFoundException("user with this email :"+email+" not found",
                    ErrorCodes.USER_NOT_FOUND);
        }
        return new User(user.get().getEmail(),user.get().getPassword(), Collections.emptyList());
    }
}
