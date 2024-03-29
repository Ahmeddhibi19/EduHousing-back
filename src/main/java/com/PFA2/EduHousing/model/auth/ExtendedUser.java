package com.PFA2.EduHousing.model.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ExtendedUser extends User {
    @Getter
    @Setter
    private Integer id;

    public ExtendedUser(String username, String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    /*public void setIsEnabled(ExtendedUser extendedUser){
        ;
    }
*/
   /* public ExtendedUser(String username, String password, Integer id,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }*/
}
