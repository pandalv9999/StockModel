package model;

public interface StockModel {

  void createPortfolio(String portfolioName) throws IllegalArgumentException; // should we provide 2 version of this, one with a list that can instantiate a portfolio with certain kinds of stck

  double buy(String portfolioName, String companyName,
             int shares, String date) throws IllegalArgumentException;

  double determineCost(String portfolioName) throws IllegalArgumentException;

  double determineValue(String portfolioName, String date) throws IllegalArgumentException;

  String getPortfolioState();

  String getPortfolioState(String portfolioName) throws IllegalArgumentException;
}
