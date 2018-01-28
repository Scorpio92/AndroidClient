package ru.scorpio92.mpgp.data.entity.message.auth;

import ru.scorpio92.mpgp.data.entity.message.base.EncryptablePayload;

/**
 * Created by scorpio92 on 1/17/18.
 */

public class RegisterPayload extends EncryptablePayload {

    private String Nickname;
    private String Email;

    private String AuthToken;

    public RegisterPayload(String nickname, String email) {
        this.Nickname = nickname;
        this.Email = email;
    }

    public String getAuthToken() {
        return AuthToken;
    }
}
