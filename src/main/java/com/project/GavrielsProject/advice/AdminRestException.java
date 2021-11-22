package com.project.GavrielsProject.advice;

import com.project.GavrielsProject.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class AdminRestException {

    /**
     * handle AddException
     * Generates a built-in error message with http status for AddException
     *
     * @param e the exception was caught
     * @return The thrown error message
     */
    @ExceptionHandler(value = {AddException.class})
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    public ErrorDetail handleAddException (Exception e){
        return new ErrorDetail("ADD Error", e.getMessage());
    }

    /**
     * handle DeleteException
     * Generates a built-in error message with http status for DeleteException
     *
     * @param e the exception was caught
     * @return The thrown error message
     */
    @ExceptionHandler(value = {DeleteException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleDeleteException (Exception e){
        return new ErrorDetail("DELETE Error", e.getMessage());
    }

    /**
     * handle LoginException
     * Generates a built-in error message with http status for LoginException
     *
     * @param e the exception was caught
     * @return The thrown error message
     */
    @ExceptionHandler(value = {LoginException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorDetail handleLoginException (Exception e){
        return new ErrorDetail("LOGIN Error", e.getMessage());
    }

    /**
     * handle PurchaseException
     * Generates a built-in error message with http status for PurchaseException
     *
     * @param e the exception was caught
     * @return The thrown error message
     */
    @ExceptionHandler(value = {PurchaseException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handlePurchaseException (Exception e){
        return new ErrorDetail("PURCHASE Error", e.getMessage());
    }

    /**
     * handle UpdateException
     * Generates a built-in error message with http status for UpdateException
     *
     * @param e the exception was caught
     * @return The thrown error message
     */
    @ExceptionHandler(value = {UpdateException.class})
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    public ErrorDetail handleUpdateException (Exception e){
        return new ErrorDetail("UPDATE Error", e.getMessage());
    }



}
