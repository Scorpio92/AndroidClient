package ru.scorpio92.mpgp.data.repository.network;

import java.security.PrivateKey;

import ru.scorpio92.mpgp.data.entity.message.auth.RegisterMessage;
import ru.scorpio92.mpgp.data.entity.message.auth.RegisterPayload;
import ru.scorpio92.mpgp.data.repository.network.core.INetworkRepository;
import ru.scorpio92.mpgp.data.repository.network.core.NetworkCallback;
import ru.scorpio92.mpgp.data.repository.network.core.NetworkRepository;
import ru.scorpio92.mpgp.data.repository.network.core.RequestSpecification;
import ru.scorpio92.mpgp.exception.general.DeniedServiceException;
import ru.scorpio92.mpgp.exception.general.IpBlockedException;
import ru.scorpio92.mpgp.exception.general.WtfException;
import ru.scorpio92.mpgp.exception.reg.IncorrectServerPublicKeyException;
import ru.scorpio92.mpgp.exception.reg.NicknameExistsException;
import ru.scorpio92.mpgp.exception.reg.NotValidNicknameException;
import ru.scorpio92.mpgp.util.JsonWorker;

/**
 * Created by scorpio92 on 1/13/18.
 */

public class RegisterNetRepo extends NetworkRepository implements INetworkRepository {

    public interface Callback {
        void onRegistered(String authToken);

        void onError(Exception e);
    }

    private PrivateKey privateKey;
    private Callback callback;

    public RegisterNetRepo(PrivateKey privateKey, Callback callback) {
        this.privateKey = privateKey;
        this.callback = callback;
    }

    @Override
    public void execute(RequestSpecification requestSpecification) {
        makeRequest(requestSpecification, new NetworkCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    RegisterMessage registerMessage = JsonWorker.getDeserializeJson(response, RegisterMessage.class);

                    switch (registerMessage.getStatus()) {
                        case SUCCESS:
                            try {
                                RegisterPayload registerPayload = registerMessage.getPayload(privateKey);
                                if (callback != null)
                                    callback.onRegistered(registerPayload.getAuthToken());
                            } catch (Exception e) {
                                if (callback != null)
                                    callback.onError(e);
                            }
                            break;
                        case ERROR:
                            if (callback != null) {
                                String errorCode = registerMessage.getErrorCode();
                                switch (errorCode) {
                                    case "1":
                                        callback.onError(new DeniedServiceException());
                                        break;
                                    case "2":
                                        callback.onError(new NotValidNicknameException());
                                        break;
                                    case "3":
                                        callback.onError(new NicknameExistsException());
                                        break;
                                    case "-1":
                                        callback.onError(new IpBlockedException());
                                        break;
                                    case "-2":
                                        callback.onError(new IncorrectServerPublicKeyException());
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
