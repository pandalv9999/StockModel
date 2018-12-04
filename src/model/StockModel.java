package model;

import java.util.List;
import java.util.Map;

/**
 * The interface defines a StockModel where user can do some manipulation of stocks he owns.
 */

public interface StockModel {

  /**
   * The method create a new empty Portfolio with given name.
   *
   * @param portfolioName The given Portfolio name.
   * @throws IllegalArgumentException If the name is illegal.
   */

  void createPortfolio(String portfolioName) throws IllegalArgumentException;


  /**
   * This method create a fixed portfolio with specific buying plan.
   *
   * @param portfolioName The given Portfolio name.
   * @param information   The information of the buying plan with some companies to buy and their
   *                      percentage.
   * @param amt           The amount of money you want to buy.
   * @param date          The date you want to buy this portfolio.
   * @return the total cost of current operation.
   * @throws IllegalArgumentException If the name is illegal. If the information goes wrong. If the
   *                                  buying fails.
   */

  double createPortfolio(String portfolioName, Map<String, Double> information,
                         double amt, String date)
          throws IllegalArgumentException;


  void createPercentage(String percentagesName, Map<String, Double> information)
          throws IllegalArgumentException;


  /**
   * This method create a fixed portfolio with specific buying plan. It will buy after a certain
   * period of time. If a date is not available, it will automatically search for a nearest
   * available date.
   *
   * @param portfolioName The given Portfolio name.
   * @param information   The information of the buying plan with some companies to buy and their
   *                      percentage.
   * @param amt           The amount of money you want to buy.
   * @param startDate     The starting date you want to buy this portfolio.
   * @param endDate       The ending date you want to buy this portfolio.
   * @param interval      The interval of two dates of buying.
   * @return the total cost of current operation.
   * @throws IllegalArgumentException If the name is illegal. If the information goes wrong. If the
   *                                  buying fails.
   */

  double dollarCostAverage(String portfolioName, Map<String, Double> information,
                           double amt, String startDate, String endDate, int interval)
          throws IllegalArgumentException;

  /**
   * The method buy some stock in the market and add them to a designated Portfolio. Modify: add a
   * parameter name priceType.
   *
   * @param portfolioName The target Portfolio that user whats to put stocks in.
   * @param companyName   The company's name of the desire stocks.
   * @param shares        The desired number of shares.
   * @param date          The date of the stock.
   * @param priceType     The type of price.
   * @return The total cost of this buy-in.
   * @throws IllegalArgumentException If any argument is illegal.
   */

  double buy(String portfolioName, String companyName,
             double shares, String date, String priceType) throws IllegalArgumentException;

  /**
   * The method determines the total costs of all stocks in a portfolio.
   *
   * @param portfolioName The desired Portfolio name.
   * @return The total cost (low price) of stocks.
   * @throws IllegalArgumentException If any argument is illegal.
   */

  double determineCost(String portfolioName) throws IllegalArgumentException;

  /**
   * The method determines the value of all stocks in a portfolio at a certain date..
   *
   * @param portfolioName The desired Portfolio name.
   * @param date          The date of interests.
   * @return The total value (high price) of stocks.
   * @throws IllegalArgumentException If any argument is illegal.
   */

  double determineValue(String portfolioName, String date) throws IllegalArgumentException;

  /**
   * This method will determine the commission fee of a certain portfolio.
   *
   * @param portfolioName The portfolio's name.
   * @return The commission fee of this portfolio.
   */

  double determineCommissionFee(String portfolioName);

  /**
   * This method will buy a fixed portfolio with an amount of money on a certain date.
   *
   * @param portfolioName The portfolio's name.
   * @param amt           The amount of money you want to buy.
   * @param date          The date you want to buy.
   * @return The total cost (low price) of stocks.
   * @throws IllegalArgumentException If any argument is illegal.
   */

  double buyByPercentage(String portfolioName, String percentagesName, double amt, String date)
          throws IllegalArgumentException;

  double buyByAmount(String portfolioName, String companyName, double amount, String date);

  /**
   * The method gets the information of all Portfolios.
   *
   * @return The information as String.
   */

  String getPortfolioState();

  /**
   * The method gets the information of a Portfolios.
   *
   * @param portfolioName The name of interested Portfolio
   * @return The information as Strings
   * @throws IllegalArgumentException If the name is invalid.
   */

  String getPortfolioState(String portfolioName) throws IllegalArgumentException;

  void savePortfolio(String portfolioName, String fileName);

  void loadPortfolio(String fileName);

  void savePercentage(String portfolioName, String fileName);

  void loadPercentage(String fileName);

  List<Double> determineValuePlot(String portfolioName) throws IllegalArgumentException;
}
