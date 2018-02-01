package ru.scorpio92.mpgp.presentation.presenter;

import ru.scorpio92.mpgp.domain.threading.MainThread;
import ru.scorpio92.mpgp.domain.threading.ThreadExecutor;
import ru.scorpio92.mpgp.domain.usecase.GetServersListUsecase;
import ru.scorpio92.mpgp.domain.usecase.base.IAbstractUsecase;
import ru.scorpio92.mpgp.presentation.presenter.base.AbstractPresenter;
import ru.scorpio92.mpgp.presentation.presenter.base.IConnectionPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IConnectionActivity;

/**
 * Created by scorpio92 on 2/1/18.
 */

public class ConnectionPresenter extends AbstractPresenter<IConnectionActivity> implements IConnectionPresenter {

    private IAbstractUsecase getServersListUsecase;

    public ConnectionPresenter(IConnectionActivity view) {
        super(view);
    }

    @Override
    public void getServerList() {
        getServersListUsecase = new GetServersListUsecase(ThreadExecutor.getInstance(true), MainThread.getInstance(), new GetServersListUsecase.Callback() {
            @Override
            public void onSuccess() {
                if(viewIsReady()) {
                    getView().renderServerList();
                }
            }

            @Override
            public void onError(Exception e) {
                hideProgressInView();
                handleError(e);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
