package ru.scorpio92.mpgp.exception.reg;

/**
 * Created by scorpio92 on 1/6/18.
 */

public class NotValidNicknameException extends Exception {

    public NotValidNicknameException() {
        super("Указан недопустимый никнейм");
    }
}
