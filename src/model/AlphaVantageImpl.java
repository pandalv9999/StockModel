package model;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


// Do save the price after calling API once.
public class AlphaVantageImpl implements AlphaVantage {
  private Map<String, String> nameReference; // the map convert company name to its code
  private Map<String, Map<String, Map<String, Double>>> prices; // String is a company name, date is a certain date, list is the open, high, low and close price and volume

  public AlphaVantageImpl() {
    this.nameReference = new HashMap<>();
    this.prices = new HashMap<>();
  }

  private void callAPIToGetPrices(String code) {
    String apiKey = "J2C3I7JL8H090N10";
    URL url = null;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + code + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + code);
    }
    Map<String, Map<String, Double>> res = new HashMap<String, Map<String, Double>>();
    String[] value = output.toString().split("\n");
    String[] type = {"open", "high", "low", "close", "volume"};
    for (int i = 1; i < value.length; i++) {
      String[] row = value[i].split(",");
      res.put(row[0], new HashMap<String, Double>());
      for (int j = 1; j < row.length; j++) {
        res.get(row[0]).put(type[j - 1], Double.parseDouble(row[j]));
      }
    }
    this.prices.put(code, res);
    System.out.println("Return value: ");
    System.out.println(output.toString());
  }

  @Override
  public String searchCode(String companyName) {
    return null;
  }

  @Override
  public Double getOpenPrice(String code, String date) {
    if (!prices.containsKey(code)) {
      callAPIToGetPrices(code);
    }
    return this.prices
            .get(code)
            .get(date)
            .get("open");
  }

  @Override
  public Double getHighPrice(String code, String date) {
    if (!prices.containsKey(code)) {
      callAPIToGetPrices(code);
    }
    return this.prices
            .get(code)
            .get(date)
            .get("high");
  }

  @Override
  public Double getLowPrice(String code, String date) {
    if (!prices.containsKey(code)) {
      callAPIToGetPrices(code);
    }
    return this.prices
            .get(code)
            .get(date)
            .get("low");
  }

  @Override
  public Double getClosePrice(String code, String date) {
    if (!prices.containsKey(code)) {
      callAPIToGetPrices(code);
    }
    return this.prices
            .get(code)
            .get(date)
            .get("close");
  }


  @Override
  public Double getVolume(String code, String date) {
    if (!prices.containsKey(code)) {
      callAPIToGetPrices(code);
    }
    return this.prices
            .get(code)
            .get(date)
            .get("volume");
  }
}
