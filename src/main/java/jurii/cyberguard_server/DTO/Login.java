package jurii.cyberguard_server.DTO;

public class Login {
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void getEmail(String username) {
        this.email = username;
    }
}
