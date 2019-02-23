package jsonrpc.deparser;

/**
 * Exception thrown on syntax error during deparsing process.
 * Can be converted by JSONFactory in an error message for the client
 * 
 * @author fborsani
 */

public class ExceptionDeparse extends Exception{
    
    /**
     * Main constructor. The parameters follow the rules described
     * in the JSON-RPC specification document
     * 
     * @param code numerical code associated with the error
     * @param message error message associated with the error code
     * @param data additional information field. Can be omitted
     */
    
    protected ExceptionDeparse(int code, String message, String data){
        this.code=code;
        this.message=message;
        this.data=data;
    }
    
    public int getErrorCode(){
        return code;
    }
    
    public String getErrorMessage(){
        return message;
    }
    
    public String getErrorData(){
        return data;
    }
    
    @Override
    public String toString(){
        return code+" - "+message+". "+data;
    }
    
    private final int code;
    private final String message;
    private final String data;
}
