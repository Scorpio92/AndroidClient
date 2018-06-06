package ru.scorpio92.mpgp.data.repository.network.base;

import io.reactivex.Completable;
import ru.scorpio92.mpgp.data.model.BaseMessage;

public interface IAuthRepo {

    Completable register(BaseMessage regMsg);
}
