package com.auliaAnugrahAzizJBusRD.controller;

/**
 * BaseResponse provides information of the payload in the case
 * of testing the backend using postman or other backend testing tools.
 *
 * @author Aulia Anugrah Aziz
 * @version 10 December 2023
 */
public class BaseResponse<T> {
    public boolean success;
    public String message;
    public T payload;

    /**
     * Constructs and initializes BaseResponse
     *
     * @param success   determines the success of the response
     * @param message   give a desired summary of the response
     * @param payload   object of the response
     */
    public BaseResponse(boolean success, String message, T payload) {
        this.success = success;
        this.message = message;
        this.payload = payload;
    }
}
