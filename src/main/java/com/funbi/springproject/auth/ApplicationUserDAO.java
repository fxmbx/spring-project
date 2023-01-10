package com.funbi.springproject.auth;

import java.util.Optional;

public interface ApplicationUserDAO {
   public Optional<ApplicationUser> selectApplicationUserByUsername(String usernname);
}
