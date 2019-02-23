package jsonrpc.parser;

/**
 * Class responsible for storing parameters of a json-rpc styled request message.
 * This class can generate a json-rpc formatted string with the decode() method.
 * 
 * @author fborsani
 */

public class MessageRequest extends Message{ 
    
    private final String method;
    private final String[] paramsPositional;
    private final JSONParamList paramsList;
        
    protected MessageRequest(String method,String[] params,String id){ 
        this.method=method;
        this.paramsPositional=params;
        this.paramsList=null;
        this.id=id;
    }
    
    
    protected MessageRequest(String method,JSONParamList list,String id){
        this.method=method;
        this.paramsPositional=null;
        this.paramsList=list;
        this.id=id;
    }
    
    protected MessageRequest(String method,String id){
        this.method=method;
        this.paramsPositional=null;
        this.paramsList=null;
        this.id=id;
    }
        
    @Override
    public String parse(){
        
        String answer = "{\"jsonrpc\": \""+JSONRPC+"\", "+"\"method\": \""+method+"\"";
        
        if(!(paramsList == null && paramsPositional == null)){
            
            answer += ", \"params\": ";

            if(paramsList == null){

                answer +="[";

                for(int i=0;i<paramsPositional.length;++i){
                    answer += paramsPositional[i];

                    if(i <= paramsPositional.length - 2)
                        answer += ", ";
                }

                answer +="]";
            }

            else
                answer += paramsList.parse();
        }
        
        if(id != null)
            answer += ", \"id\": "+id;
     
        answer +="}";
        
        return answer;
    }
}
