package com.devproserv.courses.servlet;

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

import static com.devproserv.courses.config.MainConfig.STUDENT_PAGE;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Contains unit-tests to check functionality of {@link MainHandler} class.
 * 
 */
public class MainHandlerTest {
    @InjectMocks
    private MainHandler mainHandler;
    @Mock
    private AppContext appContext;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher reqDisp;


    @Before
    public void setUp() {
        mainHandler = new MainHandler();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoPostOk() throws IOException, ServletException {
        when(appContext.getPath(request)).thenReturn(STUDENT_PAGE);
        when(request.getRequestDispatcher(STUDENT_PAGE)).thenReturn(reqDisp);
        
        mainHandler.doPost(request, response);
        verify(reqDisp, atLeastOnce()).forward(request, response);
    }
}
