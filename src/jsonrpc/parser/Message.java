package jsonrpc.parser;

/**
 * Abstract Class used to generate request and response messages. This class
 * also stores the default jsonrpc value.
 * 
 * @author fborsani
 */

public abstract class Message implements JSONObject {
    
    protected String id;
    protected static final String JSONRPC = JSONFactory.JSONRPC_VERSION;
    
    protected Message(){}
    
    public String getRPCVersion(){return JSONRPC;}
    public String getId(){return id;}
}

