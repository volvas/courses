package com.devproserv.courses.form;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents a web form. Validates the data and handles request
 *
 */
public interface Form {
    /**
     * Checks all data user inputs in web forms
     *
     * @param request HTTP request
     * @return path to the same page containing the form in case of invalidated data
     *         or path to further page if validation is successful
     */
    String validate(HttpServletRequest request);
}
