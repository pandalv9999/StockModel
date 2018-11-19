package model;

public class StockImpl implements Stock {

  private final String code;
  private final double shares;
  private final double averageBuyInPrice;

  /**
   * The method construct a new stock with given company's code, shares, and the average price.
   *
   * @param code              The code of the company.
   * @param shares            Number of shares he owned in a stock.
   * @param averageBuyInPrice The average price of all shares.
   * @throws IllegalArgumentException If any argument is illegal.
   */

  public StockImpl(String code, double shares, double averageBuyInPrice)
          throws IllegalArgumentException {

    if (code == null || code.isEmpty() || shares <= 0 || averageBuyInPrice < 0.0) {
      throw new IllegalArgumentException("Parameter of creating stock is invalid!");
    }

    this.code = code;
    this.shares = shares;
    this.averageBuyInPrice = averageBuyInPrice;
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public double getShares() {
    return this.shares;
  }

  @Override
  public double getAverageBuyInPrice() {
    return averageBuyInPrice;
  }

  @Override
  public String getCurrentState() {
    return String.format(
            "Code: %s, Shares: %.2f, Average Buy-in Price: %.2f", code, shares, averageBuyInPrice);
  }

}
