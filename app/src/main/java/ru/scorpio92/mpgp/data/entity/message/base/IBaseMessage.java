package ru.scorpio92.mpgp.data.entity.message.base;

/**
 * Created by scorpio92 on 1/20/18.
 */

public interface IBaseMessage {

    BaseMessage.MessageType getType();

    BaseMessage.Status getStatus();

    String getErrorCode();
}
