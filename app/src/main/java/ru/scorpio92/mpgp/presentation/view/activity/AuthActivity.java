package ru.scorpio92.mpgp.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.presentation.view.base.IFragmentListener;
import ru.scorpio92.mpgp.presentation.view.fragment.AuthFragment;
import ru.scorpio92.mpgp.presentation.view.fragment.RegistrationFragment;
import ru.scorpio92.mpgp.presentation.view.fragment.StartFragment;
import ru.scorpio92.sdk.architecture.presentation.view.BaseFragment;

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
                replaceFragment(new RegistrationFragment());
                break;
            case NEED_AUTH:
                replaceFragment(new AuthFragment());
                break;
            case SUCCESS_WS_AUTH:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1)
            finish();
        else
            super.onBackPressed();
    }

    private void initUI() {
        replaceFragment(new StartFragment());
    }

    private void replaceFragment(BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, BaseFragment.class.getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}