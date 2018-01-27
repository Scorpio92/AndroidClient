package ru.scorpio92.mpgp.presentation.presenter.base;

/**
 * Created by scorpio92 on 1/6/18.
 */

public interface IAuthPresenter extends IBasePresenter {

    void checkAuthState();

    void register(String username);
}
