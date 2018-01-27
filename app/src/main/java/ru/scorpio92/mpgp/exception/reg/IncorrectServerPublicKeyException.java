package ru.scorpio92.mpgp.exception.reg;

/**
 * Created by scorpio92 on 1/6/18.
 */

public class IncorrectServerPublicKeyException extends Exception {

    public IncorrectServerPublicKeyException() {
        super("Публичный ключ сервера недействительный");
    }
}
