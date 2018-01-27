package ru.scorpio92.mpgp.data.entity.message.auth;

import java.security.PrivateKey;

import ru.scorpio92.mpgp.data.entity.message.base.EncryptableMessage;
import ru.scorpio92.mpgp.data.entity.message.base.IEncryptableMessage;
import ru.scorpio92.mpgp.util.JsonWorker;

/**
 * Created by scorpio92 on 1/17/18.
 */

public class RegisterMessage extends EncryptableMessage implements IEncryptableMessage<RegisterPayload> {

    public RegisterMessage(String serverPublicKeyId, String serverPublicKey, String clientPublicKey, RegisterPayload payload) throws Exception {
        super(MessageType.REGISTER, serverPublicKeyId, serverPublicKey, clientPublicKey, payload);
    }

    @Override
    public RegisterPayload getPayload(PrivateKey privateKey) throws Exception {
        return JsonWorker.getDeserializeJson(getDecryptedPayloadString(privateKey), RegisterPayload.class);
    }
}
