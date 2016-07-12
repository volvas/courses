package com.github.vovas11.courses.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.vovas11.courses.command.*;

/**
 * Main servlet handling user forms on HTML pages. Gets the link to the CommandFactory
 * and depending on the command from the user form sent in HTTP request hands control
 * over to the corresponding command.
 * 
 * @author vovas11
 * @see CommandFactory
 */
@SuppressWarnings("serial")
public class Handler extends HttpServlet {
    /**
     * Selects a command corresponding to the parameter sent from the user form
     * in HTTP request. Unites the methods doPost and doGet.
     * 
     * @param  request   HTTP request
     * @param  response  HTTP response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	/* gets link to the CommandFactory instance or creates one if applies for the first time */
	CommandFactory commandFactory = CommandFactory.getInstance();
	
	/* gets link to the command from the CommandFactory */
	Command command = commandFactory.getCommand(request);
	
	/* runs the command, gets the page to display from the command and sends the page to the client */
	String page = command.execute(request);
	RequestDispatcher reqDisp = request.getRequestDispatcher(page);
	reqDisp.forward(request, response);
    }
    
    /**
     * Standard servlet method, 'overridden' by {@code processRequest} method
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	/* call common method for POST and GET methods */
	processRequest(request, response);
    }
    
    /**
     * Standard servlet method, 'overridden' by {@code processRequest} method
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
	
	/* call common method for POST and GET methods */
	processRequest(request, response);
    }
}
