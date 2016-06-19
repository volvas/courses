/**
 * Main servlet handling the login and registration commands
 * 
 * 
 */

package com.github.vovas11.courses.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.vovas11.courses.command.*;

@SuppressWarnings("serial")
public class Handler extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	// gets the CommandFactory instance or create one if applies for the first time
	CommandFactory commandFactory = CommandFactory.getInstance();
	// gets the command from the CommandFactory corresponding to the button pressed by client
	Command command = commandFactory.getCommand(request);
	// runs the command, gets the page to display from the command and sends the page to the client
	String page = command.execute(request);
	RequestDispatcher reqDisp = request.getRequestDispatcher(page);
	reqDisp.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	// TODO remove after testing
	
	System.out.println("Headers:");
	
	Enumeration<String> headers = request.getHeaderNames();
	while(headers.hasMoreElements()) {

	    String headerName = headers.nextElement();
	    String headerValue = request.getHeader(headerName);
	    System.out.println(headerName + "\t : \t" + headerValue);
	}
	
	System.out.println();
	System.out.println("Method: " + request.getMethod());
	System.out.println();
	
	System.out.println("Parameters:");
	
	Enumeration<String> paramNames = request.getParameterNames();
	while (paramNames.hasMoreElements()) {
	    String pN = paramNames.nextElement();
	    System.out.println("Name: " + pN + "\t" + "Value: " + request.getParameter(pN));
	}




	processRequest(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {

	processRequest(request, response);
    }
}
