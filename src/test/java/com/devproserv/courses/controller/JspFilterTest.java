package com.devproserv.courses.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test suite for JspFilter class.
 * Due to the tested method is void, only call of other methods
 * inside this method is checked
 * 
 * @author vovas11
 *
 */
public class JspFilterTest {
    
    // dependencies to be mocked
    @Mock
    HttpServletRequest request;
    
    @Mock
    HttpServletResponse response;
    
    @Mock
    FilterChain chain;
    
    
    // prepare dependencies
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoFilter() throws IOException, ServletException {
        
        // methods for mocked classes
        when(request.getContextPath()).thenReturn("/courses");
        doNothing().when(response).sendRedirect(anyString());
        
        // create instance to test the method
        JspFilter jFilter = new JspFilter();
        jFilter.doFilter(request, response, chain);
        
        verify(response, atLeastOnce()).sendRedirect(anyString());
        
    }

}
