package ru.scorpio92.mpgp.domain.usecase.base;

import android.os.Handler;
import android.os.Looper;

import ru.scorpio92.mpgp.domain.threading.base.IExecutor;

/**
 * Created by scorpio92 on 12/19/17.
 */

public abstract class AbstractUsecase implements IAbstractUsecase {

    private IExecutor executor;
    private Handler handler = new Handler(Looper.getMainLooper());

    protected AbstractUsecase() {
        this.executor = provideExecutor();
    }

    public abstract void run();

    protected abstract void onInterrupt();

    protected abstract IExecutor provideExecutor();

    @Override
    public void execute() {
        if(executor == null)
            throw new IllegalArgumentException("need provide executor");

        executor.execute(this);
    }

    @Override
    public void cancel() {
        onInterrupt();
    }

    protected void runOnUI(Runnable runnable) {
        handler.post(runnable);
    }
}
