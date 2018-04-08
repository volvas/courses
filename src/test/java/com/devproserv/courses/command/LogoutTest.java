package com.devproserv.courses.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import static org.mockito.Mockito.doNothing;

import static com.devproserv.courses.config.MainConfig.HOME_PAGE;

/**
 * Contains unit-tests to check functionality of {@link Logout} class
 * 
 */
public class LogoutTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private Logout logout;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        logout = new Logout();

        when(request.getSession()).thenReturn(session);

        doNothing().when(session).invalidate();
    }
    
    @Test
    public void testExecuteCommandOk() {
        String path = logout.path(request);
        assertEquals("Should be equal to " + HOME_PAGE, HOME_PAGE, path);
    }
    
}
