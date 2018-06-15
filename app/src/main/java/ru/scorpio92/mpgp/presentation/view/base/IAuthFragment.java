package ru.scorpio92.mpgp.presentation.view.base;

import ru.scorpio92.sdk.architecture.presentation.view.IBaseView;

public interface IAuthFragment extends IBaseView {

    void onFailedLoginValidation(String error);

    void onFailedPasswordValidation(String error);

    void onSuccessAuth();
}
