package ru.scorpio92.mpgp.presentation.presenter;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.exception.auth.BadTokenException;
import ru.scorpio92.mpgp.exception.auth.ClientAlreadyConnectedException;
import ru.scorpio92.mpgp.presentation.presenter.base.IStartPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IStartFragment;
import ru.scorpio92.mpgp.util.LocalStorage;
import ru.scorpio92.mpgp.util.Logger;
import ru.scorpio92.sdk.architecture.presentation.presenter.BasePresenter;

public class StartPresenter extends BasePresenter<IStartFragment> implements IStartPresenter {

    public StartPresenter(@NonNull IStartFragment view) {
        super(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (checkView())
            LocalStorage.initLocalStorage(getView().getViewContext());

        checkAuthTokenExists();
    }

    private void checkAuthTokenExists() {
        try {
            //отправляем запрос на авторизацию на сервер WS
            authorizeOnWS(LocalStorage.getLocalStorageInstance().getDataFromFile(LocalStorage.AUTH_TOKEN_STORAGE));
        } catch (Exception e) {
            Logger.error(e);
            if (checkView())
                getView().onNeedRegistration();
        }
    }

    private void authorizeOnWS(String token) {
        Logger.log("Token", token);
        //тестовая заглушка, имитация неудачной авторизации на WS
        getView().showProgress();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Handler(Looper.getMainLooper()).post(() -> {
                if (checkView()) {
                    getView().onNeedRegistration();
                    handleErrors(new BadTokenException());
                }
            });
        }).start();
    }

    @Override
    protected void onCustomHandleErrors(@NonNull Exception e) {
        super.onCustomHandleErrors(e);
        String error;
        if (e instanceof BadTokenException) {
            error = getView().getViewContext().getString(R.string.bad_token);
            //пытаемся удалить протухший токен
            try {
                LocalStorage.getLocalStorageInstance().deleteFile(LocalStorage.AUTH_TOKEN_STORAGE);
            } catch (Exception e2) {
                Logger.error(e2);
            }
            getView().onNeedRegistration();
        } else if (e instanceof ClientAlreadyConnectedException) {
            error = getView().getViewContext().getString(R.string.client_already_online);
            //хз что тут делать...
        } else {
            error = provideDefaultErrorMsg();
        }
        if (checkView())
            getView().onError(error);
    }

    @Override
    protected void writeExceptionInLog(Exception e) {
        Logger.error(e);
    }

    @Override
    protected String provideDefaultErrorMsg() {
        return getView().getViewContext().getString(R.string.wtf_error);
    }
}
