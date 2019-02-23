package jsonrpc.deparser;

import java.util.regex.Pattern;

/**
 * Utility class used together with TranslatorWorker class
 * to deparse strings written in json format.
 * 
 * @author fborsani
 */

public class TranslatorHelper {
    
    private static final int FIELDS = 3;
    private static final Pattern REG_BRACKETS = Pattern.compile("\\[|\\].{3}|\\{.|\\}.{3}");            //split on [..] or {..} + 3 chars (commas and spaces)
    private static final Pattern REG_ELEMENTS = Pattern.compile("\": \"|\", \"|\"$|\": |, \"|, |,");    //split on ":" / "," / ": / " / ," / ,
    private static final Pattern REG_BATCH = Pattern.compile("_\\$_");                                  //split batch on tagged position
    
    /**
     * Given a string written in json format this method uses a regular expression pattern
     * in order to remove spaces brackets and quotation marks from the string. The string is
     * split in order to separate json keywords (jsonrpc, method, params, id,...) from their values.
     * 
     * @param JSONString string in json-rpc format to operate on
     * 
     * @return a bidimensional array of Strings. The array contains always
     * three sub-arrays with variable length. If the number of generated sub-arrays
     * is smaller than three the empty spaces are filled with null arrays.
     * 
     */
    
    public static String[][] splitString(String JSONString){
              
        JSONString = JSONString.replaceAll("^(\\{.)|(\\}$)|\\]}", ""); //remove parenthesis
       
        String[] elements = REG_BRACKETS.split(JSONString);
           
        String[][] params = new String[FIELDS][];
        
        for(int i=0;i<elements.length;++i){
            params[i] = REG_ELEMENTS.split(elements[i]);
        }
        return params;
    }
    
    /**
     * This method is used to split a batch file in several Strings containing 
     * request or response json-rpc messages.
     * 
     * @param JSONString string in json-rpc format to operate on
     * 
     * @return An array of Strings. every String represents a request or a response
     * previously stored in the batch.
     */
    
    public static String[] splitBatch(String JSONString){
        
        JSONString = JSONString.replaceAll("^\\[|\\]$","");           //remove external [ ]
        JSONString = JSONString.replaceAll("\\},\\{", "\\}_\\$_\\{"); //tag split position for batch (_$_)
        
        String params[] = REG_BATCH.split(JSONString);
      
        return params;
    }
}
