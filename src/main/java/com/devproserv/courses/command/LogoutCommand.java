package com.devproserv.courses.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.devproserv.courses.config.MainConfig.HOME_PAGE;

/**
 * {@code LogoutCommand} handles logging out for the existing user
 * 
 * @author vovas11
 * 
 */
public class LogoutCommand implements Command {
    /**
     * Invalidates the session for the current user.
     *
     * @param request HTTP request
     * @return the the name home page
     */
    @Override
    public String path(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return HOME_PAGE;
    }
}
