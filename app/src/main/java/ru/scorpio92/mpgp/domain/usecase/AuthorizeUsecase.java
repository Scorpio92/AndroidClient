package ru.scorpio92.mpgp.domain.usecase;

import java.security.KeyPair;

import ru.scorpio92.mpgp.data.entity.SessionKey;
import ru.scorpio92.mpgp.data.entity.message.auth.AuthorizeMessage;
import ru.scorpio92.mpgp.data.entity.message.auth.AuthorizePayload;
import ru.scorpio92.mpgp.data.entity.message.auth.GetServerPublicKeyMessage;
import ru.scorpio92.mpgp.data.repository.network.AuthorizeNetRepo;
import ru.scorpio92.mpgp.data.repository.network.GetServerKeyNetRepo;
import ru.scorpio92.mpgp.data.repository.network.core.INetworkRepository;
import ru.scorpio92.mpgp.data.repository.network.specifications.AuthSpecification;
import ru.scorpio92.mpgp.domain.threading.base.IExecutor;
import ru.scorpio92.mpgp.domain.threading.base.IMainThread;
import ru.scorpio92.mpgp.domain.usecase.base.AbstractUsecase;
import ru.scorpio92.mpgp.domain.usecase.base.IUsecaseBaseCallback;
import ru.scorpio92.mpgp.util.JsonWorker;
import ru.scorpio92.mpgp.util.LocalStorage;
import ru.scorpio92.mpgp.util.crypto.RSA;
import ru.scorpio92.mpgp.util.crypto.SHA;

public class AuthorizeUsecase extends AbstractUsecase {

    private Callback callback;
    private LocalStorage localStorage;
    private INetworkRepository repository;

    public AuthorizeUsecase(IExecutor executor, IMainThread mainThread, LocalStorage localStorage, Callback callback) {
        super(executor, mainThread);
        this.callback = callback;
        this.localStorage = localStorage;
    }

    @Override
    public void run() {
        getServerKey();
    }

    private void getServerKey() {
        repository = new GetServerKeyNetRepo(new GetServerKeyNetRepo.Callback() {
            @Override
            public void onSuccess(String serverPublicKey) {
                authorize(serverPublicKey);
            }

            @Override
            public void onError(Exception e) {
                if(callback != null)
                    runOnUI(() -> callback.onError(e));
            }
        });
        repository.execute(new AuthSpecification(new GetServerPublicKeyMessage()));
    }

    private void authorize(String serverPublicKey) {
        try {
            KeyPair keyPair = RSA.buildKeyPair(RSA.KEY_2048_BIT);
            AuthorizePayload authorizePayload = new AuthorizePayload(localStorage.getDataFromFile(LocalStorage.AUTH_TOKEN_STORAGE));
            AuthorizeMessage authorizeMessage = new AuthorizeMessage(
                    SHA.getSHA1ofString(serverPublicKey),
                    serverPublicKey,
                    RSA.covertKeyToString(keyPair.getPublic()),
                    authorizePayload
            );

            repository = new AuthorizeNetRepo(keyPair.getPrivate(), new AuthorizeNetRepo.Callback() {
                @Override
                public void onAuthorized(String sessionKey, String iv) {
                    try {
                        String sessionKeyId = SHA.getSHA1ofString(sessionKey);
                        SessionKey sessionKeyObj = new SessionKey(sessionKeyId, sessionKey, iv);
                        localStorage.setDataInFile(LocalStorage.SESSION_KEY_STORAGE, JsonWorker.getSerializeJson(sessionKeyObj));
                        if(callback != null) {
                            runOnUI(() -> callback.onAuthorized());
                        }
                    } catch (Exception e) {
                        if(callback != null)
                            runOnUI(() -> callback.onError(e));
                    }

                }

                @Override
                public void onError(Exception e) {
                    if(callback != null)
                        runOnUI(() -> callback.onError(e));
                }
            });
            repository.execute(new AuthSpecification(authorizeMessage));

        } catch (Exception e) {
            if(callback != null)
                runOnUI(() -> callback.onError(e));
        }
    }

    @Override
    protected void onInterrupt() {
        callback = null;
        if(repository != null)
            repository.cancel();
    }

    public interface Callback extends IUsecaseBaseCallback {
        void onAuthorized();
    }

}