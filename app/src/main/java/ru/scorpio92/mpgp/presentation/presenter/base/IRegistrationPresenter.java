package ru.scorpio92.mpgp.presentation.presenter.base;

import ru.scorpio92.mpgp.data.model.RegInfo;
import ru.scorpio92.sdk.architecture.presentation.presenter.IBasePresenter;

public interface IRegistrationPresenter extends IBasePresenter {

    void register(RegInfo regInfo);
}
