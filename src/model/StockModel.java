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
   * This method creates a fixed portfolio with specific buying plan.
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

  /**
   * This method creates a buying plan.
   *
   * @param percentagesName The plan's name.
   * @param information     The information of the buying plan with some companies to buy and their
   *                        percentage.
   * @throws IllegalArgumentException If the name is illegal. If the information goes wrong. If the
   *                                  buying fails.
   */

  void createPercentage(String percentagesName, Map<String, Double> information)
          throws IllegalArgumentException;


  /**
   * This method creates a fixed portfolio with specific buying plan. It will buy after a certain
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
   * The method buys some stock in the market and add them to a designated Portfolio. Modify: add a
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
   * The method buys some stock in the market by certain amount and add them to a designated
   * Portfolio.
   *
   * @param portfolioName The target Portfolio that user whats to put stocks in.
   * @param companyName   The company's name of the desire stocks.
   * @param amount        The desired number of amount to buy.
   * @param date          The date of the stock.
   * @return The total cost of this buy-in.
   */
  double buyByAmount(String portfolioName, String companyName, double amount, String date);

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

  /**
   * This method will save a portfolio to a csv file.
   *
   * @param portfolioName the portfolio you want to save
   * @param fileName      the csv file's name you want to name
   */
  void savePortfolio(String portfolioName, String fileName);

  /**
   * This method will load a portfolio from a csv file.
   *
   * @param fileName the csv file's name you want to load
   */
  void loadPortfolio(String fileName);

  /**
   * This method will save a buying plan to a csv file.
   *
   * @param portfolioName the portfolio you want to save
   * @param fileName      the csv file's name you want to name
   */
  void savePercentage(String portfolioName, String fileName);

  /**
   * This method will load a buying plan from a csv file.
   *
   * @param fileName the csv file's name you want to load
   */
  void loadPercentage(String fileName);

  /**
   * This method will get you a portfolio's value in the last 12 months for plotting.
   *
   * @param portfolioName the portfolio you want to plot
   */
  List<Double> determineValuePlot(String portfolioName) throws IllegalArgumentException;
}
