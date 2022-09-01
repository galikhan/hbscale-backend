package kz.hbscale.main.jwt;

public class UsernameAndPasswordDto {

    private String username;
    private String password;

    public UsernameAndPasswordDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
