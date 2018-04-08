package com.devproserv.courses.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.MainConfig.NOT_FOUND_PAGE;

/**
 * {@code NotFound} represents a non-existing command
 *
 */
public class NotFound implements Command {

    private static final Logger logger = LogManager.getLogger(NotFound.class.getName());

    @Override
    public String path(HttpServletRequest request) {
        logger.warn("Command not found!");
        return NOT_FOUND_PAGE;
    }
}
