package model;

public class StockImpl implements Stock {

  private final String code;
  private int shares;
  private double averageBuyInPrice;

  public StockImpl(String code, int shares) {
    this.code = code;
    this.shares = shares;
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
    return averageBuyInPrice;
  }

  @Override
  public void updateShare(int newShare) {
    shares += newShare;
  }

  @Override
  public void updateAverageBuyInPrice(double newPrice) {
    averageBuyInPrice = newPrice;
  }

  @Override
  public String getCurrentState() {
    return String.format(
            "Company: %s, Shares: %d, Average Buy-in Price: %f", code, shares, averageBuyInPrice);
  }

}
