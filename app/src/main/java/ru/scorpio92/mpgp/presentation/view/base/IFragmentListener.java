package ru.scorpio92.mpgp.presentation.view.base;

public interface IFragmentListener {

    public enum ResultCode {
        NEED_REGISTRATION, //необходима регистрация
        SUCCESS_REGISTRATION, //успешная регистрация
        TRY_AUTH_IF_USER_HAVE_ACCOUNT, //уже есть акаунт
        NEED_AUTH, //необходима авторизация
        SUCCESS_AUTH, //успешная авторизация
        CONNECTED_TO_WS //успешная авторизация на WS
    }

    void onFragmentResut(ResultCode resultCode);
}
