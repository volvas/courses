package com.devproserv.courses.command;

import com.devproserv.courses.servlet.AppContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.MainConfig.SIGNUP_PAGE;
import static org.junit.Assert.assertEquals;

/**
 * Contains unit-tests to check functionality of {@link SignUp} class
 * 
 */
public class SignUpTest {
    
    @Mock
    private AppContext appContext;
    @Mock
    private HttpServletRequest request;

    private SignUp signup;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        signup = new SignUp(appContext);
    }


    @Test
    public void testPathOk() {
        String path = signup.path(request);
        assertEquals(SIGNUP_PAGE, path);
    }
    
}
