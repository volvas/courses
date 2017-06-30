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

import static com.devproserv.courses.config.MainConfig.NOT_FOUND_PAGE;

/**
 * Servlet realizes main logic of handling user requests.
 * These are registration and login procedures, handling course and student lists
 * 
 * @author vovas11
 * @see CommandFactory
 */
@WebServlet(urlPatterns={"/login", "/courses"}, name = "mainHandler")
public class MainHandler extends HttpServlet {
    
    private static final long serialVersionUID = -1332398953762002453L;
    
    /** Injection of the main app manager */
    private AppContext appContext;
    
    /**
     * Initializes the AppContext self and other dependencies
     * 
     * @param  config  link to ServletConfig instance
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        appContext = AppContext.getAppContext();
        appContext.initBeans();
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
        String commandRequest = request.getParameter("command");
        Command command = appContext.getCommand(commandRequest);
        // defence in case of intentionally wrong commands
        String path = (command == null) ? NOT_FOUND_PAGE
                                        : command.executeCommand(request);
        // forwards control to another servlet handling requests with provided path
        RequestDispatcher reqDisp = request.getRequestDispatcher(path);
        reqDisp.forward(request, response);
    }
}
