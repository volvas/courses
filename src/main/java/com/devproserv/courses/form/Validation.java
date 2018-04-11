package com.devproserv.courses.form;

/**
 * Represents an interface validating data user inputs in web forms
 *
 */
public interface Validation {

    /**
     * Checks if data is valid. Concrete validation rules are defined in
     * implementing classes.
     *
     * @return true if data is valid
     */
    boolean validated();

    /**
     * Gives information what is wrong in data user inputs
     *
     * @return message with description about wrong input
     */
    String errorMessage();


}
