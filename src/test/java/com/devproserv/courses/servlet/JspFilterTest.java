package com.devproserv.courses.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Contains unit-tests to check functionality of {@link JspFilter} class.
 * Due to the tested method is void, only call of other methods
 * inside this method is checked
 * 
 */
public class JspFilterTest {
    
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoFilter() throws IOException {
        
        when(request.getContextPath()).thenReturn("/courses");
        doNothing().when(response).sendRedirect(anyString());
        
        JspFilter jFilter = new JspFilter();
        jFilter.doFilter(request, response, chain);
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}
