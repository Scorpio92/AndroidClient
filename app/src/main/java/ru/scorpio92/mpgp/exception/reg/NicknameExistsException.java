package ru.scorpio92.mpgp.exception.reg;

/**
 * Created by scorpio92 on 1/6/18.
 */

public class NicknameExistsException extends Exception {

    public NicknameExistsException() {
        super("Никнейм уже занят");
    }
}
