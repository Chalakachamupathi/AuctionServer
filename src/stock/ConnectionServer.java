
package stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class ConnectionServer implements Runnable { 
    // some constants 
    public static final int WAIT_AUTH = 0; 
    public static final int AUTH_DONE = 1;

    public static final String WAIT_AUTH_MSG = "Wrong code!\n"; 
    public static final String AUTH_DONE_MSG = "Now you can bid and current price is :"; 
    public static final String MSG_POSTED    = "We receved your bid\n"; 
    public static final String ERROE_MSG     = "Please Enter Numbers Only!\n";
    public static final String REQEST_NAME   = "Enter your name:\n";
    public static final String REQEST_CODE   = "Enter company code name:\n";
    public static final String NEW_LINE      = "\n";

    // per connection variables
    private Socket mySocket; // connection socket per thread 
    private int currentState; 
    private StockDB allowedUsers=null;
    private String clientName; 
    private String codeName;
    private MainServer mainServer; 
    
    private  final LinkedList<Double> values=new LinkedList<Double>();

    public ConnectionServer(MainServer mainServer,StockDB sdb) { 
	this.mySocket = null; // we will set this later 
	this.currentState = WAIT_AUTH; 
	this.clientName = null; 
	this.mainServer = mainServer; 
        this.allowedUsers=sdb;
	// who created me. He should give some interface 
    }

    public boolean handleConnection(Socket socket) { 
	this.mySocket = socket; 
	Thread newThread = new Thread(this); 
	newThread.start(); 
	return true; 
    }

    @Override
    public void run() { // can not use "throws .." interface is different
	BufferedReader in=null; 
	PrintWriter out=null; 
	try { 
	    in = new 
		BufferedReader(new InputStreamReader(mySocket.getInputStream()));
	    out = new 
		PrintWriter(new OutputStreamWriter(mySocket.getOutputStream()));
		
	    String line, outline; 
            
                outline=REQEST_NAME;
                out.print(outline); // Send the said message
                out.flush(); // flush to network
                
            //Assign clientName ,the first word that enters
             Bidder b=new Bidder(in.readLine());
             
                outline=REQEST_CODE;
                out.print(outline); // Send the said message
                out.flush(); // flush to network
            
	    for(line = in.readLine(); 
		line != null && !line.equals("quit"); 
		line = in.readLine()) {
   
		switch(currentState) { 
                    case WAIT_AUTH: 
                        
                        
                        if(mainServer.isAuthorized(line.toUpperCase())) { 
                           
                                this.codeName=line.toUpperCase();
                                b.setCode(this.codeName);
                                
                            Double d=Bidder.checkCode(this.codeName);
                            
                                if(d!=null){
                                   outline = AUTH_DONE_MSG+d;
                                   outline=outline+NEW_LINE;
                                }else{
      
                                outline = AUTH_DONE_MSG+allowedUsers.findName(this.codeName);
                                outline=outline+NEW_LINE;
                                }
                            currentState = AUTH_DONE;  
                            
                        }
                        else { 
                            outline = WAIT_AUTH_MSG; 
                        }
                        break;
                        
                    case AUTH_DONE: 
                        try{
                            Double dd=Double.parseDouble(line);
                            values.add(dd);
                           Bidder.saveMsg(this.codeName, values);
                           b.saveBids(dd);
                           outline = MSG_POSTED;
                        }catch(Exception e){
                           outline=ERROE_MSG; 
                        }
                          mainServer.postMSG(b.getName() + " Says: " + line); 
                          
                          mainServer.addBidders(this.codeName, b);
                        break; 
                     
                    default: 
                        System.out.println("Undefined state"); 
                        return; 
		} // case 
		
		out.print(outline); // Send the said message 
		out.flush(); // flush to network

	    } // for 
            
            
            
	    // close everything 
	    out.close(); 
	    in.close(); 
	    this.mySocket.close(); 
	} // try 	     
	catch (IOException e) { 
	    System.out.println(e); 
	} 
        
    }
}

    
    


