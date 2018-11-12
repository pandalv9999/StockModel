package model;

import java.util.Date;
import java.util.List;

// still confused
public interface StockModel {
  List<Stock> createPortfolio();

  void buy(List<Stock> transactions);

  int checkValue(List<Stock> transactions, Date date);
}
