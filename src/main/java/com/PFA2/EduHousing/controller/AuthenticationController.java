package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.Utils.JwtUtils;
import com.PFA2.EduHousing.dto.auth.AuthenticationRequest;
import com.PFA2.EduHousing.dto.auth.AuthenticationResponse;
import com.PFA2.EduHousing.model.auth.ExtendedUser;
import com.PFA2.EduHousing.repository.UserRepository;
import com.PFA2.EduHousing.services.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;
import static com.PFA2.EduHousing.Utils.Constants.AUTHENTICATION_ENDPOINT;

@RestController
@RequestMapping(AUTHENTICATION_ENDPOINT)
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
        //Integer id= userService.getUserId(userDetails);
        final String jwt=jwtUtils.generateToken((ExtendedUser)userDetails);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .accessToken(jwt)
                .build()
        );
    }
}
