package ru.scorpio92.mpgp.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.data.entity.message.api.APIServersList;
import ru.scorpio92.mpgp.data.entity.message.api.APIServersListItem;
import ru.scorpio92.mpgp.presentation.presenter.ConnectionPresenter;
import ru.scorpio92.mpgp.presentation.presenter.base.IConnectionPresenter;
import ru.scorpio92.mpgp.presentation.view.adapter.APIServersListAdapter;
import ru.scorpio92.mpgp.presentation.view.base.AbstractActivity;
import ru.scorpio92.mpgp.presentation.view.base.IConnectionActivity;
import ru.scorpio92.mpgp.util.Logger;

/**
 * Created by scorpio92 on 2/1/18.
 */

public class ConnectionActivity extends AbstractActivity<IConnectionPresenter> implements IConnectionActivity {

    private ProgressBar progressBar;
    private LinearLayoutCompat mainContainer;
    private ListViewCompat serverList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        initUI();

        registerPresenter(new ConnectionPresenter(this));
        getPresenter().getServerList();
    }

    @Override
    public void showProgress(boolean show) {
        mainContainer.setVisibility(View.GONE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void initUI() {
        progressBar = findViewById(R.id.progress);
        mainContainer = findViewById(R.id.mainContainer);
        serverList = findViewById(R.id.list);
        serverList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String wsServerAddress = ((APIServersListItem)serverList.getAdapter().getItem(position)).getAddress();
                Logger.log(wsServerAddress);
            }
        });
    }

    @Override
    public void renderServerList(APIServersList apiServersList) {
        mainContainer.setVisibility(View.VISIBLE);
        APIServersListAdapter adapter = new APIServersListAdapter(this, apiServersList);
        serverList.setAdapter(adapter);
    }
}
