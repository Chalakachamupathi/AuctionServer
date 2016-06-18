
package stock;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;



public class MainServer { 

    /* Some constants */     
    public static final int BASE_PORT = 2000;  // do not change    

    private String name;
    
    /* local data for the server 
     * Every main server is defined in terms of the port it 
     * listens and the database of allowed users 
     */ 
    private ServerSocket serverSocket=null;  // server Socket for main server 
    private StockDB allowedUsers=null;     // who are allowed to chat 
    
    /**
     *This map is used to save the Bidder objects
     */
    public  HashMap<String,LinkedList<Bidder>> bidders=new HashMap<String,LinkedList<Bidder>>();
    
    private LinkedList<Bidder> objBidders=new LinkedList<Bidder>();

    /**
     *This map used to save the Symbol ,bidder's name and bidding values
     * @param socket
     * @param users
     */
 
    public MainServer(int socket, StockDB users) {
	this.allowedUsers = users; 
	try { 
	    this.serverSocket = new ServerSocket(socket); 
	} catch (IOException e) { 
	    System.out.println(e); 
	}
    }

    /* each server will provide the following functions to 
     * the public. Note that these are non-static 
     */ 
    public boolean isAuthorized(String regNo) { 
	return this.allowedUsers.findSymbol(regNo);
    }    

    public String getName() { 
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    /* server will define how the messages should be posted 
     * this will be used by the connection servers
     */ 

    public void postMSG(String msg) { 
	// all threads print to same screen 
	System.out.println(msg); 
    }

    /**
     *
     * @param code
     * @param bidder
     * In here adding bidders to the list if a bidder is in already 
     * remove it and add updated one.   
     */
    public   void addBidders(String code,Bidder bidder){
       Bidder bd;
      ListIterator<Bidder> listIterator=(ListIterator)objBidders.iterator();
        try{
                while(listIterator.hasNext()){
                    bd=listIterator.next();
                    if(bd.getName().equals(bidder.getName())){
                        objBidders.remove(bd);
                    }
                }
        }catch(Exception e){
            System.out.println(e);
        }
       objBidders.add(bidder);
       bidders.put(code, objBidders);
   } 
   
    
  
    public void server_loop() { 
	try { 
	    while(true) { 
		Socket socket = this.serverSocket.accept(); 
		ConnectionServer worker = new ConnectionServer(this,allowedUsers); 
		worker.handleConnection(socket); 
	    }
	} catch(IOException e) { 
	    System.out.println(e);
	}
    }// end server_loop 
}


	




