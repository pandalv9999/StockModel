package model;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * The class implements the AlphaVantage Interface. The class save all data once the API is called.
 */

public class AlphaVantageImpl implements AlphaVantage {

  private Map<String, String> nameReference; // the map convert company name to its code
  private Map<String, Map<String, Map<String, Double>>> prices; // String is a company name, date is a certain date, list is the open, high, low and close price and volume
  private String[] APIKeys = {"J2C3I7JL8H090N10", "Q3VB628VO7MT9GBK", "QIDZM1CKRMP31G3S",
          "7Q072N49XLJCL76Z", "6PK36VERE89B8KA8"};
  private int keyCount;

  private static AlphaVantageImpl uniqueInstance;

  private AlphaVantageImpl() {
    this.nameReference = new HashMap<>();
    this.prices = new HashMap<>();
    this.keyCount = 1;
  }

  public static AlphaVantageImpl getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new AlphaVantageImpl();
    }
    return uniqueInstance;
  }

  private String getAPIKey() {
    String res = APIKeys[this.keyCount];
    this.keyCount += 1;
    if (this.keyCount == this.APIKeys.length) {
      this.keyCount = 0;
    }
    return res;
  }

  private void callAPIToGetPrices(String code) {
    // System.out.println(code);
    String apiKey = getAPIKey();
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
//    System.out.println("Return value: ");
//    System.out.println(output.toString());
  }

  private void callAPIToGetCode(String companyName) {
    String apiKey = getAPIKey();
    URL url = null;

    try {
      url = new URL("https://www.alphavantage.co/query?function=SYMBOL_SEARCH&"
              + "keywords=" + companyName
              + "&apikey=" + apiKey
              + "&datatype=csv");
//      System.out.println(url.toString());
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
      throw new IllegalArgumentException("No code data found for " + companyName);
    }

    String[] value = output.toString().split("\n");
    if (value.length <= 1) {
      throw new IllegalArgumentException("No code data found for " + companyName);
    }
    String[] row = value[1].split(",");
    this.nameReference.put(companyName, row[0]); // Use the first search result as its code.
  }

  @Override
  public String searchCode(String companyName) {
    if (!this.nameReference.containsKey(companyName)) {
      callAPIToGetCode(companyName);
    }
    return this.nameReference.get(companyName);
  }

  @Override
  public Double getOpenPrice(String code, String date) throws IllegalArgumentException {
    if (!prices.containsKey(code)) {
      callAPIToGetPrices(code);
    }
    Map<String, Map<String, Double>> res = this.prices.get(code);
    Double res2 = 0.0;
    try {
      res2 = res
              .get(date)
              .get("open");
    } catch (Exception e) {
      throw new IllegalArgumentException("There is no information on this date.");
    }
    return res2;
  }

  @Override
  public Double getHighPrice(String code, String date) throws IllegalArgumentException {
    if (!prices.containsKey(code)) {
      callAPIToGetPrices(code);
    }
    Map<String, Map<String, Double>> res = this.prices.get(code);
    Double res2 = 0.0;
    try {
      res2 = res
              .get(date)
              .get("high");
    } catch (Exception e) {
      throw new IllegalArgumentException("There is no information on this date.");
    }
    return res2;
  }

  @Override
  public Double getLowPrice(String code, String date) throws IllegalArgumentException {
    if (!prices.containsKey(code)) {
      callAPIToGetPrices(code);
    }
    Map<String, Map<String, Double>> res = this.prices.get(code);
    Double res2 = 0.0;
    try {
      res2 = res
              .get(date)
              .get("low");
    } catch (Exception e) {
      throw new IllegalArgumentException("There is no information on this date.");
    }
    return res2;
  }

  @Override
  public Double getClosePrice(String code, String date) throws IllegalArgumentException {
    if (!prices.containsKey(code)) {
      callAPIToGetPrices(code);
    }
    Map<String, Map<String, Double>> res = this.prices.get(code);
    Double res2 = 0.0;
    try {
      res2 = res
              .get(date)
              .get("close");
    } catch (Exception e) {
      throw new IllegalArgumentException("There is no information on this date.");
    }
    return res2;
  }


  @Override
  public Double getVolume(String code, String date) throws IllegalArgumentException {
    if (!prices.containsKey(code)) {
      callAPIToGetPrices(code);
    }

    Map<String, Map<String, Double>> res = this.prices.get(code);
    Double res2 = 0.0;
    try {
      res2 = res
              .get(date)
              .get("volume");
    } catch (Exception e) {
      throw new IllegalArgumentException("There is no information on this date.");
    }
    return res2;
  }
}
