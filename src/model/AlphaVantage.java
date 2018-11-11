package model;

public interface AlphaVantage {
  String searchCode(String companyName);

  Double getOpenPrice(String code, String date);

  Double getHighPrice(String code, String date);

  Double getLowPrice(String code, String date);

  Double getClosePrice(String code, String date);

  Double getVolume(String code, String date);
}
