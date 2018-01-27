package ru.scorpio92.mpgp.data.entity.message.base;

import ru.scorpio92.mpgp.util.crypto.RSA;

/**
 * Created by scorpio92 on 1/4/18.
 */

public abstract class EncryptablePayload extends Payload {

    public String getEncryptedString(String publicKey) throws Exception {
        return RSA.encryptToBase64(RSA.convertStringToPublicKey(publicKey), toString());
    }
}
