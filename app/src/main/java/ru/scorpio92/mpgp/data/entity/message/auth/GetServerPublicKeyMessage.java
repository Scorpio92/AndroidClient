package ru.scorpio92.mpgp.data.entity.message.auth;

import ru.scorpio92.mpgp.data.entity.message.base.IMessage;
import ru.scorpio92.mpgp.data.entity.message.base.Message;
import ru.scorpio92.mpgp.util.JsonWorker;

/**
 * Created by scorpio92 on 1/13/18.
 */

public class GetServerPublicKeyMessage extends Message implements IMessage<GetServerPublicKeyPayload> {

    public GetServerPublicKeyMessage() {
        super(MessageType.GET_SERVER_KEY, new GetServerPublicKeyPayload());
    }

    @Override
    public GetServerPublicKeyPayload getPayload() throws Exception {
        return JsonWorker.getDeserializeJson(payload, GetServerPublicKeyPayload.class);
    }
}
