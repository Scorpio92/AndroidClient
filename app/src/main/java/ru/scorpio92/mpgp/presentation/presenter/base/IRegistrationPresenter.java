package ru.scorpio92.mpgp.presentation.presenter.base;

import ru.scorpio92.sdk.architecture.presentation.presenter.IBasePresenter;

public interface IRegistrationPresenter extends IBasePresenter {

    void onLPInput(String login, String password);

    void register(String login, String password, String nickname);
}
