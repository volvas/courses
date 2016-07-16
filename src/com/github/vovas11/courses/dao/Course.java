package com.github.vovas11.courses.dao;

/**
 * Represents the entity of the Course. Maps the table 'courses' in the database. 
 * Part of DAO design pattern.
 * 
 * @author vovas11
 * @see CourseDao
 */
public class Course {
    private int id;
    private String name;
    private String description;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
