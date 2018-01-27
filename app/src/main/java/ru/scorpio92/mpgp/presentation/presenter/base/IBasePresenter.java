package ru.scorpio92.mpgp.presentation.presenter.base;

/**
 * Created by scorpio92 on 12/19/17.
 */

public interface IBasePresenter {

    void onResume();

    void onPause();

    void onStart();

    void onStop();

    void onDestroy();
}
