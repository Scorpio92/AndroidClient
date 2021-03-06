package ru.scorpio92.mpgp.presentation.presenter.base;

import ru.scorpio92.sdk.architecture.presentation.presenter.IBasePresenter;

public interface IAuthPresenter extends IBasePresenter {

    void authorize(String login, String password);
}
