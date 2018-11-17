package model;

import com.sun.tools.javac.util.List;

/**
 * The interface defines a StockModel where user can do some manipulation of stocks he owns.
 */

public interface StockModel {

  /**
   * The method create a new Portfolio with given name.
   * @param portfolioName The given Portfolio name.
   * @throws IllegalArgumentException If the name is illegal.
   */

  void createPortfolio(String portfolioName) throws IllegalArgumentException;

  double createPortfolio(String portfolioName, List<String> companyName,
                       List<Double> percentage, double amt, String date)
          throws IllegalArgumentException;

  /**
   * The method buy some stock in the market and add them to a designated Portfolio.
   * @param portfolioName The target Portfolio that user whats to put stocks in.
   * @param companyName The company's name of the desire stocks.
   * @param shares The desired number of shares.
   * @param date The date of the stock.
   * @return The total cost of this buy-in.
   * @throws IllegalArgumentException If any argument is illegal.
   */

  double buy(String portfolioName, String companyName,
             int shares, String date) throws IllegalArgumentException;

  /**
   * The method determines the total costs of all stocks in a portfolio.
   * @param portfolioName The desired Portfolio name.
   * @return The total cost (low price) of stocks.
   * @throws IllegalArgumentException If any argument is illegal.
   */

  double determineCost(String portfolioName) throws IllegalArgumentException;

  /**
   * The method determines the value of all stocks in a portfolio at a certain date..
   * @param portfolioName The desired Portfolio name.
   * @param date The date of interests.
   * @return The total value (high price) of stocks.
   * @throws IllegalArgumentException If any argument is illegal.
   */

  double determineValue(String portfolioName, String date) throws IllegalArgumentException;

  /**
   * The method gets the information of all Portfolios.
   * @return The information as String.
   */

  String getPortfolioState();

  /**
   * The method gets the information of a Portfolios.
   * @param portfolioName The name of interested Portfolio
   * @return The information as Strings
   * @throws IllegalArgumentException If the name is invalid.
   */

  String getPortfolioState(String portfolioName) throws IllegalArgumentException;
}
