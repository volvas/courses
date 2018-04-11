package com.devproserv.courses.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents a command coming from web forms via HTTP request.
 *
 */
public interface Command {
    /**
     * Takes a command from user form (sent in HTTP request) and defines the page which
     * is returned as response by the servlet.
     *
     * @param request HTTP request from the servlet
     * @return the name of the page (path) which servlet container redirects to
     */
    String path(HttpServletRequest request);
}
