package com.devproserv.courses.dao;

/**
 * Represents a common entity of user. Maps the table 'users' in the database.
 * 
 * @author vovas11
 * @see UserDao
 */
public class User {

    // fields representing columns in the table 'users'
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;

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
}
