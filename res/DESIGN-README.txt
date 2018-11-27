Team 19657 -
Shuomin Wu
Rujun Yao

Assignment 8: OurStockModel vol.2

	This READ-ME file is focused on the modification and some new features we made for this assignments. In order to accommodate the changes specified in the assignment, we have made a few modifications as well as add some new features.

 	1. Stock. The class Stock is our data structure designed to store each stock's information. In the last homework, the number of shares is integers. In this assignments, in order to support the features that we can buy shares by specifying the total amount of money, says, $2000, and buy them in proportions for each company in portfolio, and we have consults the piazza that the double amount of shares is acceptable, we change the int to double. We can now buy 3.5 shares of a company. 

	2. StockModel. In the buy() method, we add a new parameters named priceType. The parameters is used to specify what kind of prices we want for a stock in a day: open, close, high or low. In the previous assignment we only cares about the low price, so all the purchase is defaulted to be the low price. In this assignment, however, close prices is taken more places. So we add a variables. Besides that, we add some new features in the old function. Now if the date is not specified, we will find the closest date of that day, and do the purchase on that day. We also modify the builder, since now we can specify the commission fee for each stock model.

	That is the all modification we have made. The rest are new features that we added, without any modifications. 