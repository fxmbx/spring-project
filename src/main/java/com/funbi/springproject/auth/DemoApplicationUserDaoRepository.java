package com.funbi.springproject.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.funbi.springproject.security.ApplicationUserRole;
import com.google.common.collect.Lists;

@Repository("fake")
public class DemoApplicationUserDaoRepository implements ApplicationUserDAO{
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DemoApplicationUserDaoRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String usernname) {
        return getApplcationUsers().stream().filter(x->x.getUsername().equals(usernname)).findFirst();
    }
    

    private List<ApplicationUser> getApplcationUsers(){
        return Lists.newArrayList(
            new ApplicationUser(passwordEncoder.encode("password"), "funbi", ApplicationUserRole.STUDENT.getGrantedAuthorities(), true, true, true, true),
            new ApplicationUser(passwordEncoder.encode("password123"), "toyin", ApplicationUserRole.ADMIN.getGrantedAuthorities(), true, true, true, true),
            new ApplicationUser(passwordEncoder.encode("password1234"), "tunde", ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(), true, true, true, true)
        );
    }
}
