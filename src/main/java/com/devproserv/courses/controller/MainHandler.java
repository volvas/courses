package com.devproserv.courses.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet realizes main logic of handling user requests.
 * These are registration and login procedures, handling course and student lists
 * 
 * @author vovas11
 */
@WebServlet(urlPatterns={"/login", "/courses"}, name = "mainHandler")
public class MainHandler extends HttpServlet {

    private static final long serialVersionUID = 901070610445666149L;

    private AppContext appContext;
    
    /**
     * Initialize Application context
     * 
     * @param  config  link to ServletConfig instance
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        appContext = new AppContext();
    }

    /**
     * Forward control to a path defined by HTTP request.
     * 
     * @param  request   HTTP request
     * @param  response  HTTP response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final String pathToForward = appContext.getPath(request);
        final RequestDispatcher dispatcher = request.getRequestDispatcher(pathToForward);
        dispatcher.forward(request, response);
    }
}
