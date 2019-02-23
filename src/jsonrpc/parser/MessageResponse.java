package jsonrpc.parser;

/**
 * Class responsible for storing parameters of a json-rpc styled message. This
 * class can generate a json-rpc formatted string with the decode() method.
 * 
 * @author fborsani
 */

public class MessageResponse extends Message{
    
    private final String result;
    private final String error;
    
    protected MessageResponse(String result,String id){
       this.result=result;
       this.id=id;
       this.error=null;
    }
    
    protected MessageResponse(JSONObject error){
        this.error=error.parse();
        this.id="null";
        this.result=null;
    }
    
    @Override
    public String parse(){
        String answer;
        
        if(error == null){
            answer="{\"jsonrpc\": "+"\"" + JSONRPC +"\""+ ", "+
                    "\"result\": " + result +", "+
                    "\"id\": "     + id +
                    "}";
        }
        else{
            answer="{\"jsonrpc\": "+"\"" + JSONRPC +"\""+ ", "+
                    "\"error\": " + error +", "+
                    "\"id\": null}";
        }
        return answer;
    }
}
