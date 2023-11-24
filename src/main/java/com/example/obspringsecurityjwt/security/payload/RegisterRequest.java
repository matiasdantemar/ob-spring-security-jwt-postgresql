package com.example.obspringsecurityjwt.security.payload;

// sirve para que el authcontroller utilice el metodo register, aqui se da de alta un usuario

//funcionan como el DTO, devuelven informacion o la reciben
public class RegisterRequest {

    private String username;
    // Se puede verificar la contraseña, comprobacion, agregar un paso que es verificar un email, envia un correo con una URL con una clave de validacion
    // cuando el usuario le da a aceptar, se llama a la apiREST pasandole una clave, si no la activa se borra a los dias, se utiliza una plantilla html con lifetime
    // enviar un correo con un servidor SMTP (Simple Mail Transfer Protocol),
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}