package ru.scorpio92.mpgp.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.presentation.presenter.AuthPresenter;
import ru.scorpio92.mpgp.presentation.presenter.base.IAuthPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IAuthFragment;
import ru.scorpio92.mpgp.presentation.view.base.IFragmentListener;
import ru.scorpio92.mpgp.util.ViewUtils;
import ru.scorpio92.sdk.architecture.presentation.view.BaseFragment;

public class AuthFragment extends BaseFragment<IAuthPresenter> implements IAuthFragment {

    private IFragmentListener listener;
    private CoordinatorLayout rootLayout;
    private TextInputLayout loginTILayout, passwordTILayout;
    private AppCompatEditText login, password;
    private AlertDialog authDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authorization, container, false);
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
    public void onSuccessAuth() {
        if(listener != null)
            listener.onFragmentResut(IFragmentListener.ResultCode.SUCCESS_AUTH);
    }

    @NonNull
    @Override
    protected IAuthPresenter providePresenter() {
        return new AuthPresenter(this);
    }

    @Override
    public void showProgress() {
        super.showProgress();
        if(getContext() != null)
            authDialog = ViewUtils.showProgressDialog(getContext(), getString(R.string.authorization_process));
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        ViewUtils.safetyDismissDialog(authDialog);
    }

    @Override
    public void onError(@NonNull String error) {
        super.onError(error);
        ViewUtils.showShackBar(rootLayout, error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

    private void initUI(View view) {
        rootLayout = view.findViewById(R.id.root);

        loginTILayout = view.findViewById(R.id.loginTILayout);
        login = view.findViewById(R.id.login);

        passwordTILayout = view.findViewById(R.id.passwordTILayout);
        password = view.findViewById(R.id.password);

        view.findViewById(R.id.continueBtn).setOnClickListener(view1 -> {
            ViewUtils.hideSoftKeyboard(getActivity());
            loginTILayout.setError("");
            passwordTILayout.setError("");
            getPresenter().authorize(
                    login.getText().toString().trim(),
                    password.getText().toString().trim()
            );
        });
    }
}
