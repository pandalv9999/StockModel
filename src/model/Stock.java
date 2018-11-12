package model;

public interface Stock {

  String getCode();

  int getShares();

  double getAverageBuyInPrice();

  void updateShare(int newShare);

  void updateAverageBuyInPrice(double newPrice);

  String getCurrentState();
}
