package ru.scorpio92.mpgp.data.entity.message.auth;

import ru.scorpio92.mpgp.data.entity.message.base.EncryptablePayload;

/**
 * Created by scorpio92 on 1/17/18.
 */

public class RegisterPayload extends EncryptablePayload {

    private String nickname;
    private String email;

    private String authToken;

    public RegisterPayload(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public String getAuthToken() {
        return authToken;
    }
}
