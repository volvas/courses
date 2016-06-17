package com.github.vovas11.courses.controller;

import java.io.IOException;

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
	CommandFactory commandFactory = CommandFactory.getInstance();
	Command command = commandFactory.getCommand(request);
	String page = command.execute(request);
	RequestDispatcher reqDisp = request.getRequestDispatcher(page);
	reqDisp.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
	processRequest(request, response);
    }
}
