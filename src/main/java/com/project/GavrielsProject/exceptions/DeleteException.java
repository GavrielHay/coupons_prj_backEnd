package com.project.GavrielsProject.exceptions;

public class DeleteException extends Exception {
    /**
     * Default exception for "Delete" ( = from CRUD) problems or deviation from specific limitations (In connection with the DB).
     */
    public DeleteException() {
        System.out.println("Sorry, there was a problem in the delete attempt");
    }

    /**
     * Constructor which allows to insert a specific custom massage according to the correct error
     *
     * @param message the specific custom message for the correct error
     */
    public DeleteException(String message) {
        super(message);
    }
}
