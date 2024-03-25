package com.PFA2.EduHousing.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Roles {
    ADMIN(
            Set.of(
                    Permissions.ADMIN_CREATE,
                    Permissions.ADMIN_READ,
                    Permissions.ADMIN_UPDATE,
                    Permissions.ADMIN_DELETE

            )
    ),
    HOMEOWNER(
            Set.of(
                    Permissions.HOMEOWNER_DELETE,
                    Permissions.HOMEOWNER_READ,
                    Permissions.HOMEOWNER_CREATE,
                    Permissions.HOMEOWNER_UPDATE
            )
    ),
    STUDENT(
            Set.of(
                    Permissions.STUDENT_CREATE,
                    Permissions.STUDENT_UPDATE,
                    Permissions.STUDENT_READ,
                    Permissions.STUDENT_DELETE
            )
    )
    ;
    @Getter
    private final Set<Permissions> permissions;

   /* Roles(Set<Permissions> adminCreate) {

    }*/

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
