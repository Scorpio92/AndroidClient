package ru.scorpio92.mpgp.data.entity.message.base;

import ru.scorpio92.mpgp.util.JsonWorker;

/**
 * Created by scorpio92 on 1/4/18.
 */

public abstract class BaseMessage {

    public enum MessageType {
        GET_SERVER_KEY,
        REGISTER,
        AUTHORIZE
    }

    public enum Status {
        SUCCESS,
        ERROR
    }

    protected String type;
    protected String status;
    protected String errorCode;
    protected String payload;

    public BaseMessage() {
    }

    public BaseMessage(MessageType type, String payload) {
        this.type = type.name();
        this.payload = payload;
    }

    public MessageType getType() {
        return Enum.valueOf(MessageType.class, type);
    }

    public Status getStatus() {
        return Enum.valueOf(Status.class, status);
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return JsonWorker.getSerializeJson(this);
    }
}
