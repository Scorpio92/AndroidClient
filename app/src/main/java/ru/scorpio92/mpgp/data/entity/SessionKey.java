package ru.scorpio92.mpgp.data.entity;

/**
 * Created by scorpio92 on 1/22/18.
 */

public class SessionKey {

    private String sessionKeyId;
    private String sessionKey;
    private String IV;

    public SessionKey(String sessionKeyId, String sessionKey, String IV) {
        this.sessionKeyId = sessionKeyId;
        this.sessionKey = sessionKey;
        this.IV = IV;
    }

    public String getSessionKeyId() {
        return sessionKeyId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getIV() {
        return IV;
    }
}
