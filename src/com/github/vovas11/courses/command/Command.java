package com.github.vovas11.courses.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface {@code Command} is part of Design pattern "Command".
 * Encapsulates the request from the main servlet using the command objects.
 * 
 * @author vovas11
 * @see    CommandFactory
 */
public interface Command {
    /**
     * Method {@code execute} defines the single interface between initiator (servlet)
     * and executor (different commands). Should be overridden in inheriting classes.
     * 
     * @param   request   HTTP request from the servlet
     * @return the name of the page that should be returned by the servlet
     */
    public String execute(HttpServletRequest request);
}
