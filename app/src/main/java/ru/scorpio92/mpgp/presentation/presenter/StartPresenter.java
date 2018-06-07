package ru.scorpio92.mpgp.presentation.presenter;

import android.support.annotation.NonNull;

import ru.scorpio92.mpgp.presentation.presenter.base.IStartPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IStartFragment;
import ru.scorpio92.mpgp.util.LocalStorage;
import ru.scorpio92.sdk.architecture.presentation.presenter.BasePresenter;

public class StartPresenter extends BasePresenter<IStartFragment> implements IStartPresenter {

    private LocalStorage localStorage;

    public StartPresenter(@NonNull IStartFragment view) {
        super(view);
        localStorage = LocalStorage.getInstance(getView().getViewContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (localStorage.fileExist(LocalStorage.AUTH_TOKEN_STORAGE)) {
            //отправляем запрос на авторизацию на сервер WS
            authOnWS();
        } else {
            if (checkView())
                getView().onNeedRegistration();
        }
    }

    private void authOnWS() {

    }
}
