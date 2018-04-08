package com.devproserv.courses.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.devproserv.courses.config.MainConfig.HOME_PAGE;

/**
 * {@code Logout} handles logging out for the existing user
 *
 */
public class Logout implements Command {
    @Override
    public String path(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return HOME_PAGE;
    }
}
