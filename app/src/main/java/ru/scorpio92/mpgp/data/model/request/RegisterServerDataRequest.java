package ru.scorpio92.mpgp.data.model.request;

public class RegisterServerDataRequest {

    public static final String LOGIN_REGEXP = "^[A-Za-z0-9]{3,20}$";
    public static final String PASSWORD_REGEXP = "^(.){8,64}$";
    public static final String NICKNAME_REGEXP = "^[^\\s].{1,18}[^\\s]$";

    private String login;
    private String password;
    private String nickname;

    public RegisterServerDataRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public RegisterServerDataRequest(String login, String password, String nickname) {
        this.login = login;
        this.password = password;
        this.nickname = nickname;
    }
}
