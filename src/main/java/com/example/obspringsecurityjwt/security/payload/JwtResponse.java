package com.example.obspringsecurityjwt.security.payload;

// si el login es correcto vamos a devolver el token un objeto de una clase, se traduce en JSON, la clave token y el valor
// son las respuestas y clases que son para logearse, registrarse, recibir una respuesta

//funcionan como el DTO, devuelven informacion o la reciben, retorna un token

public class JwtResponse {

    private String token;

    public JwtResponse() {
    }
    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}