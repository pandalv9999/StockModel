package model;

public class SingleTransactionImpl implements SingleTransaction {
  private final String code;
  private final int shares;

  public SingleTransactionImpl(String code, int shares) {
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
}
