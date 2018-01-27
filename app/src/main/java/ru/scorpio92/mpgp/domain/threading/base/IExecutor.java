package ru.scorpio92.mpgp.domain.threading.base;


import ru.scorpio92.mpgp.domain.usecase.base.AbstractUsecase;

public interface IExecutor {

    void execute(final AbstractUsecase usecase);

    void shutdownExecutor();
}
