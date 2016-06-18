----------
HOW TO RUN 
----------
On windows........!!!
just double click on Stock.bat in the dist folder in the project folder'Stock'.
Or
Go to dist folder using cd command in the project folder'Stock',then type "java -jar Stock.jar" in cmd

On linux.........!!!
Go to dist folder using cd command in the project folder'Stock' ,then type "java -jar Stock.jar" in Terminal

----------
HOW TO USE
----------

	Sever side:
		you just have to run the programme thats it...

	Client side:
		for windos users can use 'telnet' through port number 2000 in  cmd
		for linux users can use 'nc' through port number 2000 in terminal.

		example- telnet/nc "server ip" 2000

		After connecting to sever first word that you enter we take it as your name then
		you have to enter valid company code if the code is valid we will show you current price of the company
		then you can start bidding...

	Note:
		After you start bidding to one company you can only bid for that company 
		if want to bid for another company you have to reconnect.


