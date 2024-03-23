package com.PFA2.EduHousing.dto.auth;

import com.PFA2.EduHousing.model.Roles;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

   private String accessToken;
}
