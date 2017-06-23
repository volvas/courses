package com.devproserv.courses.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devproserv.courses.command.Command;
import com.devproserv.courses.command.CommandFactory;

import static com.devproserv.courses.config.MainConfig.HOME_PAGE;
import static com.devproserv.courses.config.MainConfig.NOT_FOUND_PAGE;

/**
 * Servlet realizes main logic of handling user requests.
 * 
 * @author vovas11
 * @see CommandFactory
 */
@WebServlet(urlPatterns={"/login", "/courses"} )
public class MainHandler extends HttpServlet {
    
    private static final long serialVersionUID = -1967971687994990895L;
    
    
    private CommandFactory commandFactory;
    
    /**
     * Initializes the CommandFactory instance
     * 
     * @param  config  link to ServletConfig instance
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        AppInitializer appInit = new AppInitializer();
        appInit.initBeans();
        commandFactory = CommandFactory.getInstance();
    }

    /**
     * Selects a command corresponding to the parameter sent from the user form.
     * 
     * @param  request   HTTP request
     * @param  response  HTTP response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // creates command by name, runs it and returns page to be returned to client
        String page = HOME_PAGE;
        
        Command command = commandFactory.getCommand(request);
        if (command == null) {
            forwardToDispatcher(NOT_FOUND_PAGE, request, response);
        }
        
        page = command.executeCommand(request);
        forwardToDispatcher(page, request, response);
    }
    
    /* sends to RequestDispatcher a page to be returned to client */
    private void forwardToDispatcher(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher reqDisp = request.getRequestDispatcher(page);
        
        if (reqDisp == null) {
            reqDisp = request.getRequestDispatcher(NOT_FOUND_PAGE);
        }
        
        reqDisp.forward(request, response);
    }
}
