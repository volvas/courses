# Courses for students
WEB Project for training purpose using web server with Servlets, JSP, Connection pool, Java code, design patterns etc.

===========
**Description**  
System of optional courses. There is a list of courses, each course is assigned to a lecturer. Student subscribes to one or more courses. Registration data is stored. After course completion lecturer evaluates it with a mark and data stores in the archive.  
  
**General requirements to the project**  

Build WEB system having the following functions:  
1. Build classes describing entities of the data domain  
2. Names of classes and methods should reflect their functionality and classes should have correct hierarhy  
3. Code should satisfy Java Code Convention  
4. Information about data domain should be kept in a database. API JDBC with connection pool (standard or custom) should be used for access into the database. MySQL or Derby are recommended as a database  
5. The application should support cyrillic (be multilangual), including information in DB  
6. Architecture of application should correspond to template Model-View-Controller  
7. During logic implementation GoF templates like Factory Method, Command, Builder, Strategy, State, Observer etc are to be used  
8. Implement functionality that is mentioned in the task using servlets and JSP  
9. Use JSTL library and custom tags on the JSPs  
10. During business logic development sessions and filters are to be used  
11. Implement event logging i.e. information about arising exceptions and events in the system is to be handled with Log4j  
12. The application should contain comments.  

**Restrictions**
