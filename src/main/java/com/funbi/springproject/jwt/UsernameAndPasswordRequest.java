package com.funbi.springproject.jwt;

public class UsernameAndPasswordRequest {

    private String username;
    private String password;
    
    public UsernameAndPasswordRequest() {
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}
