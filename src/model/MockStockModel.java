package model;


import java.util.Map;

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
  public double createPortfolio(String portfolioName,Map<String, Double> information,
                                double amt, String date) throws IllegalArgumentException {
    log.append("create fixed");
    log.append("\n");
    log.append(portfolioName);
    log.append("\n");
    log.append(information.keySet().toString());
    log.append("\n");
    log.append(information.values().toString());
    log.append("\n");
    log.append(Double.toString(amt));
    log.append("\n");
    log.append(date);
    log.append("\n");
    return 0.0;
  }

  @Override
  public double dollarCostAverage(String portfolioName, Map<String, Double> information,
                                  double amt, String startDate,
                                  String endDate, int interval) throws IllegalArgumentException {
    log.append("create fixed ongoing\n");
    log.append(portfolioName);
    log.append("\n");
    log.append(information.keySet().toString());
    log.append("\n");
    log.append(information.values().toString());
    log.append("\n");
    log.append(Double.toString(amt));
    log.append("\n");
    log.append(startDate);
    log.append("\n");
    log.append(endDate);
    log.append("\n");
    log.append(interval);
    log.append("\n");
    return 0;

  }

  @Override
  public double buy(String portfolioName, String companyName,
             double shares, String date, String priceType) throws IllegalArgumentException {
    log.append("buy ");
    log.append(portfolioName);
    log.append(" ");
    log.append(companyName);
    log.append(" ");
    log.append(shares);
    log.append(" ");
    log.append(date);
    log.append(" ");
    log.append(priceType);
    log.append("\n");
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
  public double determineCommissionFee(String portfolioName) {
    log.append("determineFee ");
    log.append(portfolioName);
    log.append("\n");
    return 0;
  }

  @Override
  public double buyByPercentage(String portfolioName, double amt, String date) {
    log.append("buyByPercentage ");
    log.append(portfolioName);
    log.append(" ");
    log.append(Double.toString(amt));
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
