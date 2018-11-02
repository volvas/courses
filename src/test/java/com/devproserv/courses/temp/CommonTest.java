package com.devproserv.courses.temp;


public class CommonTest {
//    @Mock
//    private AppContext appContext;
//    @Mock
//    private HttpServletRequest request;
//
//
//    @Mock
//    private HttpSession session;
//    @Mock
//    private UserDao userDao;
//    @Mock
//    private CourseDao courseDao;
//    @Mock
//    private User user;
//
//    private Login login;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        login = new Login(appContext);
//
//        when(request.getParameter("login")).thenReturn("Login");
//        when(request.getParameter("password")).thenReturn("Password");
//        when(request.getSession()).thenReturn(session);
//        doNothing().when(request).setAttribute(anyString(), anyString());
//
//        doNothing().when(session).setAttribute(anyString(), any());
//
//        when(appContext.getUserDao()).thenReturn(userDao);
//        when(appContext.getCourseDao()).thenReturn(courseDao);
//
//        //when(userDao.getUser("Login", "Password")).thenReturn(user);
//
//        when(courseDao.getSubscribedCourses(user)).thenReturn(Collections.emptyList());
//        when(courseDao.getAvailableCourses(user)).thenReturn(Collections.emptyList());
//    }




    //    @Test
//    public void testExecuteCommandOkStudent() {
//        when(userDao.userExists("Login", "Password")).thenReturn(true);
//
//        when(user.getRole()).thenReturn(Role.STUD);
//
//        String page = login.path(request);
//        assertEquals("Should be equal to " + STUDENT_PAGE, STUDENT_PAGE, page);
//    }

//    @Test
//    public void testExecuteCommandOkLecturer() {
//        when(userDao.userExists("Login", "Password")).thenReturn(true);
//
//        when(user.getRole()).thenReturn(Role.LECT);
//
//        String page = login.path(request);
//        assertEquals("Should be equal to " + LECTURER_PAGE, LECTURER_PAGE, page);
//    }
//
//    @Test
//    public void testExecuteCommandOkAdmin() {
//        when(userDao.userExists("Login", "Password")).thenReturn(true);
//
//        when(user.getRole()).thenReturn(Role.ADMIN);
//
//        String page = login.path(request);
//        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
//    }

    //    @Test
//    public void testExecuteCommandUserDoesNotExist() {
//        when(request.getParameter("login")).thenReturn("Login");
//        when(request.getParameter("password")).thenReturn("Password");
//
//        when(userDao.userExists("Login", "Password")).thenReturn(false);
//
//        String page = login.path(request);
//        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
//    }












    // Signup

//    private SignUp signup;
//
//    // prepare dependencies
//    @Before
//    public void setUp1() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        signup = new SignUp(appContext);
//        // mocks methods of HttpServletRequest
//        when(request.getParameter("login")).thenReturn("Login");
//        when(request.getParameter("password")).thenReturn("Password");
//        when(request.getParameter("firstname")).thenReturn("FirstName");
//        when(request.getParameter("lastname")).thenReturn("LastName");
//        when(request.getParameter("faculty")).thenReturn("Faculty");
//        doNothing().when(request).setAttribute(eq("message"), anyString());
//        // mocks methods of AppContext
//        when(appContext.getUserDao()).thenReturn(userDao);
//        // mocks methods of UserDao
//        //when(userDao.loginExists("Login")).thenReturn(Boolean.valueOf(false));
//        when(userDao.createUser("Login", "Password", "FirstName", "LastName", "Faculty"))
//                .thenReturn(Boolean.valueOf(true));
//    }
//
//    @Test
//    public void testExecuteCommandOk() {
//        when(userDao.loginExists("Login")).thenReturn(Boolean.valueOf(false));
//        when(userDao.createUser("Login", "Password", "FirstName", "LastName", "Faculty"))
//                .thenReturn(Boolean.valueOf(true));
//
//        String page = signup.path(request);
//        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
//    }
//
//    @Test
//    public void testExecuteCommandUserExists() {
//        when(userDao.loginExists("Login")).thenReturn(Boolean.valueOf(true));
//        when(userDao.createUser("Login", "Password", "FirstName", "LastName", "Faculty"))
//                .thenReturn(Boolean.valueOf(true));
//
//        String page = signup.path(request);
//        assertEquals("Should be equal to " + SIGNUP_PAGE, SIGNUP_PAGE, page);
//    }
//
//    @Test
//    public void testExecuteCommandDBTroubles() {
//        when(userDao.loginExists("Login")).thenReturn(Boolean.valueOf(false));
//        when(userDao.createUser("Login", "Password", "FirstName", "LastName", "Faculty"))
//                .thenReturn(Boolean.valueOf(false));
//
//        String page = signup.path(request);
//        assertEquals("Should be equal to " + SIGNUP_PAGE, SIGNUP_PAGE, page);
//    }
//
//    @Test
//    public void testExecuteCommandWrongParameters() {
//        when(request.getParameter("login")).thenReturn("");
//        when(userDao.loginExists("Login")).thenReturn(Boolean.valueOf(false));
//        when(userDao.createUser("", "Password", "FirstName", "LastName", "Faculty"))
//                .thenReturn(Boolean.valueOf(true));
//
//        String page = signup.path(request);
//        assertEquals("Should be equal to " + SIGNUP_PAGE, SIGNUP_PAGE, page);
//    }
//
//




}
