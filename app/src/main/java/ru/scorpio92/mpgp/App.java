package ru.scorpio92.mpgp;

import android.app.Application;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * Created by scorpio92 on 1/6/18.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Security.addProvider(new BouncyCastleProvider());
    }
}
