package ru.scorpio92.mpgp.presentation.presenter;

import android.support.annotation.NonNull;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.data.model.request.RegisterServerDataRequest;
import ru.scorpio92.mpgp.domain.AuthorizeUseCase;
import ru.scorpio92.mpgp.exception.auth.IncorrectPairException;
import ru.scorpio92.mpgp.exception.reg.InvaidLoginException;
import ru.scorpio92.mpgp.exception.reg.InvaidPasswordException;
import ru.scorpio92.mpgp.presentation.presenter.base.IAuthPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IAuthFragment;
import ru.scorpio92.mpgp.util.LocalStorage;
import ru.scorpio92.mpgp.util.Logger;
import ru.scorpio92.mpgp.util.ValidateUtils;
import ru.scorpio92.sdk.architecture.domain.rxusecase.SimpleObserver;
import ru.scorpio92.sdk.architecture.presentation.presenter.BasePresenter;

public class AuthPresenter extends BasePresenter<IAuthFragment> implements IAuthPresenter {

    private AuthorizeUseCase authorizeUseCase;

    public AuthPresenter(IAuthFragment view) {
        super(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (checkView())
            LocalStorage.initLocalStorage(getView().getViewContext());
    }

    @Override
    public void authorize(String login, String password) {
        if (!ValidateUtils.validateParam(login, RegisterServerDataRequest.LOGIN_REGEXP)) {
            getView().onFailedLoginValidation(getView().getViewContext().getString(R.string.invalid_login));
            return;
        }

        if (!ValidateUtils.validateParam(password, RegisterServerDataRequest.PASSWORD_REGEXP)) {
            getView().onFailedPasswordValidation(getView().getViewContext().getString(R.string.invalid_password));
            return;
        }

        authorizeUseCase = new AuthorizeUseCase(login, password);
        authorizeUseCase.execute(new SimpleObserver<Void>() {
            @Override
            protected void onStart() {
                super.onStart();
                if(checkView())
                    getView().showProgress();
            }

            @Override
            public void onComplete() {
                if (checkView()) {
                    getView().hideProgress();
                    getView().onSuccessAuth();
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
        } else if (e instanceof InvaidPasswordException) {
            error = getView().getViewContext().getString(R.string.invalid_password);
        } else if (e instanceof IncorrectPairException) {
            error = getView().getViewContext().getString(R.string.incorrect_pair);
        }  else {
            error = provideDefaultErrorMsg();
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
        if (authorizeUseCase != null)
            authorizeUseCase.cancel();
    }
}
