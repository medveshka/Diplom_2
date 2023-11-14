package org.example.models.user;

public class UpdateUser {
    private String email;
    private String password;
    private String name;

    public UpdateUser(String email,String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}