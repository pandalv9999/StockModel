package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StockModelTest {

  private AlphaVantageImpl alphaVantage;
  private StockModel myModel = StockModelImpl
          .getBuilder()
          .commissionFee(0.0)
          .build();

  private double price1;
  private double price2;
  private double price3;
  private double price4;

  @Before
  public void setUp() {

    alphaVantage = AlphaVantageImpl.getInstance();

    myModel.createPortfolio("First Portfolio");
    myModel.createPortfolio("Second Portfolio");

    price1 = myModel.buy("First Portfolio", "Google",
            100, "2018-05-30");
    price2 = myModel.buy("First Portfolio", "MicroSoft",
            300, "2018-05-30");
    price3 = myModel.buy("First Portfolio", "Google",
            150, "2018-06-04");
    price4 = myModel.buy("Second Portfolio", "MicroSoft",
            200, "2018-05-29");

  }

  @Test
  public void buy() {

    try {
      myModel.createPortfolio("First Portfolio");
      fail("Existing name");
    } catch (IllegalArgumentException e) {
      //do nothing
    }

    assertEquals(alphaVantage.getLowPrice("GOOG", "2018-05-30") * 100,
            price1, 0.001);
    assertEquals(alphaVantage.getLowPrice("GOOG", "2018-06-04") * 150,
            price3, 0.001);
    assertEquals(alphaVantage.getLowPrice("MSFT", "2018-05-30") * 300,
            price2, 0.001);
    assertEquals(alphaVantage.getLowPrice("MSFT", "2018-05-29") * 200,
            price4, 0.001);
  }

  @Test
  public void determineCost() {
    assertEquals(price1 + price2 + price3,
            myModel.determineCost("First Portfolio"), 0.001);
    assertEquals(price4, myModel.determineCost("Second Portfolio"), 0.01);

    try {
      myModel.determineCost("");
      fail("shall fail");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
  }

  @Test
  public void determineValue() {
    double value1 = alphaVantage.getHighPrice("GOOG", "2018-11-06") * 250
            + alphaVantage.getHighPrice("MSFT", "2018-11-06") * 300;
    assertEquals(value1, myModel.determineValue("First Portfolio",
            "2018-11-06"), 0.001);

    try {
      myModel.determineValue("ewe", "");
      fail("shall fail");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
  }

  @Test
  public void getPortfolioState() {
    assertEquals("First Portfolio:\n"
            + "Code: MSFT, Shares: 300, Average Buy-in Price: 97.91\n"
            + "Code: GOOG, Shares: 250, Average Buy-in Price: 1095.94\n"
            + "\n"
            + "Second Portfolio:\n"
            + "Code: MSFT, Shares: 200, Average Buy-in Price: 97.23\n"
            + "\n", myModel.getPortfolioState());
  }

  @Test
  public void getPortfolioState1() {
    assertEquals("First Portfolio:\n"
                    + "Code: MSFT, Shares: 300, Average Buy-in Price: 97.91\n"
                    + "Code: GOOG, Shares: 250, Average Buy-in Price: 1095.94\n",
            myModel.getPortfolioState("First Portfolio"));
    assertEquals("Second Portfolio:\n"
                    + "Code: MSFT, Shares: 200, Average Buy-in Price: 97.23\n",
            myModel.getPortfolioState("Second Portfolio"));

    try {
      myModel.getPortfolioState("");
      fail("shall fail");
    } catch (IllegalArgumentException e) {
      //do nothing
    }

  }
}