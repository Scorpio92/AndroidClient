package ru.scorpio92.mpgp.data.entity.message.auth;


import ru.scorpio92.mpgp.data.entity.message.base.EncryptablePayload;

/**
 * Created by scorpio92 on 1/16/18.
 */

public class AuthorizePayload extends EncryptablePayload {

    private String SessionKey;

    private String AuthToken;

    public AuthorizePayload(String authToken) {
        this.AuthToken = authToken;
    }

    public String getSessionKey() {
        return SessionKey;
    }
}
