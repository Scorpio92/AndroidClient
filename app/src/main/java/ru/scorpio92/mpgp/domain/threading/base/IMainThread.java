package ru.scorpio92.mpgp.domain.threading.base;


public interface IMainThread {

    /**
     * Make runnable operation run in the main thread.
     *
     * @param runnable The runnable to run.
     */
    void post(final Runnable runnable);
}
