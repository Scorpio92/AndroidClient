package ru.scorpio92.mpgp.presentation.view.base;

import ru.scorpio92.sdk.architecture.presentation.view.IBaseView;

public interface IRegistrationFragment extends IBaseView {

    void onFailedLoginValidation(String error);

    void onFailedPasswordValidation(String error);

    void onFailedNicknameValidation(String error);

    void onStep1();

    void onStep2();

    void onSuccessRegistration();
}
