package ru.scorpio92.mpgp.data.repository.network;

import ru.scorpio92.mpgp.data.entity.message.auth.GetServerPublicKeyMessage;
import ru.scorpio92.mpgp.data.entity.message.auth.GetServerPublicKeyPayload;
import ru.scorpio92.mpgp.data.repository.network.core.INetworkRepository;
import ru.scorpio92.mpgp.data.repository.network.core.NetworkCallback;
import ru.scorpio92.mpgp.data.repository.network.core.NetworkRepository;
import ru.scorpio92.mpgp.data.repository.network.core.RequestSpecification;
import ru.scorpio92.mpgp.exception.general.DeniedServiceException;
import ru.scorpio92.mpgp.exception.general.IpBlockedException;
import ru.scorpio92.mpgp.exception.general.WtfException;
import ru.scorpio92.mpgp.util.JsonWorker;

/**
 * Created by scorpio92 on 1/13/18.
 */

public class GetServerKeyNetRepo extends NetworkRepository implements INetworkRepository {

    public interface Callback {
        void onSuccess(String serverPublicKeyId, String serverPublicKey);

        void onError(Exception e);
    }

    private Callback callback;

    public GetServerKeyNetRepo(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void execute(RequestSpecification requestSpecification) {
        makeRequest(requestSpecification, new NetworkCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    GetServerPublicKeyMessage getServerKeyMsg = JsonWorker.getDeserializeJson(response, GetServerPublicKeyMessage.class);

                    switch (getServerKeyMsg.getStatus()) {
                        case SUCCESS:
                            GetServerPublicKeyPayload getServerPublicKeyPayload = getServerKeyMsg.getPayload();
                            if (callback != null)
                                callback.onSuccess(getServerPublicKeyPayload.getServerPublicKeyId(), getServerPublicKeyPayload.getServerPublicKey());
                            break;
                        case ERROR:
                            if (callback != null) {
                                String errorCode = getServerKeyMsg.getErrorCode();
                                switch (errorCode) {
                                    case "1":
                                        callback.onError(new DeniedServiceException());
                                        break;
                                    case "-1":
                                        callback.onError(new IpBlockedException());
                                        break;
                                    case "-999":
                                    default:
                                        callback.onError(new WtfException());
                                        break;
                                }
                            }
                            break;
                    }

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
}
