package com.example.obspringsecurityjwt.security.payload;

// sirve para que el authcontroller utilice el metodo login, aqui se comprueba si esta eautenticado

//funcionan como el DTO, devuelven informacion o la reciben
public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest() {
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