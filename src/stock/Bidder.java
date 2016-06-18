
package stock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author chalaka
 */
public class Bidder {
    
    public static Map<String,LinkedList<Double>> bidValues=new HashMap<String,LinkedList<Double>>();
    private final String name;
    private  String code;
    private List<Double> bids=new LinkedList<Double>(); 
    

    
    public Bidder(String name){
        this.name=name;
       
    }
    public String getCode(){
        return code;
    }
    public void setCode(String code){
        this.code=code;
    }
    public void saveBids(double bid){
        bids.add(bid);
    }
    //return bit values as a array
    public Object[] getBids(){
        return  bids.toArray();
    }
    
    public String getName(){
        return this.name;
    }
   
    public static Double checkCode(String code){
          for(int i=0;i<bidValues.size();i++){
           if(Bidder.bidValues.containsKey(code.toUpperCase())){
              return bidValues.get(code.toUpperCase()).getLast();
           }
        }
          return null;
    }
     /**
     *
     * @param code
     * @param values
     */
    public synchronized static void saveMsg(String code,LinkedList<Double> values){
            Bidder.bidValues.put(code, values);     
    }
    
}
