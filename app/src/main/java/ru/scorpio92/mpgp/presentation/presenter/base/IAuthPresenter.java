package ru.scorpio92.mpgp.presentation.presenter.base;

import ru.scorpio92.sdk.architecture.presentation.presenter.IBasePresenter;

public interface IAuthPresenter extends IBasePresenter {

    void checkAuthState();

    void register(String username);

    void authorize();
}
