package com.devproserv.courses.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;

import static com.devproserv.courses.config.MainConfig.EXCEPTION_ERR_PAGE;
import static com.devproserv.courses.config.MainConfig.NOT_FOUND_PAGE;
import static com.devproserv.courses.config.MainConfig.GENERIC_ERR_PAGE;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test suite for ErrorHandler class
 * 
 * @author vovas11
 *
 */
public class ErrorHandlerTest {
    
    // dependencies to be mocked
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher reqDisp;
    
    // prepare dependencies
    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    
    @Test
    public void testForServerError() throws Exception {
        
        // Auxiliary instances
        Throwable exception = new IllegalStateException();
        
        // methods for mocked classes
        when(request.getAttribute("javax.servlet.error.exception")).thenReturn(exception);
        when(request.getRequestDispatcher(EXCEPTION_ERR_PAGE)).thenReturn(reqDisp);
        
        // create instance to test the method
        ErrorHandler errorHanlder = new ErrorHandler();
        errorHanlder.doGet(request, response);
        
        verify(reqDisp, atLeastOnce()).forward(request, response);
    }
    
    @Test
    public void testForNotFoundPageError() throws Exception {
        
        // methods for mocked classes
        when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(Integer.valueOf(404));
        when(request.getRequestDispatcher(NOT_FOUND_PAGE)).thenReturn(reqDisp);
        
        // create instance to test the method
        ErrorHandler errorHanlder = new ErrorHandler();
        errorHanlder.doGet(request, response);
        
        verify(reqDisp, atLeastOnce()).forward(request, response);
    }
    
    @Test
    public void testForOtherError() throws Exception {
        
        // methods for mocked classes
        when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(Integer.valueOf(405));
        when(request.getRequestDispatcher(GENERIC_ERR_PAGE)).thenReturn(reqDisp);
        
        // create instance to test the method
        ErrorHandler errorHanlder = new ErrorHandler();
        errorHanlder.doGet(request, response);
        
        verify(reqDisp, atLeastOnce()).forward(request, response);
    }

}
