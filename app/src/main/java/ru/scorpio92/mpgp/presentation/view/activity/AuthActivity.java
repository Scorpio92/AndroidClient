package ru.scorpio92.mpgp.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.presentation.view.base.IFragmentListener;
import ru.scorpio92.mpgp.presentation.view.fragment.AuthFragment;
import ru.scorpio92.mpgp.presentation.view.fragment.RegistrationFragment;
import ru.scorpio92.mpgp.presentation.view.fragment.StartFragment;
import ru.scorpio92.mpgp.util.LocalStorage;
import ru.scorpio92.mpgp.util.ViewUtils;

public class AuthActivity extends AppCompatActivity implements IFragmentListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initUI();
    }

    @Override
    public void onFragmentResut(ResultCode resultCode) {
        switch (resultCode) {
            case NEED_REGISTRATION:
                ViewUtils.replaceFragment(getSupportFragmentManager(), R.id.container, new RegistrationFragment());
                break;
            case TRY_AUTH_IF_USER_HAVE_ACCOUNT:
                ViewUtils.replaceFragment(getSupportFragmentManager(), R.id.container, new AuthFragment());
                break;
            case SUCCESS_REGISTRATION:
                ViewUtils.clearFragmentStack(getSupportFragmentManager());
                ViewUtils.replaceFragment(getSupportFragmentManager(), R.id.container, new AuthFragment());
                break;
            case NEED_AUTH:
                ViewUtils.clearFragmentStack(getSupportFragmentManager());
                ViewUtils.replaceFragment(getSupportFragmentManager(), R.id.container, new AuthFragment());
                break;
            case SUCCESS_AUTH:
                ViewUtils.clearFragmentStack(getSupportFragmentManager());
                ViewUtils.replaceFragment(getSupportFragmentManager(), R.id.container, new StartFragment());
                break;
            case CONNECTED_TO_WS:
                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1)
            ViewUtils.createAndShowAlertDialog(AuthActivity.this, getString(R.string.exit_dialog_title), getString(R.string.exit_dialog_msg), false, (dialogInterface, i) -> {
                dialogInterface.dismiss();
                if (LocalStorage.getLocalStorageInstance() != null)
                    LocalStorage.getLocalStorageInstance().close();
                finish();
            });
        else
            super.onBackPressed();
    }

    private void initUI() {
        ViewUtils.replaceFragment(getSupportFragmentManager(), R.id.container, new StartFragment());
    }
}