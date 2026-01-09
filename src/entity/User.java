package entity;

/**
 * Represents a user account
 */
public class User {
    private Long id;
    private String login;
    private String password;
    private String role;  // USER by default or ADMIN

    public User() {
    }

    public User(String login, String password) {
        this();
        this.login = login;
        this.password = password;
    }

    //------------------------------------------
    // Getter and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    // -----------------------------

    public boolean isAdmin() {
        return "ADMIN".equals(this.role);
    }

    @Override
    public String toString() {
        return "User Id: " + id + "\n" + "Login: " + login + "\n" + "Role: " + role;
    }
}
