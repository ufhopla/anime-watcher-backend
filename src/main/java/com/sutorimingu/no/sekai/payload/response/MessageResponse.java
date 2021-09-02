package com.sutorimingu.no.sekai.payload.response;

/**
 * @author ufhopla
 * on 06/08/2021.
 */
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
