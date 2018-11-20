package model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StockModelTestPlus {

  private AlphaVantage av;
  private StockModel myModel = StockModelImpl
          .getBuilder()
          .commissionFee(5.0)
          .build();
  private  Map<String, Double> information = new HashMap<>();

  @Before
  public void setUp(){
    av = AlphaVantageImpl.getInstance();
    information.put("Google", 0.3);
    information.put("Microsoft", 0.4);
    information.put("Apple", 0.3);
  }

  @Test
  public void createPortfolio() {
    double cost = myModel.createPortfolio("1", information, 50000, "2018-09-13");
    assertEquals(50000, cost, 0.01);
    assertEquals("1:\n"
            + "Code: MSFT, Shares: 177.13, Average Buy-in Price: 112.91\n"
            + "Code: GOOG, Shares: 12.76, Average Buy-in Price: 1175.33\n"
            + "Code: AAPL, Shares: 66.25, Average Buy-in Price: 226.41\n"
            + "The commission fee of this portfolio is $15.00",
            myModel.getPortfolioState("1"));

    Map<String, Double> wrong = new HashMap<>();
    wrong.put("323", 0.7);
    wrong.put("ewe", 0.8);

    try {
      myModel.createPortfolio("2", wrong, 5000, "2018-01-01");
      fail("Should failed");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }
  }

  @Test
  public void dollarCostAverage() {
  }

  @Test
  public void determineCommissionFee() {
  }

  @Test
  public void buyByPercentage() {
  }
}