package ru.scorpio92.mpgp.presentation.view.fragment;

import android.support.annotation.NonNull;

import ru.scorpio92.mpgp.presentation.presenter.AuthPresenter;
import ru.scorpio92.mpgp.presentation.presenter.base.IAuthPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IAuthFragment;
import ru.scorpio92.sdk.architecture.presentation.view.BaseFragment;

public class AuthFragment extends BaseFragment<IAuthPresenter> implements IAuthFragment {

    @Override
    public void onSuccessAuth() {

    }

    @NonNull
    @Override
    protected IAuthPresenter providePresenter() {
        return new AuthPresenter(this);
    }
}
