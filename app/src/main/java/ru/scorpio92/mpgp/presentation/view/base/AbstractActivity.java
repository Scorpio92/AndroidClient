package ru.scorpio92.mpgp.presentation.view.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import ru.scorpio92.mpgp.presentation.presenter.base.IBasePresenter;
import ru.scorpio92.mpgp.util.Logger;


/**
 * Created by scorpio92 on 12/19/17.
 */

public abstract class AbstractActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseActivity{

    private P presenter;

    protected void registerPresenter(P presenter) {
        this.presenter = presenter;
    }

    protected P getPresenter() {
        return presenter;
    }

    protected void hideKeyboard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    protected void showToast(String msg) {
        if (msg != null && !msg.isEmpty())
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        if (presenter != null)
            presenter.onPause();

        super.onPause();
    }

    @Override
    protected void onResume() {
        if (presenter != null)
            presenter.onResume();

        super.onResume();
    }

    @Override
    protected void onStart() {
        if (presenter != null)
            presenter.onStart();

        super.onStart();
    }

    @Override
    protected void onStop() {
        if (presenter != null)
            presenter.onStop();

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null)
            presenter.onDestroy();

        super.onDestroy();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showError(String error) {

    }
}
