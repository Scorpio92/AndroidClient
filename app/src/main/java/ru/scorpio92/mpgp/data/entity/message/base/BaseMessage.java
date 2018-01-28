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

    protected String Type;
    protected String Status;
    protected String ErrorCode;
    protected String Payload;

    public BaseMessage() {
    }

    public BaseMessage(MessageType type, String payload) {
        this.Type = type.name();
        this.Payload = payload;
    }

    public MessageType getType() {
        return Enum.valueOf(MessageType.class, Type);
    }

    public Status getStatus() {
        return Enum.valueOf(Status.class, Status);
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    @Override
    public String toString() {
        return JsonWorker.getSerializeJson(this);
    }
}
