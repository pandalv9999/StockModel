Team 19657 -
Shuomin Wu
Rujun Yao

Assignment 10: OurStockModel vol.3

	This READ-ME file is focused on the modification and some new features we made for this assignments. In order to accommodate the changes specified in the assignment, we have made a few modifications as well as add some new features.

	1. StockModel. We add a buyByAmount() method. You can now buy a stock by certain amount. We add a createPercentage() method and you can use it to create a buying plan without buying. We add savePortfolio(), savePercentage(), loadPortfolio(), loadPercentage() methods, which helps you save a portfolio or a buying plan in the same directory as a csv file and load a buying plan or a portfolio from a csv file in the same directory. This is a serialize and deserialize combo. If the portfolio or buying plan's name is already in the system, it will not load the csv.
	
	2. Controller. Now we have two controllers, StockControllerCommand is for command line view and StockController is for gui view. The common methods of them are extracted into several classes in the commands module. The StockController for gui also mainipulates the view. It set up some button action and add listeners for the view. Because it takes in different views in the constructor, I used a builder pattern here.

	3. View. We add several views for the gui. To make it clear and easy to use, one command is done in one window. In the main window you can use the command lines too by typing commands and click "Go". Or you can click other buttons to jump to the command you want. E.g you can click "Create" and jump to the create an empty portfolio window. You can click the "Exit" button to jump back to the main window. In the main window you can click "Exit" to close the program. You can type in parameters and we will do the rest for you. The result will be displayed in the text area on the right. Save and Load need to have the csv file in the same directory as the jar file.

	4 Plot. We also do this extra part by using xchar-3.5.2.jar to help me plot a portfolio's value in the last 12 months. You can click "Determine Value" button, and then type in an existing porfolio's name and click "Plot Value in 12 months". We will plot the portfolio's historic value for you. Please check it out.