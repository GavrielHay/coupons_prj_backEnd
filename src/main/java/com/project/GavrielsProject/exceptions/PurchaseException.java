package com.project.GavrielsProject.exceptions;

public class PurchaseException extends Exception {
    /**
     * Default exception for Coupons purchase method (in Customer Service) problems or deviation from specific limitations (In connection with the DB).
     */
    public PurchaseException() {
        System.out.println("Sorry, there was a problem in the purchase attempt");
    }

    /**
     * Constructor which allows to insert a specific custom massage according to the correct error
     *
     * @param message the specific custom message for the correct error
     */
    public PurchaseException(String message) {
        super(message);
    }
}
