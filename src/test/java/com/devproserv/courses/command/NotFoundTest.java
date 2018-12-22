package com.devproserv.courses.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.Conf.NOT_FOUND_PAGE;
import static org.junit.Assert.assertEquals;

/**
 * Contains unit-tests to check functionality of {@link NotFound} class
 *
 */
public class NotFoundTest {

    @Mock
    private HttpServletRequest request;

    private Command notFound;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        notFound = new NotFound();
    }

    @Test
    public void testExecuteCommandOk() {
        String path = notFound.path(request);
        assertEquals("Should be equal to " + NOT_FOUND_PAGE, NOT_FOUND_PAGE, path);
    }
}
