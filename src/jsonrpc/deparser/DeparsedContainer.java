package jsonrpc.deparser;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class stores one or more (in case of a request\response batch) 
 * DeparsedElements classes.
 * 
 * @author fborsani
 */

public class DeparsedContainer {
    private final DeparsedElement[] deparsedElems;
    
   protected DeparsedContainer(DeparsedElement[] deparsedElems){
       this.deparsedElems = deparsedElems;
   }
   
   protected DeparsedContainer(DeparsedElement deparsedElems){
       DeparsedElement[] tmp = {deparsedElems};
       this.deparsedElems = tmp;
   }
   
   public DeparsedElement[] getAll(){ return deparsedElems;}
   public DeparsedElement[] getErrors(){
       
       ArrayList<DeparsedElement> temp = new ArrayList<>();
       for(int i=0; i<deparsedElems.length;++i)
           if(deparsedElems[i].isError())
               temp.add(deparsedElems[i]);
       
       DeparsedElement[] tempArray = new DeparsedElement[temp.size()];
       tempArray = temp.toArray(tempArray);
       return tempArray;
   }
   public DeparsedElement[] getElements(){
       
       ArrayList<DeparsedElement> temp = new ArrayList<>();
       for(int i=0; i<deparsedElems.length;++i)
           if(deparsedElems[i].isElement())
               temp.add(deparsedElems[i]);
       
       DeparsedElement[] tempArray = new DeparsedElement[temp.size()];
       tempArray = temp.toArray(tempArray);
       return tempArray;      
   }
   
    @Override
    public boolean equals(Object o){
        
        if(this == o) return true;
        if(!(o instanceof DeparsedContainer)) return false;
        
        DeparsedContainer containerRight = (DeparsedContainer) o;
        return Objects.deepEquals(this.deparsedElems, containerRight.deparsedElems);
    }
    
    @Override
    public int hashCode(){
        return Objects.hashCode(deparsedElems);
    }
}
