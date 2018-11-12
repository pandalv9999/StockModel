package model;

public interface StockModel {
  void createPortfolio(); // should we provide 2 version of this, one with a list that can instantiate a portfolio with certain kinds of stck

  double buy(String code, int shares);

  double determineValue(String date);

  String getPortfolioState();
}
