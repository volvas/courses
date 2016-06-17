/**
 * 
 * 
 * 
 * 
 */
package com.github.vovas11.courses.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DaoFactory {
    static DaoFactory factory = new DaoFactory();
    DataSource datasrc;
    
    private DaoFactory() {
	try {
	    InitialContext initContext = new InitialContext();
	    datasrc = (DataSource) initContext.lookup("java:comp/env/jdbc/dbcoursetest2");
	} catch (NamingException e) {
	    e.printStackTrace();
	}
    }
    
    public static DaoFactory getInstance() {
	return factory;
    }
    
    public UserDao getUserDao() {
	UserDao users = new UserDao(datasrc);
	return users;
    }
    public CourseDao getCourseDao() {
	return new CourseDao(datasrc);
    }
}
