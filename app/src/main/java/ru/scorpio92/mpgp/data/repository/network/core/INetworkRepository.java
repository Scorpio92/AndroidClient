package ru.scorpio92.mpgp.data.repository.network.core;

/**
 * Created by scorpio92 on 1/3/18.
 */

public interface INetworkRepository {

    void execute(RequestSpecification requestSpecification);

    void cancel();
}
