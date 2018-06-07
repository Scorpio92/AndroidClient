package ru.scorpio92.mpgp.presentation.view.base;

import ru.scorpio92.sdk.architecture.presentation.view.IBaseView;

public interface IStartFragment extends IBaseView {

    void onNeedRegistration();

    void onNeedAuth();

    void onSuccessAuth();
}
