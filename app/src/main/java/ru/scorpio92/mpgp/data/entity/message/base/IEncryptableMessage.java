package ru.scorpio92.mpgp.data.entity.message.base;

import java.security.PrivateKey;

/**
 * Created by scorpio92 on 1/20/18.
 */

public interface IEncryptableMessage<P extends EncryptablePayload> extends IBaseMessage {

    P getPayload(PrivateKey privateKey) throws Exception;
}
