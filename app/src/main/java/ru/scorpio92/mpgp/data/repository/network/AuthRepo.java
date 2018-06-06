package ru.scorpio92.mpgp.data.repository.network;

import io.reactivex.Completable;
import retrofit2.Retrofit;
import ru.scorpio92.mpgp.BuildConfig;
import ru.scorpio92.mpgp.data.model.BaseMessage;
import ru.scorpio92.mpgp.data.repository.network.base.IAuthRepo;
import ru.scorpio92.mpgp.data.repository.network.core.RetrofitNetworkRepository;
import ru.scorpio92.mpgp.exception.general.WtfException;
import ru.scorpio92.mpgp.exception.reg.InvaidLoginException;
import ru.scorpio92.mpgp.exception.reg.InvaidNicknameException;
import ru.scorpio92.mpgp.exception.reg.InvaidPasswordException;
import ru.scorpio92.mpgp.exception.reg.LoginExistsException;
import ru.scorpio92.mpgp.exception.reg.NicknameExistsException;

public class AuthRepo extends RetrofitNetworkRepository<API> implements IAuthRepo {

    @Override
    public Completable register(BaseMessage regMsg) {
        return getApiInterface().register(regMsg)
                .flatMapCompletable(baseMessage -> {
                    switch (baseMessage.getStatus()) {
                        case SUCCESS:
                            return Completable.complete();
                        case ERROR:
                            switch (baseMessage.getError().getErrorCode()) {
                                case 1:
                                    throw new InvaidLoginException();
                                case 2:
                                    throw new LoginExistsException();
                                case 3:
                                    throw new InvaidPasswordException();
                                case 4:
                                    throw new InvaidNicknameException();
                                case 5:
                                    throw new NicknameExistsException();
                                default:
                                    throw new WtfException();
                            }
                        default:
                            throw new WtfException();
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
