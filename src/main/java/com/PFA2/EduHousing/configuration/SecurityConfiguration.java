package com.PFA2.EduHousing.configuration;


import com.PFA2.EduHousing.services.auth.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
/*import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;*/
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;
import static com.PFA2.EduHousing.Utils.Constants.AUTHENTICATION_ENDPOINT;
import static com.PFA2.EduHousing.model.Permissions.*;
import static com.PFA2.EduHousing.model.Roles.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {


    private static final String[] WHITE_LIST_URLS = {
            AUTHENTICATION_ENDPOINT+"/authenticate",
            AUTHENTICATION_ENDPOINT+"/refresh_token",
            "/api"+APP_ROOT+"/confirm-account/admin",
            "/api"+APP_ROOT+"/confirm-account/homeowner",
            "/api"+APP_ROOT+"/confirm-account/student",
            "/api"+APP_ROOT+"/admin/create/**",
            "/api"+APP_ROOT+"/homeowner/create/**",
            "/api"+APP_ROOT+"/student/create/**",
            "/api"+APP_ROOT+"/college/findAll",
            "/api"+APP_ROOT+"/admin/total_number_user",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"};

    private final ApplicationUserDetailsService applicationUserDetailsService;

    private final ApplicationRequestFilter applicationRequestFilter;

   // private final DaoAuthenticationProvider daoAuthenticationProvider;
    /*private AuthenticationProvider authenticationProvider;*/
    @Autowired
    public SecurityConfiguration(ApplicationRequestFilter jwtAuthenticationFilter,
                          ApplicationUserDetailsService applicationUserDetailsService
                         // DaoAuthenticationProvider daoAuthenticationProvider
    ) {

        this.applicationRequestFilter = jwtAuthenticationFilter;
        this.applicationUserDetailsService = applicationUserDetailsService;
        //this.daoAuthenticationProvider = daoAuthenticationProvider;
    }
   /* @Bean
    public AuthenticationProvider authenticationProvider() {

        daoAuthenticationProvider.setUserDetailsService(applicationUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }*/
   @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setUserDetailsService(applicationUserDetailsService);
       provider.setPasswordEncoder(passwordEncoder());
       return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //httpSecurity.headers().frameOptions().disable();

       // httpSecurity.cors().and().csrf().disable();
        //@formatter:off
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->
                            req.requestMatchers(
                                    WHITE_LIST_URLS
                        ).permitAll()
                                    .requestMatchers("/api"+APP_ROOT+"/admin/authorized/**").hasAnyRole(ADMIN.name())
                                    .requestMatchers("/api"+APP_ROOT+"/city/admin/**").hasAnyRole(ADMIN.name())
                                    .requestMatchers("/api"+APP_ROOT+"/apartment/homeowner/**").hasAnyRole(HOMEOWNER.name())
                                    .requestMatchers("/api"+APP_ROOT+"/applicationFeedback/user/**").hasAnyAuthority(
                                            HOMEOWNER_CREATE.name(),
                                            STUDENT_CREATE.name(),
                                            STUDENT_UPDATE.name(),
                                            HOMEOWNER_UPDATE.name(),
                                            STUDENT_DELETE.name(),
                                            HOMEOWNER_DELETE.name()
                                    )
                                    .requestMatchers("/api"+APP_ROOT+"/apartmentImages/homeowner/**").hasAnyRole(HOMEOWNER.name())
                                    .requestMatchers("/api"+APP_ROOT+"/college/admin/**").hasAnyRole(ADMIN.name())
                                    .requestMatchers("/api"+APP_ROOT+"/favourites/student/**").hasAnyRole(STUDENT.name())
                                    .requestMatchers("/api"+APP_ROOT+"/homeowner/admin/**").hasAnyRole(ADMIN.name())
                                    .requestMatchers("/api"+APP_ROOT+"/homeowner/homeowner/**").hasAnyRole(HOMEOWNER.name())
                                    .requestMatchers("/api"+APP_ROOT+"/profileImage/admin/**").hasAnyRole(ADMIN.name())
                                    .requestMatchers("/api"+APP_ROOT+"/rentalDetails/homeowner/**").hasAnyRole(HOMEOWNER.name())
                                    .requestMatchers("/api"+APP_ROOT+"/rentalDetails/homeowner_admin/**").hasAnyRole(HOMEOWNER.name(),ADMIN.name())
                                    .requestMatchers("/api"+APP_ROOT+"/rentalDetails/student/**").hasAnyAuthority(STUDENT_READ.name(),ADMIN_READ.name())
                                    .requestMatchers("/api"+APP_ROOT+"/rentalfeedback/student/**").hasAnyRole(STUDENT.name())
                                    .requestMatchers("/api"+APP_ROOT+"/rentalfeedback/admin/**").hasAnyRole(ADMIN.name())
                                    .requestMatchers("/api"+APP_ROOT+"/rentalfeedback/student_admin/**").hasAnyAuthority(STUDENT_READ.name(),ADMIN_READ.name())
                                    .requestMatchers("/api"+APP_ROOT+"/request/student/**").hasAnyRole(STUDENT.name())
                                    .requestMatchers("/api"+APP_ROOT+"request/admin_homeowner/**").hasAnyAuthority(ADMIN_READ.name(),HOMEOWNER_READ.name())
                                    .requestMatchers("/api"+APP_ROOT+"/request/admin_student/**").hasAnyAuthority(ADMIN_READ.name(),STUDENT_READ.name())
                                    .requestMatchers("/api"+APP_ROOT+"/request/homeowner/**").hasAnyRole(HOMEOWNER.name())
                                    .requestMatchers("/api"+APP_ROOT+"/student/student/**").hasAnyRole(STUDENT.name())
                                    .requestMatchers("/api"+APP_ROOT+"/student/admin/**").hasAnyRole(ADMIN.name())
                                    .requestMatchers("/api"+APP_ROOT+"/user/admin/delete_by_id/**").hasAnyRole(ADMIN.name())
                        .anyRequest().authenticated()
                )

                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.exceptionHandling()
                /*.authenticationEntryPoint(
                        (request, response, authException)
                                -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                authException.getLocalizedMessage()
                        )
                )*/
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore( applicationRequestFilter,  UsernamePasswordAuthenticationFilter.class);
        //@formatter:on
        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter(){
       final UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
       final CorsConfiguration corsConfiguration=new CorsConfiguration();
       corsConfiguration.setAllowCredentials(true);
       corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000",""));
       corsConfiguration.setAllowedHeaders(Arrays.asList("Origin","Content-type","Accept","Authorization"));
       corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
       source.registerCorsConfiguration("/**",corsConfiguration);
       return new CorsFilter(source);
    }



    /*@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        return new DaoAuthenticationProvider();
    }*/
     /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(applicationUserDetailsService).passwordEncoder(passwordEncoder());
    }*/
    //@Override
    // protected void configure(HttpSecurity http) throws Exception{
    //http.csrf().disable()
    //.authorizeRequests().requestMatchers("/**/authenticate",
    // "/**/admin/create",
    //  "/**/homeowner/create",
    // "/v2/api-docs",
    // "/swagger-resources",
    // "/swagger-resources/**",
    //"/configuration/ui",
    // "/configuration/security",
    // "/swagger-ui.html",
    // "/webjars/**",
    // "/v3/api-docs/**",
    //  "/swagger-ui/**").permitAll()
    // .anyRequest().authenticated()
    // .and()
    // .sessionManagement()
    // .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // http.addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class)
    // }
}
