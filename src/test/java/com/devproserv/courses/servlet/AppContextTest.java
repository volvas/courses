package com.devproserv.courses.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.MainConfig.COMMAND_LOGIN;
import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;
import static com.devproserv.courses.config.MainConfig.NOT_FOUND_PAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


/**
 * Contains unit-tests to check functionality of {@link AppContext} class
 *
 */
public class AppContextTest {

    @Mock
    private HttpServletRequest request;

    private AppContext appContext;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        appContext = new AppContext();
    }


    @Test
    public void testGetPathOk() {
        when(request.getParameter("command")).thenReturn(COMMAND_LOGIN);
        String path = appContext.getPath(request);
        assertEquals(LOGIN_PAGE, path);
    }

    @Test
    public void testGetPathWrongCommand() {
        when(request.getParameter("command")).thenReturn("invalid command");
        String path = appContext.getPath(request);
        assertEquals( NOT_FOUND_PAGE, path);
    }
}
