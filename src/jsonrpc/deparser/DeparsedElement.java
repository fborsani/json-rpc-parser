package jsonrpc.deparser;

import java.util.Collections;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;
import jsonrpc.parser.JSONFactory;
import jsonrpc.parser.JSONObject;

/**
 * Class used to store the result of the deparsing operation or an Error class
 * if the translation process failed. The error can be returned as an already
 * parsed json-rpc response string ready to be sent or an JSONObject class.
 * 
 * @author fborsani
 */

public class DeparsedElement {
    
    private final HashMap<String,String> deparsedElement;
    private final String jsonError;
    private final JSONObject error; 
    
    protected DeparsedElement(HashMap<String,String> deparsedElement){
        this.deparsedElement = deparsedElement;
        
        error = null;
        jsonError = null;
    }
    
    protected DeparsedElement(ExceptionDeparse e){
        this.error = JSONFactory.getInstance().getError(e);
        this.jsonError = JSONFactory.getInstance().getResponseError(e).parse();
        
        deparsedElement = null;
    }
    
    public Map<String,String> getElement(){ return Collections.unmodifiableMap(deparsedElement);}
    
    public JSONObject getError(){return error;}
    public String getErrorParsed(){return jsonError;}
    
    public boolean isError(){ return jsonError != null;}
    public boolean isElement(){ return deparsedElement != null;}
    
    @Override
    public boolean equals(Object o){
        
        if(this == o) return true;
        if(!(o instanceof DeparsedElement)) return false;
        
        DeparsedElement elemRight = (DeparsedElement) o;
        return Objects.deepEquals(this.deparsedElement, elemRight.deparsedElement) &&
               Objects.equals(this.error, elemRight.error) && Objects.equals(this.jsonError, elemRight.jsonError);
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(deparsedElement,error,jsonError);
    }
    
    @Override
    public String toString(){
        String answer="no element\n";
        
        for(HashMap.Entry<String,String> entry: deparsedElement.entrySet()){
            answer += entry.getKey() + ": " + entry.getValue()+"\n";
        }
        
        return answer;
    }
    
}
