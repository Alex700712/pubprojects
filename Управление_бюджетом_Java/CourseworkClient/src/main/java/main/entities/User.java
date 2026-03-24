package main.entities;

public class User {

    private int userId;
    private String login;
    private String password;

    private String role;

    private Employee employee;

    public User() {}
    public User(String login, String password)
    {

        this.login = login;
        this.password = password;
    }

    public int getUserId() { return this.userId; }

    public String getLogin() { return this.login; }

    public String getPassword() { return this.password; }

    public void setUserId(int id) { this.userId = id; }

    public void setLogin(String login) { this.login = login; }

    public void setPassword(String password) { this.password = password; }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}

