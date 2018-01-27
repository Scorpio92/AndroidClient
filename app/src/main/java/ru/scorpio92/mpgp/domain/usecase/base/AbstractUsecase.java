package ru.scorpio92.mpgp.domain.usecase.base;

import ru.scorpio92.mpgp.domain.threading.base.IExecutor;
import ru.scorpio92.mpgp.domain.threading.base.IMainThread;

/**
 * Created by scorpio92 on 12/19/17.
 */

public abstract class AbstractUsecase implements IAbstractUsecase {

    private IExecutor executor;
    private IMainThread mainThread;

    public AbstractUsecase(IExecutor executor, IMainThread mainThread) {
        this.executor = executor;
        this.mainThread = mainThread;
    }

    public abstract void run();

    protected abstract void onInterrupt();

    @Override
    public void execute() {
        executor.execute(this);
    }

    @Override
    public void cancel() {
        onInterrupt();
    }

    protected void runOnUI(Runnable runnable) {
        mainThread.post(runnable);
    }
}
