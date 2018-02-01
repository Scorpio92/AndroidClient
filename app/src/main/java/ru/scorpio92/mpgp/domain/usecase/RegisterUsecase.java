package ru.scorpio92.mpgp.domain.usecase;

import java.security.KeyPair;

import ru.scorpio92.mpgp.data.entity.message.auth.GetServerPublicKeyMessage;
import ru.scorpio92.mpgp.data.entity.message.auth.RegisterMessage;
import ru.scorpio92.mpgp.data.entity.message.auth.RegisterPayload;
import ru.scorpio92.mpgp.data.repository.network.GetServerKeyNetRepo;
import ru.scorpio92.mpgp.data.repository.network.RegisterNetRepo;
import ru.scorpio92.mpgp.data.repository.network.core.INetworkRepository;
import ru.scorpio92.mpgp.data.repository.network.specifications.AuthSpecification;
import ru.scorpio92.mpgp.domain.threading.base.IExecutor;
import ru.scorpio92.mpgp.domain.threading.base.IMainThread;
import ru.scorpio92.mpgp.domain.usecase.base.AbstractUsecase;
import ru.scorpio92.mpgp.domain.usecase.base.IUsecaseBaseCallback;
import ru.scorpio92.mpgp.util.LocalStorage;
import ru.scorpio92.mpgp.util.crypto.RSA;
import ru.scorpio92.mpgp.util.crypto.SHA;

public class RegisterUsecase extends AbstractUsecase {

    private String login;
    private Callback callback;
    private LocalStorage localStorage;
    private INetworkRepository repository;

    public RegisterUsecase(IExecutor executor, IMainThread mainThread, LocalStorage localStorage, String login, Callback callback) {
        super(executor, mainThread);
        this.login = login;
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
                register(serverPublicKey);
            }

            @Override
            public void onError(Exception e) {
                if(callback != null)
                    runOnUI(() -> callback.onError(e));
            }
        });
        repository.execute(new AuthSpecification(new GetServerPublicKeyMessage()));
    }

    private void register(String serverPublicKey) {
        try {
            RegisterPayload registerPayload = new RegisterPayload(login, "scorpio92@mail.ru");
            KeyPair keyPair = RSA.buildKeyPair(RSA.KEY_2048_BIT);
            RegisterMessage registerMessage = new RegisterMessage(
                    SHA.getSHA1ofString(serverPublicKey),
                    serverPublicKey,
                    RSA.covertKeyToString(keyPair.getPublic()),
                    registerPayload
            );

            repository = new RegisterNetRepo(keyPair.getPrivate(), new RegisterNetRepo.Callback() {
                @Override
                public void onRegistered(String authToken) {
                    try {
                        if(callback != null) {
                            localStorage.setDataInFile(LocalStorage.AUTH_TOKEN_STORAGE, authToken);
                            runOnUI(() -> callback.onRegistered());
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
            repository.execute(new AuthSpecification(registerMessage));

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
        void onRegistered();
    }

}