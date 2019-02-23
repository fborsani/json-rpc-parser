package jsonrpc.deparser;

import java.util.ArrayList;
import java.util.HashMap;

public class Translator {
    
    public Translator(){}
    
    public DeparsedContainer decode(String JSONString){
        
        try{
            
            if(JSONString == null) throw new ExceptionParseError("Null String");
            
            ArrayList<DeparsedElement> arrayDeparsedElements = new ArrayList<>();
            
            String[] elements = TranslatorHelper.splitBatch(JSONString);
        
            if(elements.length == 0) throw new ExceptionInvalidRequest("Empty request");
                
            for(int i=0; i<elements.length; ++i){
                HashMap<String,String> result = TranslatorWorker.getInstance().Decode(elements[i]);
                
                if(result == null)
                    arrayDeparsedElements.add(new DeparsedElement(TranslatorWorker.getLastError()));
                else
                    arrayDeparsedElements.add(new DeparsedElement(result));
            }
            
            return new DeparsedContainer(arrayDeparsedElements.toArray(new DeparsedElement[arrayDeparsedElements.size()]));
        }
        catch(ExceptionDeparse e){
            return new DeparsedContainer(new DeparsedElement(e));
        }
    }
}
