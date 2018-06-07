package ru.scorpio92.mpgp.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.presentation.presenter.RegistrationPresenter;
import ru.scorpio92.mpgp.presentation.presenter.base.IRegistrationPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IFragmentListener;
import ru.scorpio92.mpgp.presentation.view.base.IRegistrationFragment;
import ru.scorpio92.sdk.architecture.presentation.view.BaseFragment;

public class RegistrationFragment extends BaseFragment<IRegistrationPresenter> implements IRegistrationFragment {

    private IFragmentListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        initUI(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (IFragmentListener) context;
    }

    @Override
    public void onSuccessRegistration() {
        if (listener != null)
            listener.onFragmentResut(IFragmentListener.ResultCode.NEED_AUTH);
    }

    @NonNull
    @Override
    protected IRegistrationPresenter providePresenter() {
        return new RegistrationPresenter(this);
    }

    private void initUI(View view) {
    }
}
