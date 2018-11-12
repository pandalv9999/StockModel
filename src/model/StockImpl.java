package model;

import java.text.DecimalFormat;

public class StockImpl implements Stock {

  private final String code;
  private int shares;
  private double averageBuyInPrice;
  private DecimalFormat twoDecimal;

  public StockImpl(String code, int shares) {
    this.code = code;
    this.shares = shares;
    this.twoDecimal = new DecimalFormat("###.##");
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

  @Override
  public void updateShare(int newShare) {
    shares += newShare;
  }

  @Override
  public void updateAverageBuyInPrice(double newPrice) {
    averageBuyInPrice = Double.valueOf(twoDecimal.format(newPrice));
  }

  @Override
  public String getCurrentState() {
    return String.format(
            "Company: %s, Shares: %d, Average Buy-in Price: %.2f", code, shares, averageBuyInPrice);
  }

}
