package model;

import java.util.Date;
import java.util.List;

public interface StockModel {
  List<SingleTransaction> createPortfolio();

  void buy(List<SingleTransaction> transactions);

  int checkValue(List<SingleTransaction> transactions, Date date);
}
