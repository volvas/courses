package com.devproserv.courses.command;

import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Test suite for CommandFactory class
 * It is not fully isolated from other classes in the package
 * 
 * @author vovas11
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CommandFactory.class)
public class CommandFactoryTest {
    
    @Test
    public void testFirst() throws Exception {
        
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("command")).thenReturn("login");
        
        CommandFactory commandFactoryMock = mock(CommandFactory.class);
        
    }
}
