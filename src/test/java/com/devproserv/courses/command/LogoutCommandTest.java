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
 * Contains unit-tests to check functionality of {@link LogoutCommand} class
 * 
 * @author vovas11
 *
 */
public class LogoutCommandTest {
    
    // dependencies to be mocked
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    
    
    private LogoutCommand logoutCommand;
    
    // prepare dependencies
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        logoutCommand = new LogoutCommand();
        // mocks methods of HttpServletRequest
        when(request.getSession()).thenReturn(session);
        // mocks methods of HttpSession
        doNothing().when(session).invalidate();
    }
    
    @Test
    public void testExecuteCommandOk() {
        String page = logoutCommand.executeCommand(request);
        assertEquals("Should be equal to " + HOME_PAGE, HOME_PAGE, page);
    }
    
}
