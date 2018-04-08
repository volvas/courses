package com.devproserv.courses.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Defines the single interface for all commands coming from the user forms via HTTP request.
 */
public interface Command {
    /**
     * Takes a command from user form (sent in HTTP request) and defines the page which
     * is returned as response by the servlet.
     *
     * @param request HTTP request from the servlet
     * @return the name of the page (path) which browser is redirected to
     */
    String path(HttpServletRequest request);
}
