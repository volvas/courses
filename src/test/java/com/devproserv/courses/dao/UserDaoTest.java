package com.devproserv.courses.dao;

/**
 * Contains unit-tests to check functionality of {@link UserDao} class
 * 
 * @author vovas11
 *
 */
public class UserDaoTest {
//    // dependencies to be mocked
//    @Mock
//    private AppContext appContext;
//    @Mock
//    private DataSource dataSource;
//    @Mock
//    private Connection connection;
//    @Mock
//    private PreparedStatement prepStmt;
//    @Mock
//    private PreparedStatement prepStmt1;
//    @Mock
//    private PreparedStatement prepStmt2;
//    @Mock
//    private ResultSet resultSet;
//    @Mock
//    private ResultSet resultSet1;
//    @Mock
//    private User user;
//    @Mock
//    private Student student;
//
//    private UserDao userDao;
    
    // prepare dependencies
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        userDao = new UserDao(dataSource);
//        when(dataSource.getConnection()).thenReturn(connection);
//        // mocks methods of Connection
//        when(connection.prepareStatement(SELECT_LOGIN_SQL)).thenReturn(prepStmt);
//        when(connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)).thenReturn(prepStmt1);
//        when(connection.prepareStatement(INSERT_STUDENT_SQL)).thenReturn(prepStmt2);
//        // mocks methods of PreparedStatement
//        doNothing().when(prepStmt).setString(anyInt(), anyString());
//        doNothing().when(prepStmt1).setString(anyInt(), anyString());
//        doNothing().when(prepStmt2).setString(anyInt(), anyString());
//        when(prepStmt.executeQuery()).thenReturn(resultSet);
//        when(prepStmt1.executeUpdate()).thenReturn(Integer.valueOf(1));
//        when(prepStmt1.getGeneratedKeys()).thenReturn(resultSet1);
//    }
//
//    @Test
//    public void testLoginExistsOk() throws SQLException {
//        // mocks methods of ResultSet
//        when(resultSet.next()).thenReturn(Boolean.valueOf(false));
//
//        Boolean loginExists = userDao.loginExists("Login");
//        assertFalse("Should be false",loginExists);
//    }
//
//    @Test
//    public void testLoginExistsNotOk() throws SQLException {
//        // mocks methods of ResultSet
//        when(resultSet.next()).thenReturn(Boolean.valueOf(true));
//
//        Boolean loginExists = userDao.loginExists("Login");
//        assertTrue("Should be false",loginExists);
//    }
//
//    @Test
//    public void testCreateUserOk() throws SQLException {
//        // mocks methods of ResultSet
//        when(resultSet1.next()).thenReturn(Boolean.valueOf(false));
//
//        // TODO continue with student.setId(generatedKey.getInt(1));
//        Boolean loginExists = userDao.loginExists("Login");
//        assertFalse("Should be false",loginExists);
//    }
}
