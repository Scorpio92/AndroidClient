package ru.scorpio92.mpgp.presentation.presenter;

import android.support.annotation.NonNull;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.presentation.presenter.base.IAuthPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IAuthFragment;
import ru.scorpio92.mpgp.util.Logger;
import ru.scorpio92.sdk.architecture.presentation.presenter.BasePresenter;

public class AuthPresenter extends BasePresenter<IAuthFragment> implements IAuthPresenter {

    public AuthPresenter(IAuthFragment view) {
        super(view);
    }

    @Override
    public void authorize(String login, String password) {

    }

    @Override
    protected void onCustomHandleErrors(@NonNull Exception e) {
        super.onCustomHandleErrors(e);
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
