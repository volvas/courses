package com.devproserv.courses.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devproserv.courses.command.Command;
import com.devproserv.courses.command.CommandFactory;

/**
 * Servlet handles user login and sign up functions.
 * 
 * @author vovas11
 * @see CommandFactory
 */
@SuppressWarnings("serial")
public class LoginHandler extends HttpServlet {
    
    // fields
    private CommandFactory commandFactory;
    
    /**
     * Initializes the CommandFactory instance
     * 
     * @param  config  link to ServletConfig instance
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
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
        String page = "/index.html";
        
        if (commandFactory == null) {
            page = "/404.html";
            forwardToDispatcher(page, request, response);
        }
        
        Command command = commandFactory.getCommand(request);
        if (command == null) {
            page = "/404.html";
            forwardToDispatcher(page, request, response);
        }
        
        page = command.executeCommand(request);
        forwardToDispatcher(page, request, response);
    }
    
    /* sends to RequestDispatcher a page to be returned to client */
    private void forwardToDispatcher(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher reqDisp = request.getRequestDispatcher(page);
        
        if (reqDisp == null) {
            reqDisp = request.getRequestDispatcher("/404.html");
        }
        
        reqDisp.forward(request, response);
    }
}