package ru.scorpio92.mpgp.data.repository.network.specifications;

import ru.scorpio92.mpgp.Constants;
import ru.scorpio92.mpgp.data.entity.message.base.BaseMessage;
import ru.scorpio92.mpgp.data.repository.network.core.RequestSpecification;

/**
 * Created by scorpio92 on 1/13/18.
 */

public class AuthSpecification extends RequestSpecification {

    public AuthSpecification(BaseMessage message) {
        super(Constants.AUTH_SERVER + ":" + Constants.AUTH_SERVER_PORT, message);
    }
}
