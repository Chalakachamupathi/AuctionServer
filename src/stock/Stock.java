
package stock;

import javax.swing.JFrame;

public class Stock {


    public static void main(String[] args) {
        //reson to create two StockDB object is to get Symbol,price and Symbol,Security Name.
        
        StockDB allowedUsers      = new StockDB("stocks.csv","Symbol","Price ");				        
        StockDB allowedUsersNames = new StockDB("stocks.csv","Symbol","Security Name");
        
        MainServer mainServer = new MainServer(MainServer.BASE_PORT,allowedUsers);
        
        JFrame show=new ShowJFrame(allowedUsers,allowedUsersNames,mainServer);
        show.setTitle("STOCK PRICES");
        show.pack();
        show.setVisible(true);
	mainServer.server_loop(); 
        
    }
    
    
}
