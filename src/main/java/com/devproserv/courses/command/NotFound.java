package com.devproserv.courses.command;

import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.MainConfig.NOT_FOUND_PAGE;

/**
 * Represents a non-existing command
 *
 */
public class NotFound implements Command {
    @Override
    public String path(HttpServletRequest request) {
        return NOT_FOUND_PAGE;
    }
}
