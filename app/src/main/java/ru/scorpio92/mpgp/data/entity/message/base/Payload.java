package ru.scorpio92.mpgp.data.entity.message.base;

import ru.scorpio92.mpgp.util.JsonWorker;

/**
 * Created by scorpio92 on 1/4/18.
 */

public abstract class Payload {

    @Override
    public String toString() {
        return JsonWorker.getSerializeJson(this);
    }
}
