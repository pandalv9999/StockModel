package model;

/**
 * The Interface is an query interface that asks for the information in the Stock Market. It is
 * provided by the AlphaVantage.
 */

public interface AlphaVantage {

  /**
   * The method asks for the code in the stock market for a given company name.
   *
   * @param companyName The company's name.
   * @return The code of the company in the stock market.
   */
  String searchCode(String companyName);

  /**
   * The method gets the open price of a share of the stock of a company in a certain day.
   *
   * @param code The company's code.
   * @param date The date of the prices we want to query.
   * @return The price in form of double.
   * @throws IllegalArgumentException If there is no information of the day.
   */
  Double getOpenPrice(String code, String date) throws IllegalArgumentException;

  /**
   * The method gets the high price of a share of the stock of a company in a certain day.
   *
   * @param code The company's code.
   * @param date The date of the prices we want to query.
   * @return The price in form of double.
   * @throws IllegalArgumentException If there is no information of the day.
   */
  Double getHighPrice(String code, String date) throws IllegalArgumentException;

  /**
   * The method gets the low price of a share of the stock of a company in a certain day.
   *
   * @param code The company's code.
   * @param date The date of the prices we want to query.
   * @return The price in form of double.
   * @throws IllegalArgumentException If there is no information of the day.
   */
  Double getLowPrice(String code, String date) throws IllegalArgumentException;

  /**
   * The method gets the close price of a share of the stock of a company in a certain day.
   *
   * @param code The company's code.
   * @param date The date of the prices we want to query.
   * @return The price in form of double.
   * @throws IllegalArgumentException If there is no information of the day.
   */
  Double getClosePrice(String code, String date) throws IllegalArgumentException;

  /**
   * The method gets the volume of a share of the stock of a company in a certain day.
   *
   * @param code The company's code.
   * @param date The date of the prices we want to query.
   * @return The price in form of double.
   * @throws IllegalArgumentException If there is no information of the day.
   */
  Double getVolume(String code, String date) throws IllegalArgumentException;

  /**
   * This method get a certain type of price of a share in a certain date.
   *
   * @param code      The company's code.
   * @param date      The date of the prices we want to query.
   * @param priceType The price type we want to query.
   * @return The price in form of double.
   */
  double getPrice(String code, String date, String priceType);

}
