package ru.scorpio92.mpgp.presentation.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import ru.scorpio92.mpgp.Constants;
import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.presentation.presenter.AuthPresenter;
import ru.scorpio92.mpgp.presentation.presenter.base.IAuthPresenter;
import ru.scorpio92.mpgp.presentation.view.base.AbstractActivity;
import ru.scorpio92.mpgp.presentation.view.base.IAuthActivity;

/**
 * Created by scorpio92 on 1/6/18.
 */

public class AuthActivity extends AbstractActivity<IAuthPresenter> implements IAuthActivity {

    private ProgressBar progressBar;
    private RelativeLayout authContainer;
    private AppCompatEditText login;
    private AppCompatTextView tvError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initUI();

        registerPresenter(new AuthPresenter(this));
        getPresenter().checkAuthState();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initUI() {

        progressBar = findViewById(R.id.progress);

        authContainer = findViewById(R.id.authContainer);

        tvError = findViewById(R.id.error);

        login = findViewById(R.id.login);
        login.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                register();
            }
            return false;
        });

        findViewById(R.id.regBtn).setOnClickListener(v -> {
            register();
        });

        findViewById(R.id.link).setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Constants.PROJECT_URL));
            startActivity(i);
        });
    }

    private void register() {
        hideKeyboard();
        getPresenter().register(login.getText().toString().trim());
    }

    @Override
    public void onSuccessReg() {
        showToast("Регистрация успешно пройдена!");
        getPresenter().authorize();
    }

    @Override
    public void onSuccessAuth() {
        showToast("Авторизация успешно пройдена!");
        startActivity(new Intent(this, ConnectionActivity.class));
        finish();
    }

    @Override
    public void showError(String error) {
        authContainer.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
        if (error != null && !error.isEmpty())
            tvError.setText(error);
    }

    @Override
    public void showProgress(boolean show) {
        authContainer.setVisibility(View.GONE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}