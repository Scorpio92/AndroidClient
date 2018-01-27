package ru.scorpio92.mpgp.data.entity.message.auth;

import java.security.PrivateKey;

import ru.scorpio92.mpgp.data.entity.message.base.EncryptableMessage;
import ru.scorpio92.mpgp.data.entity.message.base.IEncryptableMessage;
import ru.scorpio92.mpgp.util.JsonWorker;

/**
 * Created by scorpio92 on 1/16/18.
 */

public class AuthorizeMessage extends EncryptableMessage implements IEncryptableMessage<AuthorizePayload> {

    private String IV;

    public AuthorizeMessage(String serverPublicKeyId, String serverPublicKey, String clientPublicKey, AuthorizePayload payload) throws Exception {
        super(MessageType.AUTHORIZE, serverPublicKeyId, serverPublicKey, clientPublicKey, payload);
    }

    public String getIV() {
        return IV;
    }

    @Override
    public AuthorizePayload getPayload(PrivateKey privateKey) throws Exception {
        return JsonWorker.getDeserializeJson(getDecryptedPayloadString(privateKey), AuthorizePayload.class);
    }
}
