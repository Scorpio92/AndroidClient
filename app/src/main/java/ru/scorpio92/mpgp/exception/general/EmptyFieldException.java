package ru.scorpio92.mpgp.exception.general;

/**
 * Created by scorpio92 on 1/6/18.
 */

public class EmptyFieldException extends Exception {

    public EmptyFieldException(String field) {
        super("Необходимо обязательно заполнить поле: " + field);
    }
}
