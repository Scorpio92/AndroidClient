package ru.scorpio92.mpgp.exception.general;

public class DeniedServiceException extends Exception {

    public DeniedServiceException() {
        super("На данный момент сервис недоступен");
    }
}
