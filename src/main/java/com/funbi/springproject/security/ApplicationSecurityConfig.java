package com.funbi.springproject.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.funbi.springproject.auth.ApplicationUserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig  {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;


    private final String adminManagementBaseURl = "/management/api/**";
    private final String defualtSuccessURL = "/courses";
    private final String loginURL = "/login";
    private final String logoutURL = "/logout";
    
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, 
    ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        // .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        // .and()
        .csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/","index","/css/*","/js/*").permitAll()
        // .requestMatchers("/api/v1/students/*").hasRole(ApplicationUserRole.STUDENT.name())
        // .requestMatchers(HttpMethod.GET,adminManagementBaseURl).hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())
        // .requestMatchers(HttpMethod.DELETE , adminManagementBaseURl).hasAnyAuthority(ApplicationUserPermission.STUDENT_WRITE.name(), ApplicationUserPermission.COURSE_WRITE.name())
        // .requestMatchers(HttpMethod.POST , adminManagementBaseURl).hasAnyAuthority(ApplicationUserPermission.STUDENT_WRITE.name(), ApplicationUserPermission.COURSE_WRITE.name())
        // .requestMatchers(HttpMethod.PUT , adminManagementBaseURl).hasAnyAuthority(ApplicationUserPermission.STUDENT_WRITE.name(), ApplicationUserPermission.COURSE_WRITE.name())

        .anyRequest()
            .authenticated()
        .and()
            .authenticationProvider(daoAuthenticationProvider());
            
        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);

        return provider;
    }
}

