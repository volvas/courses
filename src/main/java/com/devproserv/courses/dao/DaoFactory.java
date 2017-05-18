package com.devproserv.courses.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * {@code DaoFactory} is a factory (design pattern) that creates instances
 * of the data access objects (DAO).
 * 
 * @author vovas11
 * @see CourseDao
 * @see UserDao
 */
public class DaoFactory {
    
    /* stores the link to the single instance (Singleton) */
    static DaoFactory factory = new DaoFactory();
    
    /* stores the link to the common data source */
    DataSource datasrc;
    
    /* forbids the instantiation of the class outside and receives the link to datasource
     * by Initial Context */
    private DaoFactory() {
	try {
	    InitialContext initContext = new InitialContext();
	    datasrc = (DataSource) initContext.lookup("java:comp/env/jdbc/coursedb");
	} catch (NamingException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Returns to the link to single instance of the DaoFactory class
     * @return link to single instance of the class
     */
    public static DaoFactory getInstance() {
	return factory;
    }
    
    /**
     * Creates the instance of the UserDao class and returns the link
     * 
     * @return link to the instance of UserDao
     */
    public UserDao getUserDao() {
	return new UserDao(datasrc);
    }
    
    /**
     * Creates the instance of the CourseDao class and returns the link
     * 
     * @return link to the instance of CourseDao
     */
    public CourseDao getCourseDao() {
	return new CourseDao(datasrc);
    }
}
