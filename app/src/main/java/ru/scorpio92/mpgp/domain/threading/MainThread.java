package ru.scorpio92.mpgp.domain.threading;


import android.os.Handler;
import android.os.Looper;

import ru.scorpio92.mpgp.domain.threading.base.IMainThread;


public class MainThread implements IMainThread {

    private volatile static IMainThread mainThread;

    private Handler mHandler;

    private MainThread() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static IMainThread getInstance() {
        if(mainThread == null) {
            synchronized (MainThread.class) {
                if (mainThread == null) {
                    mainThread = new MainThread();
                }
            }
        }

        return mainThread;
    }
}
