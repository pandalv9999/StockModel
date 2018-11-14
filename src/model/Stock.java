package model;

/**
 * The interface defines the data structure of a Stock. A stock contains its company's code, the
 * number of shares, and its cost when user own the stock.
 */

public interface Stock {

  /**
   * The method gets the company's code of the stock.
   * @return The company's code.
   */

  String getCode();

  /**
   * The method gets the number of shares of the current stock.
   * @return The number of shares.
   */

  int getShares();

  /**
   * The method gets the current average by-in prices for all the share owned for a stock.
   * @return The average buy-in price.
   */

  double getAverageBuyInPrice();

  /**
   * The method prints out the information of a stock by a String.
   * @return The information of current stock.
   */

  String getCurrentState();
}
