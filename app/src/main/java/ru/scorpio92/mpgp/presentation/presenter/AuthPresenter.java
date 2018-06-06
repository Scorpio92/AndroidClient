package ru.scorpio92.mpgp.presentation.presenter;

import android.support.annotation.NonNull;

import io.reactivex.observers.DisposableObserver;
import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.data.model.RegInfo;
import ru.scorpio92.mpgp.domain.RegisterUsecase;
import ru.scorpio92.mpgp.exception.reg.InvaidLoginException;
import ru.scorpio92.mpgp.exception.reg.InvaidNicknameException;
import ru.scorpio92.mpgp.exception.reg.InvaidPasswordException;
import ru.scorpio92.mpgp.exception.reg.LoginExistsException;
import ru.scorpio92.mpgp.exception.reg.NicknameExistsException;
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
        registerUsecase.execute(new DisposableObserver<Void>() {
            @Override
            protected void onStart() {
                if (checkView())
                    getView().showProgress();
            }

            @Override
            public void onNext(Void aVoid) {

            }

            @Override
            public void onError(Throwable e) {
                handleErrors((Exception) e);
            }

            @Override
            public void onComplete() {
                if (checkView()) {
                    getView().hideProgress();
                    getView().onSuccessRegistration();
                }
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
        String error;
        if (e instanceof InvaidLoginException) {
            error = getView().getViewContext().getString(R.string.invalid_login);
        } else if (e instanceof InvaidNicknameException) {
            error = getView().getViewContext().getString(R.string.invalid_nickname);
        } else if (e instanceof InvaidPasswordException) {
            error = getView().getViewContext().getString(R.string.invalid_password);
        } else if (e instanceof LoginExistsException) {
            error = getView().getViewContext().getString(R.string.login_exists);
        } else if (e instanceof NicknameExistsException) {
            error = getView().getViewContext().getString(R.string.nickname_exists);
        } else {
            error = getView().getViewContext().getString(R.string.wtf_error);
        }
        if (checkView())
            getView().onError(error);
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
