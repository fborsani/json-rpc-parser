package jsonrpc.deparser;

import java.util.HashMap;

/**
 * Interface used to implement Decoders. All decoders must implement a decode()
 * method where the translation is performed and a getError() method in order to
 * retrieve thrown exceptions.
 * 
 * @author fborsani
 */

public interface IDecoder {
    
    public abstract HashMap<String,String> decode(String[][] elements);
    public abstract ExceptionDeparse getError();

}
