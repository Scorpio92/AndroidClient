package ru.scorpio92.mpgp.data.repository.network;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import ru.scorpio92.mpgp.BuildConfig;
import ru.scorpio92.mpgp.data.model.BaseMessage;
import ru.scorpio92.mpgp.data.repository.network.base.IAuthRepo;
import ru.scorpio92.mpgp.data.repository.network.core.RetrofitNetworkRepository;

public class AuthRepo extends RetrofitNetworkRepository<API> implements IAuthRepo {

    @Override
    public Observable<Boolean> register(BaseMessage regMsg) {
        return getApiInterface().register(regMsg)
                .flatMap(baseMessage -> {
                    if(baseMessage.getStatus() == BaseMessage.Status.SUCCESS) {
                        return Observable.just(true);
                    } else {
                        return Observable.just(false);
                    }
                });
    }

    @Override
    protected API createApiInterface(Retrofit client) {
        return client.create(API.class);
    }

    @Override
    protected String provideBaseURL() {
        return BuildConfig.AUTH_SERVER_IP + ":" + BuildConfig.AUTH_SERVER_PORT;
    }
}
