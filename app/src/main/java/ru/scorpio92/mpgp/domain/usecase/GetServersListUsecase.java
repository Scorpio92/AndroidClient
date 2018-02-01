package ru.scorpio92.mpgp.domain.usecase;

import ru.scorpio92.mpgp.data.repository.network.GetServersListNetRepo;
import ru.scorpio92.mpgp.data.repository.network.core.INetworkRepository;
import ru.scorpio92.mpgp.data.repository.network.specifications.GetServersListSpecification;
import ru.scorpio92.mpgp.domain.threading.base.IExecutor;
import ru.scorpio92.mpgp.domain.threading.base.IMainThread;
import ru.scorpio92.mpgp.domain.usecase.base.AbstractUsecase;
import ru.scorpio92.mpgp.domain.usecase.base.IUsecaseBaseCallback;

/**
 * Created by scorpio92 on 2/1/18.
 */

public class GetServersListUsecase extends AbstractUsecase {

    private Callback callback;
    private INetworkRepository repository;

    public GetServersListUsecase(IExecutor executor, IMainThread mainThread, Callback callback) {
        super(executor, mainThread);
        this.callback = callback;
    }

    @Override
    public void run() {
        repository = new GetServersListNetRepo(new GetServersListNetRepo.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                runOnUI(() -> {
                    if(callback != null)
                        callback.onError(e);
                });
            }
        });
        repository.execute(new GetServersListSpecification());
    }

    @Override
    protected void onInterrupt() {
        callback = null;
        if(repository != null)
            repository.cancel();
    }

    public interface Callback extends IUsecaseBaseCallback {
        void onSuccess();
    }
}
