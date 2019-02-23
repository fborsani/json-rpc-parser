package jsonrpc.deparser;

import static jsonrpc.parser.JSONFactory.REQUEST_ERROR_CODE;

/**
 * Exception class that extends ExceptionDeparse class.
 * This class is created with the "Invalid Request" error code
 * and message.
 * 
 * @author fborsani
 */

public class ExceptionInvalidRequest extends ExceptionDeparse{
    
    /**
     * Allows insertion of additional informations. The data field can be null
     * @param data additional information about the error
     */
    
    public ExceptionInvalidRequest(String data){
        super(REQUEST_ERROR_CODE,"Invalid Request",data);
    }
}
