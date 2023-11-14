package org.example.models.user;

public class DeleteUser {
    private String email;
    private String password;


    public DeleteUser(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
