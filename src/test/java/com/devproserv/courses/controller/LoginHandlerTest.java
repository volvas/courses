package com.devproserv.courses.controller;

import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import com.devproserv.courses.command.Command;
import com.devproserv.courses.command.CommandFactory;
import com.devproserv.courses.command.LoginCommand;

/**
 * Test suite for LoginHandler class
 * It is not fully isolated from other classes in the package
 * 
 * 
 * @author vovas11
 *
 */
public class LoginHandlerTest {
    
    @Test
    public void testFirst() throws Exception {
        
        // mocks for classes
        CommandFactory commandFactoryMock = mock(CommandFactory.class);
        Command loginCommandMock = mock(LoginCommand.class);
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        RequestDispatcher reqDispMock = mock(RequestDispatcher.class);
        
        // methods for mocked classes
        when(commandFactoryMock.getCommand(requestMock)).thenReturn(loginCommandMock);
        when(loginCommandMock.executeCommand(requestMock)).thenReturn("/login.jsp");
        when(requestMock.getParameter("command")).thenReturn("login");
        when(requestMock.getRequestDispatcher("/login.jsp")).thenReturn(reqDispMock);
        
        
        doNothing().when(reqDispMock).forward(requestMock, responseMock);
        
        LoginHandler loginHandler = spy(new LoginHandler());
        
        verify(loginHandler, atLeastOnce()).doPost(requestMock, responseMock);
    }
}
