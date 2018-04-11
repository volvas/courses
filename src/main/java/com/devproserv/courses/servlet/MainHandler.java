package com.devproserv.courses.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles all web forms in the application like registration and login procedures,
 * handling course and student lists
 * 
 */
@WebServlet(urlPatterns={"/login", "/courses"}, name = "mainHandler")
public class MainHandler extends HttpServlet {

    private static final long serialVersionUID = 901070610445666149L;

    private AppContext appContext;
    
    /**
     * Initializes Application context
     * 
     * @param  config  reference to ServletConfig instance
     */
    @Override
    public void init(ServletConfig config) {
        appContext = new AppContext();
    }

    /**
     * Forwards request to a page defined by a command in HTTP request.
     * 
     * @param  request   HTTP request
     * @param  response  HTTP response
     * @throws ServletException standard Servlet Exception
     * @throws IOException standard IO Exception
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final String pathToForward = appContext.getPath(request);
        final RequestDispatcher dispatcher = request.getRequestDispatcher(pathToForward);
        dispatcher.forward(request, response);
    }
}
