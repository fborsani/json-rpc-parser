package jsonrpc.parser;

import java.util.LinkedHashMap;
import java.util.HashMap;

/**
 * Class used to store the name and the associated value of json-rpc parameter.
 * This class is used to supply parameters to a MessageRequest class instance.
 * 
 * @author fborsani
 */

public class JSONParamList implements JSONObject{
    private final LinkedHashMap<String,String> paramList = new LinkedHashMap<>();
    
    protected JSONParamList(String[] paramNames, String[] paramValues){
        
        int lenght = Integer.min(paramNames.length, paramValues.length);
        
        for(int i=0;i<lenght;++i){
            if(paramNames[i] !=null && paramValues[i] !=null)
                paramList.put(paramNames[i], paramValues[i]);
        }
    }
    
    @Override
    public String parse(){
        
        String answer = "{";
        
        for(HashMap.Entry<String,String> e : paramList.entrySet()){
            answer += "\"" + e.getKey() + "\": ";
            answer += e.getValue() + ", ";          
        }
        
        answer = answer.substring(0,answer.length()-2) + "}"; //removes last comma + space
        
        return answer;
    }
}
