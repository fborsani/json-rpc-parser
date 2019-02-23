package jsonrpc.parser;

/**
 * Class that manages and parses multiple request or response messages sent as
 * a single batch.
 * 
 * @author fborsani
 */

public class JSONBatch implements JSONObject {
    
    private final Message[] elements;
    
    public JSONBatch(MessageRequest[] elements){    //prevents creation of batch with mixed request and response messages
        this.elements = elements;
    }
    
    public JSONBatch(MessageResponse[] elements){
        this.elements = elements;
    }
    
    @Override
    public String parse(){
        String answer = "[";
        
        for(int i=0; i<elements.length; ++i){
            answer += elements[i].parse();
            
            if(i<=elements.length)
                answer += ", ";
        }
        
        answer += "]";
        
        return answer;
    }
}
