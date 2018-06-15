package ru.scorpio92.mpgp.domain;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import ru.scorpio92.mpgp.data.model.BaseMessage;
import ru.scorpio92.mpgp.data.model.RegInfo;
import ru.scorpio92.mpgp.data.model.request.RegisterServerDataRequest;
import ru.scorpio92.mpgp.data.repository.network.AuthRepo;
import ru.scorpio92.mpgp.util.JsonWorker;
import ru.scorpio92.sdk.architecture.domain.rxusecase.RxAbstractUseCase;

public class RegisterUseCase extends RxAbstractUseCase<Void> {

    private RegInfo regInfo;
    private AuthRepo authRepo;

    public RegisterUseCase(RegInfo regInfo) {
        this.regInfo = regInfo;
        authRepo = new AuthRepo();
    }

    @Override
    protected void checkPreconditions(@NonNull DisposableObserver<Void> observer) throws Exception {
        super.checkPreconditions(observer);
    }

    @Override
    protected Observable<Void> buildObservable() throws Exception {
        BaseMessage regMsg = new BaseMessage(BaseMessage.Type.REGISTER);
        regMsg.setServerData(JsonWorker.getSerializeJson(
                new RegisterServerDataRequest(
                        regInfo.getLogin(),
                        regInfo.getPassword(),
                        regInfo.getNickname()
                )));
        return authRepo.register(regMsg).toObservable();
    }

    @Override
    public void cancel() {
        super.cancel();
        authRepo.cancel();
    }
}