package ru.scorpio92.mpgp.data.repository.network.core;

/**
 * Created by scorpio92 on 1/3/18.
 */

public class BadResponseCodeException extends Exception {

    private int responseCode;

    public BadResponseCodeException(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
