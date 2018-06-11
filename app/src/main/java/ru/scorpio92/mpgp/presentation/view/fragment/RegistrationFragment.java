package ru.scorpio92.mpgp.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.presentation.presenter.RegistrationPresenter;
import ru.scorpio92.mpgp.presentation.presenter.base.IRegistrationPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IFragmentListener;
import ru.scorpio92.mpgp.presentation.view.base.IRegistrationFragment;
import ru.scorpio92.mpgp.util.ViewUtils;
import ru.scorpio92.sdk.architecture.presentation.view.BaseFragment;

public class RegistrationFragment extends BaseFragment<IRegistrationPresenter> implements IRegistrationFragment {

    private IFragmentListener listener;
    private CoordinatorLayout rootLayout;
    private LinearLayoutCompat lpContainer, nickContainer;
    private TextInputLayout loginTILayout, passwordTILayout, nicknameTILayout;
    private AppCompatEditText login, password, nickname;

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
    public void onFailedLoginValidation(String error) {
        loginTILayout.setError(error);
    }

    @Override
    public void onFailedPasswordValidation(String error) {
        passwordTILayout.setError(error);
    }

    @Override
    public void onFailedNicknameValidation(String error) {
        nicknameTILayout.setError(error);
    }

    @Override
    public void onStep1() {
        lpContainer.setVisibility(View.VISIBLE);
        nickContainer.setVisibility(View.GONE);
    }

    @Override
    public void onStep2() {
        lpContainer.setVisibility(View.GONE);
        nickContainer.setVisibility(View.VISIBLE);
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


    @Override
    public void onError(@NonNull String error) {
        super.onError(error);
        ViewUtils.showShackBar(rootLayout, error);
    }

    private void initUI(View view) {
        rootLayout = view.findViewById(R.id.root);

        lpContainer = view.findViewById(R.id.lpContainer);
        lpContainer.setVisibility(View.VISIBLE);

        loginTILayout = view.findViewById(R.id.loginTILayout);
        login = view.findViewById(R.id.login);

        passwordTILayout = view.findViewById(R.id.passwordTILayout);
        password = view.findViewById(R.id.password);

        view.findViewById(R.id.continueBtn).setOnClickListener(v -> {
            loginTILayout.setError("");
            passwordTILayout.setError("");
            getPresenter().onLPInput(
                    login.getText().toString().trim(),
                    password.getText().toString().trim()
            );
        });

        nickContainer = view.findViewById(R.id.nickContainer);
        nickContainer.setVisibility(View.GONE);

        nicknameTILayout = view.findViewById(R.id.nicknameTILayout);
        nickname = view.findViewById(R.id.nickname);

        view.findViewById(R.id.backBtn).setOnClickListener(v -> onStep1());

        view.findViewById(R.id.regBtn).setOnClickListener(v -> {
            nicknameTILayout.setError("");
            getPresenter().register(
                    login.getText().toString().trim(),
                    password.getText().toString().trim(),
                    nickname.getText().toString().trim()
            );
        });
    }
}
