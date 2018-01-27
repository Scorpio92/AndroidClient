package ru.scorpio92.mpgp.exception.general;

/**
 * Created by scorpio92 on 1/6/18.
 */

public class IpBlockedException extends Exception {

    public IpBlockedException() {
        super("Ваш адрес IP адрес заблокирован из-за многократных запросов");
    }
}
