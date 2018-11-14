package model;

public class MockStockModel implements StockModel {

  private StringBuilder log;

  public MockStockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {
    log.append("create ");
    log.append(portfolioName);
    log.append("\n");
  }

  @Override
  public double buy(String portfolioName, String companyName, int shares, String date)
          throws IllegalArgumentException {
    log.append("buy ");
    log.append(portfolioName);
    log.append(" ");
    log.append(companyName);
    log.append(" ");
    log.append(shares);
    log.append(" ");
    log.append(date);
    log.append(" \n");
    return 0;
  }

  @Override
  public double determineCost(String portfolioName) throws IllegalArgumentException {
    log.append("determineCost ");
    log.append(portfolioName);
    log.append("\n");
    return 0;
  }

  @Override
  public double determineValue(String portfolioName, String date) throws IllegalArgumentException {
    log.append("determineValue ");
    log.append(portfolioName);
    log.append(" ");
    log.append(date);
    log.append("\n");
    return 0;
  }

  @Override
  public String getPortfolioState() {
    log.append("getallstates");
    log.append("\n");
    return null;
  }

  @Override
  public String getPortfolioState(String portfolioName) throws IllegalArgumentException {
    log.append("getPortfolioState ");
    log.append(portfolioName);
    log.append("\n");
    return null;
  }
}
