package ru.scorpio92.mpgp.data.repository.network.specifications;

import ru.scorpio92.mpgp.Constants;
import ru.scorpio92.mpgp.data.repository.network.core.RequestSpecification;

/**
 * Created by scorpio92 on 1/13/18.
 */

public class GetServersListSpecification extends RequestSpecification {

    public GetServersListSpecification() {
        super(Constants.API_SERVER);
    }
}
