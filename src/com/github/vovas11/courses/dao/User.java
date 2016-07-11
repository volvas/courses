package com.github.vovas11.courses.dao;

/**
 * Creating User object corresponding to the table in DB
 * 
 */
public class User {
    
    /*     */
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String department;
    
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
    public String getDepartment() {
	return department;
    }
    public void setDepartment(String department) {
	this.department = department;
    }
}
