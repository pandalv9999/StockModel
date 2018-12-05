package model;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StockModelTestPlusPlus {

  private StockModel myModel = StockModelImpl
          .getBuilder()
          .commissionFee(5.0)
          .build();

  @Test
  public void loadPortfolio() {
    myModel.loadPortfolio("a");
    assertEquals("a:\n"
                    + "Code: GOOG, Shares: 200.00, Average Buy-in Price: 1077.88\n"
                    + "Code: AT, Shares: 200.00, Average Buy-in Price: 2.15\n"
                    + "Code: FB, Shares: 200.00, Average Buy-in Price: 137.36\n"
                    + "The commission fee of this portfolio is $15.00\n",
            myModel.getPortfolioState("a"));
    try {
      myModel.loadPortfolio("aasdfasdfasdfasfdsafsfda");
      fail("Cannot find this file.");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot find this file.", e.getMessage());
    }

    try {
      myModel.loadPortfolio("b");
      fail("Commission fee counter should be integer.");
    } catch (IllegalArgumentException e) {
      assertEquals("Commission fee counter should be integer.", e.getMessage());
    }
  }

  @Test
  public void loadPercentage() {
    myModel.loadPercentage("b1");
    myModel.createPortfolio("a");
    myModel.buyByPercentage("a", "b", 10000, "n");
    assertEquals("a:\n"
                    + "Code: GOOG, Shares: 1.90, Average Buy-in Price: 1050.82\n"
                    + "Code: TWTR, Shares: 245.70, Average Buy-in Price: 32.56\n"
                    + "The commission fee of this portfolio is $10.00\n",
            myModel.getPortfolioState("a"));
    try {
      myModel.loadPercentage("aasdfasdfasdfasfdsafsfda");
      fail("Cannot find this file.");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot find this file.", e.getMessage());
    }

    try {
      myModel.loadPercentage("b");
      fail("The sum is not one");
    } catch (IllegalArgumentException e) {
      assertEquals("The sum of all percentage is not one!", e.getMessage());
    }
  }

  private String readFileTestHelper(String fileName) throws IllegalArgumentException {
    StringBuilder state = new StringBuilder();
    String res;
    String line = "";
    try {
      BufferedReader in = new BufferedReader(new FileReader(fileName));
      line = in.readLine();
      while (line != null) {
        state.append(line);
        line = in.readLine();
      }
      in.close();
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot find this file.");
    }
    res = state.toString();
    return res;
  }

  @Test
  public void savePortfolio() {
    myModel.loadPortfolio("a");
    myModel.savePortfolio("a", "a2");
    assertEquals("a,GOOG,200.0,1077.88,AT,200.0,2.15,FB,200.0,137.36,3",
            readFileTestHelper("a2.csv"));
  }

  @Test
  public void savePercentage() {
    myModel.loadPercentage("b1");
    myModel.savePercentage("b", "b2");
    assertEquals("b,goog,0.2,twitter,0.8,",
            readFileTestHelper("b2.csv"));
  }

  @Test
  public void createPercentage() {
    Map information = new HashMap();
    information.put("goog", 0.2);
    information.put("twitter", 0.8);
    myModel.createPercentage("b", information);
    myModel.createPortfolio("a");
    myModel.buyByPercentage("a", "b", 10000, "n");
    assertEquals("a:\n"
                    + "Code: GOOG, Shares: 1.90, Average Buy-in Price: 1050.82\n"
                    + "Code: TWTR, Shares: 245.70, Average Buy-in Price: 32.56\n"
                    + "The commission fee of this portfolio is $10.00\n",
            myModel.getPortfolioState("a"));
  }


  @Test
  public void buyByAmount() {
    myModel.createPortfolio("a");
    myModel.buyByAmount("a", "goog", 10000,
            "2018-11-29");
    assertEquals("a:\n"
                    + "Code: GOOG, Shares: 9.29, Average Buy-in Price: 1076.00\n"
                    + "The commission fee of this portfolio is $5.00\n",
            myModel.getPortfolioState("a"));
    try {
      myModel.buyByAmount("a", "goog", -10000,
              "2018-11-29");
      fail("The shares to buy must be positive");
    } catch (IllegalArgumentException e) {
      assertEquals("The shares to buy must be positive", e.getMessage());
    }
    try {
      myModel.buyByAmount("sdfasdfsdf", "goog", 10000,
              "2018-11-29");
      fail("The portfolio is not yet created!");
    } catch (IllegalArgumentException e) {
      assertEquals("The portfolio is not yet created!", e.getMessage());
    }

  }


  @Test
  public void determineValuePlot() {
    myModel.createPortfolio("a");
    myModel.buyByAmount("a", "goog", 10000,
            "2018-11-29");
    List res = myModel.determineValuePlot("a");
    assertEquals(12, res.size());
  }
}