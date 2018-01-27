package ru.scorpio92.mpgp.exception.auth;

/**
 * Created by scorpio92 on 1/6/18.
 */

public class BadTokenException extends Exception {

    public BadTokenException() {
        super("Невалидный токен");
    }
}
