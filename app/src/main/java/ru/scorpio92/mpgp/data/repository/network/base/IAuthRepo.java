package ru.scorpio92.mpgp.data.repository.network.base;

import io.reactivex.Observable;
import ru.scorpio92.mpgp.data.model.BaseMessage;

public interface IAuthRepo {

    Observable<Boolean> register(BaseMessage regMsg);
}
