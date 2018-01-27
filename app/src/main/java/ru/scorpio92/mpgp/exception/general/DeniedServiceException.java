package ru.scorpio92.mpgp.exception.general;

/**
 * Created by scorpio92 on 1/6/18.
 */

public class DeniedServiceException extends Exception {

    public DeniedServiceException() {
        super("На данный момент сервис недоступен");
    }
}
