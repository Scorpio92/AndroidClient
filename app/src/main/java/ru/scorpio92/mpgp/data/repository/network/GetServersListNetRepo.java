package ru.scorpio92.mpgp.data.repository.network;

import ru.scorpio92.mpgp.data.repository.network.core.INetworkRepository;
import ru.scorpio92.mpgp.data.repository.network.core.NetworkCallback;
import ru.scorpio92.mpgp.data.repository.network.core.NetworkRepository;
import ru.scorpio92.mpgp.data.repository.network.core.RequestSpecification;
import ru.scorpio92.mpgp.util.Logger;

/**
 * Created by scorpio92 on 1/13/18.
 */

public class GetServersListNetRepo extends NetworkRepository implements INetworkRepository {

    public interface Callback {
        void onSuccess();

        void onError(Exception e);
    }

    private Callback callback;

    public GetServersListNetRepo(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void execute(RequestSpecification requestSpecification) {
        makeRequest(requestSpecification, new NetworkCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    Logger.log("TEST", response);

                } catch (Exception e) {
                    if (callback != null)
                        callback.onError(e);
                }
            }

            @Override
            public void onError(Exception e) {
                if (callback != null)
                    callback.onError(e);
            }
        });
    }

    @Override
    public void cancel() {
        callback = null;
    }
}
