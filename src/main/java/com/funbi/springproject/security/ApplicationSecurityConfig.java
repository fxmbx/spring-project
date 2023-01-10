package com.funbi.springproject.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig  {

    private final PasswordEncoder passwordEncoder;
    private final String adminManagementBaseURl = "/management/api/**";
    private final String defualtSuccessURL = "/courses";
    private final String loginURL = "/login";
    private final String logoutURL = "/logout";
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
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
        .and().
        formLogin()
            .loginPage(loginURL)
                .permitAll()
                .defaultSuccessUrl(defualtSuccessURL,true)
        .and()
            .rememberMe()
                .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                .key("somethingVerySecure")
        .and()
            .logout()
                .logoutUrl(logoutURL)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","remember-me")
                .logoutSuccessUrl(loginURL)
                ;
            
        return http.build();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails funbiuser = User
        .builder()
        .username("funbi")
        .password(passwordEncoder.encode("password"))
        // .roles(ApplicationUserRole.STUDENT.name())
        .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
        .build();
        UserDetails tundeUser = User
        .builder()
        .username("tunde")
        .password(passwordEncoder.encode("password1234"))
        // .roles(ApplicationUserRole.ADMINTRAINEE.name())
        .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
        .build();
        UserDetails toyinUser = User
        .builder()
        .username("toyin")
        .password(passwordEncoder.encode("password123"))
        // .roles(ApplicationUserRole.ADMIN.name())
        .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
        .build();


        return new InMemoryUserDetailsManager(funbiuser, toyinUser,tundeUser);


    }
}

