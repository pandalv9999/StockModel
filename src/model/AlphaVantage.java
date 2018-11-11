package model;

import java.util.Date;

public interface AlphaVantage {
  String checkCode(String companyName);

  String checkPrice(String code, Date date);
}
