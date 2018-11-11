package model;

import java.util.Date;

public interface AlphaVantage {
  String searchCode(String companyName);

  String getHighPrice(String code, Date date);

  String getLowPrice(String code, Date date);
  
  String getClosePrice(String code, Date date);
}
