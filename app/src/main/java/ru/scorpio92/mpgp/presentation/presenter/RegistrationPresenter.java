package ru.scorpio92.mpgp.presentation.presenter;

import android.support.annotation.NonNull;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.data.model.RegInfo;
import ru.scorpio92.mpgp.domain.RegisterUsecase;
import ru.scorpio92.mpgp.exception.reg.InvaidLoginException;
import ru.scorpio92.mpgp.exception.reg.InvaidNicknameException;
import ru.scorpio92.mpgp.exception.reg.InvaidPasswordException;
import ru.scorpio92.mpgp.exception.reg.LoginExistsException;
import ru.scorpio92.mpgp.exception.reg.NicknameExistsException;
import ru.scorpio92.mpgp.presentation.presenter.base.IRegistrationPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IRegistrationFragment;
import ru.scorpio92.mpgp.util.Logger;
import ru.scorpio92.sdk.architecture.domain.rxusecase.SimpleObserver;
import ru.scorpio92.sdk.architecture.presentation.presenter.BasePresenter;

public class RegistrationPresenter extends BasePresenter<IRegistrationFragment> implements IRegistrationPresenter {

    private RegisterUsecase registerUsecase;

    public RegistrationPresenter(@NonNull IRegistrationFragment view) {
        super(view);
    }

    @Override
    public void register(RegInfo regInfo) {
        registerUsecase = new RegisterUsecase(regInfo);
        registerUsecase.execute(new SimpleObserver<Void>() {
            @Override
            protected void onStart() {
                if (checkView())
                    getView().showProgress();
            }

            @Override
            public void onComplete() {
                if (checkView()) {
                    getView().hideProgress();
                    getView().onSuccessRegistration();
                }
            }

            @Override
            public void onError(Throwable e) {
                handleErrors((Exception) e);
            }
        });
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
        return getView().getViewContext().getString(R.string.wtf_error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (registerUsecase != null)
            registerUsecase.cancel();
    }
}
