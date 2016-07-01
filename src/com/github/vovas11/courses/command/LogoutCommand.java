package com.github.vovas11.courses.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Handles logging out for the existing user
 * 
 * @author vovas11
 * 
 */
public class LogoutCommand implements Command {
    /**
     * Invalidates the session for the current user.
     *
     * @param   request   HTTP request
     * @return the the name of the page the server returns to the client
     */
    @Override
    public String execute(HttpServletRequest request) {
	HttpSession session = request.getSession();
	session.invalidate();
	return "/index.html";
    }
}
