package ru.scorpio92.mpgp.presentation.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import ru.scorpio92.mpgp.BuildConfig;
import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.presentation.presenter.AuthPresenter;
import ru.scorpio92.mpgp.presentation.presenter.base.IAuthPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IAuthActivity;
import ru.scorpio92.mpgp.util.ViewUtils;
import ru.scorpio92.sdk.architecture.presentation.view.BaseActivity;

public class AuthActivity extends BaseActivity<IAuthPresenter> implements IAuthActivity {

    private ProgressBar progressBar;
    private RelativeLayout authContainer;
    private AppCompatEditText login;
    private AppCompatTextView tvError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initUI();
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
            i.setData(Uri.parse(BuildConfig.PROJECT_URL));
            startActivity(i);
        });
    }

    private void register() {
        ViewUtils.hideSoftKeyboard(this, getCurrentFocus());
        getPresenter().register(login.getText().toString().trim());
    }

    @Override
    public void onSuccessRegistration() {
        ViewUtils.showToast(this, "Регистрация успешно пройдена!");
        getPresenter().authorize();
    }

    @Override
    public void onSuccessAuth() {
        ViewUtils.showToast(this, "Авторизация успешно пройдена!");
        finish();
    }

    @Override
    public void onError(@NonNull String error) {
        authContainer.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
        if (!error.isEmpty())
            tvError.setText(error);
    }

    @Override
    public void showProgress() {
        authContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        authContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    protected IAuthPresenter providePresenter() {
        return new AuthPresenter(this);
    }
}