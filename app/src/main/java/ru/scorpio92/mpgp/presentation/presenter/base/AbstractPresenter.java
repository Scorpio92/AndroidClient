package ru.scorpio92.mpgp.presentation.presenter.base;

import ru.scorpio92.mpgp.exception.general.WtfException;
import ru.scorpio92.mpgp.presentation.view.base.IBaseActivity;
import ru.scorpio92.mpgp.util.Logger;

/**
 * Created by scorpio92 on 12/19/17.
 */

public abstract class AbstractPresenter<V extends IBaseActivity> implements IBasePresenter {

    protected V view;

    public AbstractPresenter(V view) {
        this.view = view;
    }

    protected V getView() {
        return view;
    }

    protected boolean viewIsReady() {
        return view != null;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        view = null;
    }

    protected void showProgressInView() {
        if(viewIsReady())
            view.showProgress(true);
    }

    protected void hideProgressInView() {
        if(viewIsReady())
            view.showProgress(false);
    }

    protected void handleError(Exception e) {
        if(e != null) {
            sendErrorToView(e);
        } else {
            sendErrorToView(new WtfException());
        }
    }

    protected void sendErrorToView(Exception e) {
        if (e != null) {
            Logger.error(e);
            if (viewIsReady())
                view.showError(e.getMessage());
        }
    }
}
