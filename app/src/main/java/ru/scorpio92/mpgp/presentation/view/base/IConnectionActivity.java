package ru.scorpio92.mpgp.presentation.view.base;

import ru.scorpio92.mpgp.data.entity.message.api.APIServersList;

/**
 * Created by scorpio92 on 2/1/18.
 */

public interface IConnectionActivity extends IBaseActivity {
    void renderServerList(APIServersList apiServersList);
}
