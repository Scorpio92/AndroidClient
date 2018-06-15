package ru.scorpio92.mpgp;

import android.app.Application;

import ru.scorpio92.mpgp.util.LocalStorage;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocalStorage.initLocalStorage(getApplicationContext());
    }
}
