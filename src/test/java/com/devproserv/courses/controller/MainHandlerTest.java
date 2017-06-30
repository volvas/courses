package com.devproserv.courses.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static com.devproserv.courses.config.MainConfig.NOT_FOUND_PAGE;

import javax.servlet.RequestDispatcher;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.devproserv.courses.command.Command;

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
        when(request.getParameter("command")).thenReturn("login");
        when(appContext.getCommand("login")).thenReturn(command);
        when(command.executeCommand(request)).thenReturn("/courses.jsp");
        when(request.getRequestDispatcher("/courses.jsp")).thenReturn(reqDisp);
        
        mainHandler.doPost(request, response);
        verify(reqDisp, atLeastOnce()).forward(request, response);
    }
    
    @Test(expected = NullPointerException.class)
    public void testDoPostWrongRequestParameterName() throws IOException, ServletException {
        
        // methods for mocked classes
        when(request.getParameter("wrongcommand")).thenReturn("login");
        when(appContext.getCommand("login")).thenReturn(command);
        when(command.executeCommand(request)).thenReturn("/courses.jsp");
        when(request.getRequestDispatcher("/courses.jsp")).thenReturn(reqDisp);
        
        mainHandler.doPost(request, response);
        verify(reqDisp, atLeastOnce()).forward(request, response);
    }

    @Test(expected = NullPointerException.class)
    public void testDoPostNullCommand() throws IOException, ServletException {
        
        // methods for mocked classes
        when(request.getParameter("command")).thenReturn("login");
        when(appContext.getCommand("login")).thenReturn(null);
        when(command.executeCommand(request)).thenReturn("/courses.jsp");
        when(request.getRequestDispatcher("/courses.jsp")).thenReturn(reqDisp);
        
        mainHandler.doPost(request, response);
        verify(reqDisp, atLeastOnce()).forward(request, response);
    }
    
    @Test
    public void testDoPostNoExecuteCommnadWithNull() throws IOException, ServletException {
        
        // methods for mocked classes
        when(request.getParameter("command")).thenReturn("login");
        when(appContext.getCommand("login")).thenReturn(null);
        when(request.getRequestDispatcher(NOT_FOUND_PAGE)).thenReturn(reqDisp);
        when(command.executeCommand(request)).thenReturn("/courses.jsp");
        
        doNothing().when(reqDisp).forward(request, response);
        
        mainHandler.doPost(request, response);
        verify(command, never()).executeCommand(request);
    }
}
