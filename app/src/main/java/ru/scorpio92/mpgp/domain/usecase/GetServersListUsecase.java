package ru.scorpio92.mpgp.domain.usecase;

import ru.scorpio92.mpgp.data.entity.message.api.APIServersList;
import ru.scorpio92.mpgp.data.repository.network.GetServersListNetRepo;
import ru.scorpio92.mpgp.data.repository.network.core.INetworkRepository;
import ru.scorpio92.mpgp.data.repository.network.specifications.GetServersListSpecification;
import ru.scorpio92.mpgp.domain.threading.ThreadExecutor;
import ru.scorpio92.mpgp.domain.threading.base.IExecutor;
import ru.scorpio92.mpgp.domain.usecase.base.AbstractUsecase;
import ru.scorpio92.mpgp.domain.usecase.base.IUsecaseBaseCallback;

/**
 * Created by scorpio92 on 2/1/18.
 */

public class GetServersListUsecase extends AbstractUsecase {

    private Callback callback;
    private INetworkRepository repository;

    public GetServersListUsecase(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        repository = new GetServersListNetRepo(new GetServersListNetRepo.Callback() {
            @Override
            public void onSuccess(APIServersList apiServersList) {
                runOnUI(() -> {
                    if(callback != null)
                        callback.onSuccess(apiServersList);
                });
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

    @Override
    protected IExecutor provideExecutor() {
        return ThreadExecutor.getInstance(true);
    }

    public interface Callback extends IUsecaseBaseCallback {
        void onSuccess(APIServersList apiServersList);
    }
}
