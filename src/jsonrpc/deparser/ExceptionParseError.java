package jsonrpc.deparser;

import static jsonrpc.parser.JSONFactory.PARSE_ERROR_CODE;

/**
 * Exception class that extends ExceptionDeparse class.
 * This class is created with the "Parse Error" error code
 * and message.
 * 
 * @author fborsani
 */

public class ExceptionParseError extends ExceptionDeparse{
    
    /**
     * Allows insertion of additional informations. The data field can be null
     * @param data additional information about the error
     */
    
    public ExceptionParseError(String data){     
        super(PARSE_ERROR_CODE,"Parse error",data);
    }         
}
