package ru.scorpio92.mpgp.presentation.view.base;

import android.content.Context;

/**
 * Created by scorpio92 on 1/6/18.
 */

public interface IBaseActivity {

    Context getViewContext();

    void showProgress(boolean show);

    void showError(String error);
}
