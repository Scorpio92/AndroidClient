package ru.scorpio92.mpgp.exception.general;

/**
 * Created by scorpio92 on 1/6/18.
 */

public class WtfException extends Exception {

    public WtfException() {
        super("Произошла неизвестная ошибка");
    }
}
