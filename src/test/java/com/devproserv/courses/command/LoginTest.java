package com.devproserv.courses.command;

import com.devproserv.courses.servlet.AppContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;
import static org.junit.Assert.assertEquals;

/**
 * Contains unit-tests to check functionality of {@link Login} class
 * 
 */
public class LoginTest {
    @Mock
    private AppContext appContext;
    @Mock
    private HttpServletRequest request;

    private Login login;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        login = new Login(appContext);
    }


    @Test
    public void testPathOk() {
        String path = login.path(request);
        assertEquals(LOGIN_PAGE, path);
    }
}
