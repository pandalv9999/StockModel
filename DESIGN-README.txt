Team 19657 -
Shuomin Wu
Rujun Yao

Assignment 8: OurStockModel vol.1

   Introduction. 

	Gone are the days when one could put money in a bank account and see it grow with interest. One of the very few ways to grow money these days is to invest in the stock market. Such investments can be found virtually everywhere: retirement accounts, college savings plans, health savings accounts, etc. To be successful, one must understand what stock is and how it is traded.

	However it is intimidating for a newbie to invest in the stock market. Unlike a savings account, it is possible to lose money in the stock market, in pursuit of greater growth. In order to invest in the stock market, one must have a basic understanding of how stocks work, how investments work, and how to strategize investment. There is no dearth of books, blogs and articles that give investment advice. This advice ranges from simple invest-and-forget ideas to devoting hours each day looking at data and charts. One could hire professional help (a financial broker), but that costs more money.

	OurStockModel is such a model that can stimulate the real-time stock market trades and gives a better understanding to the world of stock market. By creating portfolios, buying or selling stocks, and observing the changes of prices at different time of the day, one will be more prepared to face the various of challenges and difficulties in the real stock market. 

	Basic Functionality.

	As the first and rudimental version of the software, OurStockModel does not implements some fancy interface or methods that could do great stuffs. Instead, we only have some easy and straightforward interfaces and methods to basically satisfy customers need. In short, OurStockModel vol.1 has the following functionality.

	1. It allows users to create Portfolios. A portfolios is a combinations of different stocks, therefore creating one or more portfolios. After a portfolio is created, user can then add or sells stocks insides the portfolio. Besides that, It provides a method to let the user to examine the compositions of one portfolio, or all of the portfolios he or she has created. 

	2. Buy shares of some stock in a portfolio worth a certain amount at a certain date. Since our program is only a stimulator, it allows user to buy stocks in the past. However in reality, buying stocks can only happens in the present. For education purpose, buying stocks in the past can give the rookie stocker a sense of how the stock market works, and gives them the insight of a specific company's trend.

	3. Determine the total cost basis and total value of a portfolio at a certain date. The core of stocks is to buy the stock when its price are low and sells them all when the price is high. Therefore, our model provides methods to determine the total cost of all stocks in the portfolio and the value of those stock at a certain date. Again since it is a simulator, it allows user to determine the value in the past. 

	Besides that, our program offers a CLI like view for the user to interact with the controller. The controller will call models in the right manner according what user's input has the controller received. 

	Design

	The design of our project, with the description on the class diagram, can be interpreted as the follows. 

	1. StockController. The interface is the controller of the class. The controller reads users input in a CLI pattern, read string, perform actions, etc.

	2. AlphaVantage. The interface is our API to get the information of stocks online. The implementation is based on the demo java file provided. To get around of the issue that one can only acquire information five times per minute, we have five different that are working to extends the number to be 25 times per minute. When we have related information, we use maps to stores the information, so that in future function call we will not have to take query again. Beside that, we use a Singleton design pattern to ensures that only one instance of the object will existed. Doing so can avoid multiple querying of the API.

	3. MockStockModel. This model is to test the correctness of the controller. 

	4. Stock. This is the interface we designed to store the information of each stock. A stock has its company code, number of shares, and the current average buy-in value. The public methods are all getter to acquire related informations. And the interface also provides way to output the information of a stock in String. 

	5. StockModel and StockModelBuilder. This is the main model of our project. Currently the class has implemented all the functionality mentioned above. It can create a new portfolios with given name. All of the portfolios are stores in a map. The map's key is String, the name of the portfolio, and the map's value is another map, a map of string and Stock. The inner map's key are the company code of the Stock, and the stock, which is mentioned before, is the data structure to store values. The model offers a method to buy a stock. The method takes portfolio name, company name, number of shares and the date as parameters. The method basically use the API to get the code of a company, and use the API to find the specific price of the company's stock at the given day. Then, the method will check whether the user has the current company's stock. It is has, it recalculate the by-in price with old shares, new shares, old buy-in prices and new price, and create a new stock with new shares, new buy-in prices. Then the method delete the old entry and put the new stock in. If the portfolio do not contain the stock of the company, it simply create a new stock and put it int the portfolio. The method returns the total price of this buy. DetermineCost use map reduce to calculate the sum of all average buy-in price of stocks in the portfolio, and the DetermineValue use map reduce, as well as the api to calculate the value of all stocks at a certain date. Note that when we buy stocks, we use low price, and we check values, we use high price. Then there are two version of getPortfolioState. The one with parameters is to get the state of a specific portfolio, the one without parameters simply get the state of all portfolios. Since now we do not need parameters to construct the model, the builder that has no content is sufficient. Later on we will add somethings to it, if necessary.

	Conclusion.

	In conclusion, the hardest part of the assignment is to figure out what we have to do. Once you got a map in your head the rest will be very easy. We have anticipated some future tasks to be done on the model, such as adding a sell method, get information of different users, ..., and we try to make our designs closer to modification. Overall, the assignment, although abstract, are a examination of what we have learnt in the course of program design paradigm.




