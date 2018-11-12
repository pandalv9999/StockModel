package model;

public interface StockModel {
  void createPortfolio(String portfolioName); // should we provide 2 version of this, one with a list that can instantiate a portfolio with certain kinds of stck

  double buy(String portfolioName, String companyName, int shares) throws IllegalArgumentException;

  double determineValue(String date);

  String getPortfolioState();
}
