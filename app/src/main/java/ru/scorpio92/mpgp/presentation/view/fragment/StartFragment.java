package ru.scorpio92.mpgp.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.scorpio92.mpgp.BuildConfig;
import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.presentation.presenter.StartPresenter;
import ru.scorpio92.mpgp.presentation.presenter.base.IStartPresenter;
import ru.scorpio92.mpgp.presentation.view.base.IFragmentListener;
import ru.scorpio92.mpgp.presentation.view.base.IStartFragment;
import ru.scorpio92.mpgp.util.ViewUtils;
import ru.scorpio92.sdk.architecture.presentation.view.BaseFragment;

public class StartFragment extends BaseFragment<IStartPresenter> implements IStartFragment {

    private IFragmentListener listener;
    private CoordinatorLayout rootLayout;
    private LinearLayoutCompat actionContainer;
    private AlertDialog authDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        initUI(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (IFragmentListener) context;
    }

    @Override
    public void showProgress() {
        super.showProgress();
        actionContainer.setVisibility(View.GONE);
        if (getContext() != null)
            authDialog = ViewUtils.showProgressDialog(getContext(), getString(R.string.ws_connection_process));
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        ViewUtils.safetyDismissDialog(authDialog);
    }

    @Override
    public void onError(@NonNull String error) {
        super.onError(error);
        ViewUtils.showShackBar(rootLayout, error);
    }

    @Override
    public void onNeedRegistration() {
        actionContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNeedAuth() {
        if (listener != null)
            listener.onFragmentResut(IFragmentListener.ResultCode.NEED_AUTH);
    }

    @Override
    public void onSuccessAuth() {
        if (listener != null)
            listener.onFragmentResut(IFragmentListener.ResultCode.CONNECTED_TO_WS);
    }

    @NonNull
    @Override
    protected IStartPresenter providePresenter() {
        return new StartPresenter(this);
    }

    private void initUI(View view) {
        rootLayout = view.findViewById(R.id.root);

        actionContainer = view.findViewById(R.id.actionContainer);
        actionContainer.setVisibility(View.GONE);

        view.findViewById(R.id.regBtn).setOnClickListener(v -> {
            if (listener != null)
                listener.onFragmentResut(IFragmentListener.ResultCode.NEED_REGISTRATION);
        });

        view.findViewById(R.id.authBtn).setOnClickListener(v -> {
            if (listener != null)
                listener.onFragmentResut(IFragmentListener.ResultCode.TRY_AUTH_IF_USER_HAVE_ACCOUNT);
        });

        view.findViewById(R.id.link).setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(BuildConfig.PROJECT_URL));
            startActivity(i);
        });
    }
}
