package model;


import java.util.List;
import java.util.Map;

/**
 * This is a mock model for the testing purpose.
 */

public class MockStockModel implements StockModel {

  private StringBuilder log;

  public MockStockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public double buyByAmount(String portfolioName, String companyName, double amount, String date) {
    log.append("buya ");
    log.append(portfolioName);
    log.append(" ");
    log.append(companyName);
    log.append(" ");
    log.append(amount);
    log.append(" ");
    log.append(date);
    log.append("\n");
    return 0.0;
  }

  @Override
  public void createPercentage(String percentagesName, Map<String, Double> information)
          throws IllegalArgumentException {

    log.append("createp ");
    log.append(percentagesName);
    log.append(" ");
    log.append(information.toString());
    log.append("\n");
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {
    log.append("create ");
    log.append(portfolioName);
    log.append("\n");
  }

  @Override
  public double createPortfolio(String portfolioName, Map<String, Double> information,
                                double amt, String date) throws IllegalArgumentException {
    log.append("create fixed");
    log.append(" ");
    log.append(portfolioName);
    log.append(" ");
    log.append(information.keySet().toString());
    log.append(" ");
    log.append(information.values().toString());
    log.append(" ");
    log.append(Double.toString(amt));
    log.append(" ");
    log.append(date);
    log.append(" ");
    return 0.0;
  }

  @Override
  public double dollarCostAverage(String portfolioName, Map<String, Double> information,
                                  double amt, String startDate,
                                  String endDate, int interval) throws IllegalArgumentException {
    log.append("create fixed ongoing\n");
    log.append(portfolioName);
    log.append(" ");
    log.append(information.keySet().toString());
    log.append(" ");
    log.append(information.values().toString());
    log.append(" ");
    log.append(Double.toString(amt));
    log.append(" ");
    log.append(startDate);
    log.append(" ");
    log.append(endDate);
    log.append(" ");
    log.append(interval);
    log.append(" ");
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
  public double buyByPercentage(String portfolioName, String percentagesName, double amt,
                                String date) {
    log.append("buyByPercentage ");
    log.append(portfolioName);
    log.append(" ");
    log.append(percentagesName);
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

  @Override
  public void savePortfolio(String portfolioName, String fileName) {
    log.append("savePortfolio ");
    log.append(portfolioName);
    log.append(" ");
    log.append(fileName);
    log.append("\n");
  }

  @Override
  public void loadPortfolio(String fileName) {
    log.append("loadPortfolio ");
    log.append(fileName);
    log.append("\n");
  }

  @Override
  public void savePercentage(String percentageName, String fileName) {
    log.append("savePercentage ");
    log.append(percentageName);
    log.append(" ");
    log.append(fileName);
    log.append("\n");
  }

  @Override
  public void loadPercentage(String fileName) {
    log.append("loadPercentage ");
    log.append(fileName);
    log.append("\n");
  }

  @Override
  public List<Double> determineValuePlot(String portfolioName) throws IllegalArgumentException {
    log.append("determineValuePlot ");
    log.append(portfolioName);
    log.append("\n");
    return null;
  }
}
