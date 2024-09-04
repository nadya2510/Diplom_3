package api;

public class RegisterIn {
    private String email;
    private String password;
    private String name;
    private String accessToken;

    public RegisterIn(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public RegisterIn(String email, String password, String name, String accessToken) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.accessToken = accessToken;
    }

    public RegisterIn() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
