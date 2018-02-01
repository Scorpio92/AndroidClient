package ru.scorpio92.mpgp.presentation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.ProgressBar;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.presentation.presenter.ConnectionPresenter;
import ru.scorpio92.mpgp.presentation.presenter.base.IConnectionPresenter;
import ru.scorpio92.mpgp.presentation.view.base.AbstractActivity;
import ru.scorpio92.mpgp.presentation.view.base.IConnectionActivity;

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
    }

    @Override
    public void renderServerList() {

    }
}
