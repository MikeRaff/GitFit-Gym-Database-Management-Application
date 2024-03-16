package ca.mcgill.ecse321.gymregistration.service.exception;

import org.springframework.http.HttpStatus;

public class GRSException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;

    /**
     * getStatus: Get the current http status
     * @return
     */
    public HttpStatus getStatus() {
        return httpStatus;
    }

    /**
     * GRSException: Gym Registration System Exception class
     * @param httpStatus : Http status of exception
     * @param response : The response message
     */
    public GRSException(HttpStatus httpStatus, String response){
        super(response);
        this.httpStatus = httpStatus;
    }
}
