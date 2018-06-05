package ru.scorpio92.mpgp.data.repository.network;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.scorpio92.mpgp.data.model.BaseMessage;

public interface API {

    @POST("/")
    Observable<BaseMessage> register(@Body BaseMessage regMsg);
}
