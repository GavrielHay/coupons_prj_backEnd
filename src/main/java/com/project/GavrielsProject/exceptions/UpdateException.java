package com.project.GavrielsProject.exceptions;

public class UpdateException extends Exception {
    /**
     * Default exception for "Update" ( = from CRUD) problems or deviation from specific limitations (In connection with the DB).
     */
    public UpdateException() {
        System.out.println("Sorry, there was a problem in the upload attempt");
    }

    /**
     * Constructor which allows to insert a specific custom massage according to the correct error
     *
     * @param message the specific custom message for the correct error
     */
    public UpdateException(String message) {
        super(message);
    }
}
