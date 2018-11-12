package model;

import java.text.DecimalFormat;

public class StockImpl implements Stock {

  private final String code;
  private final int shares;
  private final double averageBuyInPrice;
  private DecimalFormat twoDecimal;

  public StockImpl(String code, int shares, double averageBuyInPrice)
          throws IllegalArgumentException {

    if (code == null || code.isEmpty() || shares <= 0 || averageBuyInPrice < 0.0) {
      throw new IllegalArgumentException("Parameter of creating stock is invalid!");
    }

    this.code = code;
    this.shares = shares;
    this.averageBuyInPrice = averageBuyInPrice;
    this.twoDecimal = new DecimalFormat("###.##"); // does it necessary?
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public int getShares() {
    return this.shares;
  }

  @Override
  public double getAverageBuyInPrice() {
    return Double.valueOf(twoDecimal.format(averageBuyInPrice));
  }
// Should we instantiate a new object rather than change it?
// should use new StockImpl("GOOG", oldshare + newshare, (oldprice*oldshare + newprice*newshare) / (oldshare + newshare)); to instantiate a stock new object
//  @Override
//  public void updateShare(int newShare) {
//    shares += newShare;
//  }
//
//  @Override
//  public void updateAverageBuyInPrice(double newPrice) {
//    averageBuyInPrice = Double.valueOf(twoDecimal.format(newPrice));
//  }

  @Override
  public String getCurrentState() {
    return String.format(
            "Code: %s, Shares: %d, Average Buy-in Price: %.2f", code, shares, averageBuyInPrice);
  }

}
