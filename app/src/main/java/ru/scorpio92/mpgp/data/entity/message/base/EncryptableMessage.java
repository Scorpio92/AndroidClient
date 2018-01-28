package ru.scorpio92.mpgp.data.entity.message.base;

import java.security.PrivateKey;

import ru.scorpio92.mpgp.util.crypto.RSA;

/**
 * Created by scorpio92 on 1/20/18.
 */

public abstract class EncryptableMessage extends BaseMessage {

    private String ServerPublicKeyId;
    private String ClientPublicKey;

    public EncryptableMessage(MessageType type, String serverPublicKeyId, String serverPublicKey, String clientPublicKey, EncryptablePayload payload) throws Exception {
        super(type, payload.getEncryptedString(serverPublicKey));
        this.ServerPublicKeyId = serverPublicKeyId;
        this.ClientPublicKey = clientPublicKey;
    }

    protected String getDecryptedPayloadString(PrivateKey privateKey) throws Exception {
        return RSA.decryptFromBase64(privateKey, Payload);
    }
}
