package ru.scorpio92.mpgp.data.entity.message.auth;

import ru.scorpio92.mpgp.data.entity.message.base.Payload;

/**
 * Created by scorpio92 on 1/17/18.
 */

public class GetServerPublicKeyPayload extends Payload {

    private String ServerPublicKey;

    public String getServerPublicKey() {
        return ServerPublicKey;
    }
}
