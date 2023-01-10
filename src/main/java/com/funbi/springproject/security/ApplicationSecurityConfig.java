package com.funbi.springproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig  {

    private final PasswordEncoder passwordEncoder;
    private final String adminManagementBaseURl = "/management/api/**";
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }
    
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/login","/","index","/css/*","/js/*").permitAll()
        .requestMatchers("/api/v1/students/*").hasRole(ApplicationUserRole.STUDENT.name())

        .requestMatchers(HttpMethod.GET,adminManagementBaseURl).hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())
        .requestMatchers(HttpMethod.POST , adminManagementBaseURl).hasAnyAuthority(ApplicationUserPermission.STUDENT_WRITE.name(), ApplicationUserPermission.COURSE_WRITE.name())
        .requestMatchers(HttpMethod.DELETE , adminManagementBaseURl).hasAnyAuthority(ApplicationUserPermission.STUDENT_WRITE.name(), ApplicationUserPermission.COURSE_WRITE.name())
        .requestMatchers(HttpMethod.PUT , adminManagementBaseURl).hasAnyAuthority(ApplicationUserPermission.STUDENT_WRITE.name(), ApplicationUserPermission.COURSE_WRITE.name())

        .anyRequest()
        .authenticated()
        .and().
        httpBasic(); 
        return http.build();
        // http.authorizeHttpRequests(
        //     (auth)-> 
        //     auth.anyRequest()      
        //     .authenticated())
        //     .httpBasic()
        //     .and()
        //     .formLogin();
        // return http.build();


    }

 
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails funbiuser = User
        .builder()
        .username("funbi")
        .password(passwordEncoder.encode("password"))
        .roles(ApplicationUserRole.STUDENT.name())
        .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
        .build();
        UserDetails tundeUser = User
        .builder()
        .username("tunde")
        .password(passwordEncoder.encode("password1234"))
        .roles(ApplicationUserRole.ADMINTRAINEE.name())
        .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())

        .build();
        UserDetails toyinUser = User
        .builder()
        .username("toyin")
        .password(passwordEncoder.encode("password123"))
        .roles(ApplicationUserRole.ADMIN.name())
        .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())

        .build();


        return new InMemoryUserDetailsManager(funbiuser, toyinUser,tundeUser);


    }
}

