package com.PFA2.EduHousing.services.auth;

import com.PFA2.EduHousing.model.auth.ExtendedUser;
import com.PFA2.EduHousing.services.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.PFA2.EduHousing.model.User user=userService.findUserByEmail(email);
        /*authorities.add(new SimpleGrantedAuthority (user.getRole().toString()));*/
        //List<SimpleGrantedAuthority> authorities = new ArrayList<>(user.getRole().getAuthorities());
        return new ExtendedUser(user.getEmail(),user.getPassword(),user.getAuthorities());
    }
}
