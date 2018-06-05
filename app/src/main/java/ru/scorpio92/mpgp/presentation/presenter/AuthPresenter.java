package ru.scorpio92.mpgp.presentation.presenter;

import android.support.annotation.NonNull;

import io.reactivex.observers.DisposableObserver;
import ru.scorpio92.mpgp.data.model.RegInfo;
import ru.scorpio92.mpgp.domain.RegisterUsecase;
import ru.scorpio92.mpgp.presentation.presenter.base.IAuthPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IAuthActivity;
import ru.scorpio92.mpgp.util.LocalStorage;
import ru.scorpio92.mpgp.util.Logger;
import ru.scorpio92.sdk.architecture.presentation.presenter.BasePresenter;

public class AuthPresenter extends BasePresenter<IAuthActivity> implements IAuthPresenter {

    private RegisterUsecase registerUsecase;
    private LocalStorage localStorage;

    public AuthPresenter(IAuthActivity view) {
        super(view);
        localStorage = LocalStorage.getInstance(getView().getViewContext());
    }

    @Override
    public void checkAuthState() {
        if (localStorage.fileExist(LocalStorage.AUTH_TOKEN_STORAGE)) {
            authorize();
        }
    }

    @Override
    public void register(String username) {
        registerUsecase = new RegisterUsecase(new RegInfo(username, username));
        registerUsecase.execute(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (checkView()) {
                    if (aBoolean)
                        getView().onSuccessRegistration();
                    else
                        getView().onError("FUCK!");
                }
            }

            @Override
            public void onError(Throwable e) {
                handleErrors((Exception) e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void authorize() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (registerUsecase != null)
            registerUsecase.cancel();
    }

    @Override
    protected void onCustomHandleErrors(@NonNull Exception e) {
        super.onCustomHandleErrors(e);
    }

    @Override
    protected void writeExceptionInLog(Exception e) {
        Logger.error(e);
    }

    @Override
    protected String provideDefaultErrorMsg() {
        return "OOOPS!";
    }
}
