package jsonrpc.parser;

/**
 * Class used to store the parameters of a json-rpc error. This class is used
 * to supply parameters to a MessageResponse class instance.
 * 
 * @author fborsani
 */

public class Error implements JSONObject{
    
    private final String message;
    private final String data;
    private final int code;
    
    public Error(int code,String message,String data){
        this.code=code;
        this.message=message;
        this.data=data;
    }
    
    public Error(int code,String message){
        this.code=code;
        this.message=message;
        this.data=null;
    }
    
    @Override
    public String parse(){
        String answer = "{\"code\": "  + String.valueOf(code) +", "+
                        "\"message\": " +"\""+ message +"\"";
             
        if(data != null) answer += ", "+"\"data\": "  +"\"" + data  +"\"";
                
        answer += "}";
        
        return answer;
                
    }     
}
