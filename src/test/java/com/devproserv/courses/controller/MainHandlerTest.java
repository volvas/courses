package com.devproserv.courses.controller;

import com.devproserv.courses.command.Command;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Contains unit-tests to check functionality of {@link MainHandler} class.
 * 
 * @author vovas11
 *
 */
public class MainHandlerTest {
    
    @InjectMocks
    private MainHandler mainHandler;

    // dependencies to be mocked
    @Mock
    private AppContext appContext;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher reqDisp;
    @Mock
    private Command command;
    
    @Before
    public void setUp() throws Exception {
        mainHandler = new MainHandler();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoPostOk() throws IOException, ServletException {
        
        // methods for mocked classes
        when(appContext.getPath(request)).thenReturn("/courses.jsp");
        when(request.getRequestDispatcher("/courses.jsp")).thenReturn(reqDisp);
        
        mainHandler.doPost(request, response);
        verify(reqDisp, atLeastOnce()).forward(request, response);
    }
}
