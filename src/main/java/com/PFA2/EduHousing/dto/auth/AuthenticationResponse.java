package com.PFA2.EduHousing.dto.auth;

import com.PFA2.EduHousing.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

   private String accessToken;
   private String refreshToken;
}
