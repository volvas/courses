package com.devproserv.courses.servlet;

import static com.devproserv.courses.config.MainConfig.NOT_FOUND_PAGE;
import static com.devproserv.courses.config.MainConfig.GENERIC_ERR_PAGE;
import static com.devproserv.courses.config.MainConfig.EXCEPTION_ERR_PAGE;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet handles errors related to wrong requests and JVM's exceptions.
 * 
 * @author vovas11
 */
@WebServlet(urlPatterns={"/error"}, name = "errorHandler")
public class ErrorHandler extends HttpServlet {

    private static final long serialVersionUID = -9152573062248666207L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Collect error info from the request
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        
        String pageToRedirect = GENERIC_ERR_PAGE;
        
        // select three variants: exception, status 404, and others
        if (throwable != null) {
            pageToRedirect = EXCEPTION_ERR_PAGE;
        } else if (statusCode == 404) {
            pageToRedirect = NOT_FOUND_PAGE;
        }
        
        RequestDispatcher reqDisp = request.getRequestDispatcher(pageToRedirect);
        reqDisp.forward(request, response);
    }
}
