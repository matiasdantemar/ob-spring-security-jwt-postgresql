package com.example.obspringsecurityjwt.security.payload;

// para enviar un String un mensage de texto,
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}