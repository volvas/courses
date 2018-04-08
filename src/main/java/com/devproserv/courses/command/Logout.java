package com.devproserv.courses.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.devproserv.courses.config.MainConfig.HOME_PAGE;

/**
 * {@code Logout} handles logging out for the existing user
 *
 */
public class Logout implements Command {

    private static final Logger logger = LogManager.getLogger(Logout.class.getName());

    @Override
    public String path(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        logger.info("User logged out.");
        return HOME_PAGE;
    }
}
