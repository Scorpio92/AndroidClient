package ru.scorpio92.mpgp.data.model;

public class RegInfo {

    private String login;
    private String password;
    private String nickname;

    public RegInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public RegInfo(String login, String password, String nickname) {
        this.login = login;
        this.password = password;
        this.nickname = nickname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
