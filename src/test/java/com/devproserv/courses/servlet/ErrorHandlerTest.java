package com.devproserv.courses.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.devproserv.courses.servlet.ErrorHandler.EXCEPTION_PAGE;
import static com.devproserv.courses.servlet.ErrorHandler.GENERIC_ERR_PAGE;
import static com.devproserv.courses.command.NotFound.NOT_FOUND_PAGE;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Contains unit-tests to check functionality of {@link ErrorHandler} class
 * 
 */
public class ErrorHandlerTest {
    
    private ErrorHandler errorHanlder;
    
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher reqDisp;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        errorHanlder = new ErrorHandler();
    }
    
    
    @Test
    public void testForServerError() throws Exception {
        
        Throwable exception = new IllegalStateException();
        
        when(request.getAttribute("javax.servlet.error.exception")).thenReturn(exception);
        when(request.getRequestDispatcher(EXCEPTION_PAGE)).thenReturn(reqDisp);

        errorHanlder.doGet(request, response);
        verify(reqDisp, atLeastOnce()).forward(request, response);
    }
    
    @Test
    public void testForNotFoundPageError() throws Exception {
        
        when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(404);
        when(request.getRequestDispatcher(NOT_FOUND_PAGE)).thenReturn(reqDisp);
        
        errorHanlder.doGet(request, response);
        verify(reqDisp, atLeastOnce()).forward(request, response);
    }
    
    @Test
    public void testForOtherError() throws Exception {
        
        when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(405);
        when(request.getRequestDispatcher(GENERIC_ERR_PAGE)).thenReturn(reqDisp);
        
        errorHanlder.doGet(request, response);
        
        verify(reqDisp, atLeastOnce()).forward(request, response);
    }

}
