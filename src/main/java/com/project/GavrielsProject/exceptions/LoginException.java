package com.project.GavrielsProject.exceptions;

public class LoginException extends Exception {
    /**
     * Default exception for clients Login try function problems or deviation from specific limitations (In connection with the DB).
     */
    public LoginException() {
        System.out.println("Sorry, there was a problem in the login attempt");
    }

    /**
     * Constructor which allows to insert a specific custom massage according to the correct error
     *
     * @param message the specific custom message for the correct error
     */
    public LoginException(String message) {
        super(message);
    }
}
