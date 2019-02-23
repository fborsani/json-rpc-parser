package jsonrpc.deparser;

import jsonrpc.deparser.decoders.*;
import static jsonrpc.parser.JSONFactory.*;

import java.util.HashMap;

/**
 * This singleton class is responsible for decoding single json-rpc request or 
 * response Strings. Before starting the translation process (actually performed 
 * by a decoder class) a syntax check is run. If the test is successful the 
 * translator uses one of the available decoder classes to perform the 
 * translation. If a suitable decoder is not found or the string has a syntax
 * error an exception is thrown. The thrown exception can be retrieved and 
 * propagated by the getLastError() method.
 */

final public class TranslatorWorker {
     
    private static TranslatorWorker instance = null;
    private static ExceptionDeparse error = null;
    private static HashMap<String,IDecoder> decoderList = new HashMap<>();
    
    protected TranslatorWorker(){
        this.addDecoder("methodPositionalParams", new DecoderRequest(false));
        this.addDecoder("methodNamedParams", new DecoderRequest(true));
        this.addDecoder("result", new DecoderResponse());
        this.addDecoder("error", new DecoderResponse());
    };
    
    /**
     * Stores and returns the exception thrown in the decode() method during 
     * the last session. Once the decode() method is called again the stored
     * error is set back to null and remains unchanged if no other exceptions
     * are thrown.
     * 
     * @return null as default value or if the last decoding process was 
     * successful. An ExceptionDeparse instance if an error occurred.
     */
    
    protected static ExceptionDeparse getLastError(){
        return error;
    }
    
    protected static TranslatorWorker getInstance(){
        if (instance == null) return instance = new TranslatorWorker();
        else return instance;
    }
    
    /**
     * Adds a new decoder to the translator in order to expand its functionality
     * 
     * @param key the keyword in the json-rpc string associated with the decoder
     * @param decoder an instance of a class that extends the IDecoder 
     * abstract class
     */
    
    protected void addDecoder(String key, IDecoder decoder){
        decoderList.put(key, decoder);
    }
    
    /**
     * 
     * @param JSONString string in json-rpc format to translate
     * 
     * @return an HashMap if the translation process 
     * completed successfully, otherwise returns null
     * 
     * @throws ExceptionDeparse in case of incorrect json-rpc syntax detected
     * by this class or by the decoder employed in the translation process. The
     * error can be retrieved via getLastError() method.
     */
    
    protected HashMap<String,String> Decode (String JSONString) throws ExceptionDeparse {
        
        error = null;   //reset error
        
        boolean RequestHasPositionalParameters = false;
            
        int pCheck=0;           //curly brackets check
        boolean qCheck=false;   //quotes check. If true parenthesis are part of a string and are ignored
        boolean spCheck=false;  //square brackets check
            
        try{ 
                
            for(int i=0; i<JSONString.length();++i){
                               
                if (JSONString.charAt(i) == '"')
                    qCheck = !qCheck;
                
                if (JSONString.charAt(i) == '{' && qCheck == false) 
                    ++pCheck;
                else if (JSONString.charAt(i) == '}'&& qCheck == false)
                    --pCheck;
                
                if (JSONString.charAt(i) == '[' && qCheck == false){
                    spCheck = true;
                    RequestHasPositionalParameters=true;
                }               
                else if(JSONString.charAt(i) == ']' && qCheck == false)
                   spCheck = false;            
            }
            
            if (pCheck != 0 || qCheck || spCheck) throw new ExceptionParseError("Syntax error");
            
            String[][] elements = TranslatorHelper.splitString(JSONString);              
            
            if(elements[0].length >= 3 && elements[0][0].equals("jsonrpc") && elements[0][1].equals(JSONRPC_VERSION)){
                
                String type = elements[0][2];
                
                if(type.equals("method")){
                    if(RequestHasPositionalParameters) type += "PositionalParams";
                    else type += "NamedParams";
                }
                
                IDecoder decoder = decoderList.get(type);
                
                if(decoder == null) throw new ExceptionParseError("Suitable decoder not found");
                else if( decoder.decode(elements) == null) throw decoder.getError();
                else return decoder.decode(elements);               
            }
            else throw new ExceptionParseError("Malformed jsonrpc string"); 
        }
        catch(ExceptionDeparse e){
            error = e; 
            return null; 
        }
    }
}