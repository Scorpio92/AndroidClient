package ru.scorpio92.mpgp.presentation.presenter;

import ru.scorpio92.mpgp.domain.threading.MainThread;
import ru.scorpio92.mpgp.domain.threading.ThreadExecutor;
import ru.scorpio92.mpgp.domain.usecase.AuthorizeUsecase;
import ru.scorpio92.mpgp.domain.usecase.RegisterUsecase;
import ru.scorpio92.mpgp.domain.usecase.base.IAbstractUsecase;
import ru.scorpio92.mpgp.exception.general.EmptyFieldException;
import ru.scorpio92.mpgp.presentation.presenter.base.AbstractPresenter;
import ru.scorpio92.mpgp.presentation.presenter.base.IAuthPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IAuthActivity;
import ru.scorpio92.mpgp.util.LocalStorage;

/**
 * Created by scorpio92 on 1/6/18.
 */

public class AuthPresenter extends AbstractPresenter<IAuthActivity> implements IAuthPresenter {

    private IAbstractUsecase registerUsecase;
    private IAbstractUsecase authorizeUsecase;
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
        if (username.isEmpty()) {
            handleError(new EmptyFieldException("никнейм"));
            return;
        }

        showProgressInView();

        registerUsecase = new RegisterUsecase(ThreadExecutor.getInstance(true), MainThread.getInstance(), localStorage, username, new RegisterUsecase.Callback() {
            @Override
            public void onRegistered() {
                if (viewIsReady())
                    getView().onSuccessReg();
            }

            @Override
            public void onError(Exception e) {
                hideProgressInView();
                handleError(e);
            }
        });
        registerUsecase.execute();

    }

    @Override
    public void authorize() {
        showProgressInView();

        authorizeUsecase = new AuthorizeUsecase(ThreadExecutor.getInstance(true), MainThread.getInstance(), localStorage, new AuthorizeUsecase.Callback() {
            @Override
            public void onAuthorized() {
                if (viewIsReady())
                    getView().onSuccessAuth();
            }

            @Override
            public void onError(Exception e) {
                hideProgressInView();
                handleError(e);
            }
        });
        authorizeUsecase.execute();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (registerUsecase != null)
            registerUsecase.cancel();
        if (authorizeUsecase != null)
            authorizeUsecase.cancel();
    }
}
