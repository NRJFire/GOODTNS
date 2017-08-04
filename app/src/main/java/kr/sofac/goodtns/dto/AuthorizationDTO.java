package kr.sofac.goodtns.dto;

/**
 * Created by Maxim on 04.08.2017.
 */

public class AuthorizationDTO {

    private String login;
    private String password;
    private String googleKey;

    public AuthorizationDTO(String login, String password, String googleKey) {
        this.login = login;
        this.password = password;
        this.googleKey = googleKey;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoogleKey() {
        return googleKey;
    }

    public void setGoogleKey(String googleKey) {
        this.googleKey = googleKey;
    }

    @Override
    public String toString() {
        return "AuthorizationDTO{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", googleKey='" + googleKey + '\'' +
                '}';
    }
}
