package ru.scorpio92.mpgp.presentation.view.base;

public interface IFragmentListener {

    public enum ResultCode {
        NEED_REGISTRATION,
        NEED_AUTH,
        SUCCESS_WS_AUTH
    }

    void onFragmentResut(ResultCode resultCode);
}
