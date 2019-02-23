package jsonrpc.parser;

import jsonrpc.deparser.ExceptionDeparse;


/**
 * Singleton class responsible for the creation of JSONObjects. This class also
 * stores the current jsonrpc version and the standard json-rpc error codes.
 * 
 * @author fborsani
 */

public final class JSONFactory {

    public static final String JSONRPC_VERSION = "2.0";
    
    public static final int PARSE_ERROR_CODE = -32700;
    public static final int REQUEST_ERROR_CODE = -32600;
    public static final int METHOD_ERROR_CODE = -32601;
    public static final int PARAM_ERROR_CODE = -32602;
    public static final int INTERNAL_ERROR_CODE = -32603;
    
    private static JSONFactory instance=null;
    private JSONFactory(){}
    
    public static JSONFactory getInstance(){
        if(instance == null) return instance = new JSONFactory();
        else return instance;
    }
    
    public JSONObject getJSONParamList(String[] paramNames, String[] paramValues){
        return new JSONParamList(paramNames,paramValues);
    }
    
    public JSONObject getRequest(String method,String[] params, String id){
        return new MessageRequest(method,params,id);
    }
    
    public JSONObject getRequest(String method,JSONParamList params, String id){
        return new MessageRequest(method,params,id);
    }
    
    public JSONObject getRequest(String method, String id){
        return new MessageRequest(method,id);
    }
    
    public JSONObject getNotification(String method,String[] params){
        return new MessageRequest(method,params,null);
    }
    
    public JSONObject getNotification(String method,JSONParamList params){
        return new MessageRequest(method,params,null);
    }
    
    public JSONObject getNotification(String method){
        return new MessageRequest(method,null);
    }
       
    public JSONObject getResponse(String result, String id){
        return new MessageResponse(result,id);
    }
    
    public JSONObject getResponseError(int code, String message, String data){
        return new MessageResponse(new Error(code,message,data));
    }
    
    public JSONObject getResponseError(ExceptionDeparse e){
        return new MessageResponse(new Error(e.getErrorCode(),e.getErrorMessage(),e.getErrorData()));
    }
    
    public JSONObject getError(int code, String message, String data){
        return new Error(code,message,data);
    }
    
    public JSONObject getError(ExceptionDeparse e){
        return new Error(e.getErrorCode(),e.getErrorMessage(),e.getErrorData());
    }
    
    public JSONObject getBatch(MessageRequest[] elements){
        return new JSONBatch(elements);
    }
    
    public JSONObject getBatch(MessageResponse[] elements){
        return new JSONBatch(elements);
    }
}
