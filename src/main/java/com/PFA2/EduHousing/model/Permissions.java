package com.PFA2.EduHousing.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    HOMEOWNER_READ("homeowner:read"),
    HOMEOWNER_UPDATE("homeowner:update"),
    HOMEOWNER_CREATE("homeowner:create"),
    HOMEOWNER_DELETE("homeowner:delete"),
    HOMEOWNER_ACCEPT_REQUEST("homeowner:accept-request"),
    HOMEOWNER_VALIDATE_REQUEST("homeowner:validate-request"),
    HOMEOWNER_REJECT_REQUEST("homeowner:reject-request"),
    STUDENT_READ("student:read"),
    STUDENT_UPDATE("student:update"),
    STUDENT_CREATE("student:create"),
    STUDENT_DELETE("student:delete"),
    STUDENT_MAKE_REQUEST("student:make-request")
    ;
    @Getter
    private final String permission;
}
