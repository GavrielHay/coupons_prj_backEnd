package com.project.GavrielsProject.exceptions;

public class AddException extends Exception {

    /**
     * Default exception for "Creation" ( = from CRUD) problems or deviation from specific limitations (In connection with the DB).
     */
    public AddException() {
        System.out.println("Sorry, there was a problem in the add attempt");
    }

    /**
     * Constructor which allows to insert a specific custom massage according to the correct error
     *
     * @param message the specific custom message for the correct error
     */
    public AddException(String message) {
        super(message);
    }
}
