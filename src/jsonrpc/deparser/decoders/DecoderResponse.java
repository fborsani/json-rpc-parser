package jsonrpc.deparser.decoders;

import jsonrpc.deparser.ExceptionParseError;
import jsonrpc.deparser.ExceptionDeparse;

import java.util.HashMap;
import jsonrpc.deparser.IDecoder;

/**
 * Decoder that translates all json-rpc response type of messages, included
 * the ones that carry error structures
 * 
 * @author fborsani
 */

public class DecoderResponse implements IDecoder{
    public DecoderResponse(){};
    
    private ExceptionParseError error = null;
    
    @Override
    public ExceptionDeparse getError(){ return error;}
    
    /**
     * Main decoder method
     * 
     * @param elements a json-rpc string split by the TranslatorHelper class
     * 
     * @return an HashMap where every json-rpc keyword (jsonrpc, result, id) is
     * associated with its value. If the message is transporting an error entity
     * its keywords (code, message, data) will also be processed. A message
     * carrying an error and with an id != null is considered invalid.
     * 
     */
    
   @Override
    public HashMap<String,String> decode(String[][] elements){ 
        try{
            HashMap<String,String> decodedMap = new HashMap<>();
            int fields =0;
            String[] keywords = {"jsonrpc","result","id"};
            String[] keywordsError = {"jsonrpc","error","code","message","data","id"};
            
            while(fields < elements.length && elements[fields] != null) ++fields;
            
            switch(fields){
                case 3: //case with unsuccessful operation (error field)
                    for(int f=0,k=0;f<fields;++f){  //f = current field k = current keyword (updated in inner for cycle)
                        for(int i=0; i<elements[f].length; i+=2, ++k){
                            if(elements[f][i].equals(keywordsError[1]) && f==0){    //check "error" keyword (must be in first field)
                                ++k;
                                break;
                            }                           
                            else if(k==4 && f==2) ++k;   //skip "data" keyword if was not present in error body
                            
                            if(i+1 < elements[f].length && elements[f][i].equals(keywordsError[k]) && elements[f][i+1] != null){
                                if(k==5){               //check id equals null
                                    if(elements[f][i+1].equals("null")) decodedMap.put(elements[f][i], elements[f][i+1]);
                                    else throw new ExceptionParseError(keywordsError[k]+" field is not null");
                                }               
                                else decodedMap.put(elements[f][i], elements[f][i+1]);
                            }               
                            else throw new ExceptionParseError("Invalid "+keywordsError[k]+" field");
                        }
                    }
                    break;
                case 1: //case with successful operation (result field)
                    for(int i=0,j=0;i<elements[0].length;i+=2,++j){
                        if(i+1 < elements[0].length && elements[0][i].equals(keywords[j]) && elements[0][i+1] != null) 
                            decodedMap.put(elements[0][i], elements[0][i+1]);
                        else throw new ExceptionParseError("Invalid "+keywords[j]+" field");
                    }
                    break;
                default:
                    throw new ExceptionParseError("String is malformed or empty");
            }          
            return decodedMap;      
        }
        catch(ExceptionParseError e){
            error = e;
            return null;
        }
    }
}
