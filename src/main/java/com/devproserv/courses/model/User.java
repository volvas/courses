package com.devproserv.courses.model;

import javax.servlet.http.HttpServletRequest;

public abstract class User {

    // fields representing columns in the table 'users'
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String path;


    public abstract boolean exists();

    public abstract void loadFields();

    public abstract void prepareJspData(HttpServletRequest request);

    public User convertToTrue() {
        return this; // TODO
    }



    // getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
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

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

}
