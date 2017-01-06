package com.devproserv.courses.dao;

/**
 * Represents the entity of the Student. Maps the table 'students' in the database.
 * 
 * @author vovas11
 * @see UserDao
 */
public class Student extends User {
    
    // additional fields representing columns in the table 'students'
    private String faculty;

    public String getFaculty() {
        return faculty;
    }
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
