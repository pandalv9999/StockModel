package model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StockModelTestPlus {

  private StockModel myModel = StockModelImpl
          .getBuilder()
          .commissionFee(5.0)
          .build();
  private Map<String, Double> information = new HashMap<>();

  @Before
  public void setUp() {
    information.put("Google", 0.3);
    information.put("Microsoft", 0.7);
  }

  @Test
  public void createPortfolio() {
    double cost = myModel.createPortfolio("1", information, 50000,
            "2018-09-13");
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
  public void dollarCostAverageAndCommissionFee() {
    double cost = myModel.dollarCostAverage("2", information,
            30000.0, "2016-09-13", "2018-09-21", 30);
    assertEquals(30000 * 25, cost, 0.01);
    assertEquals(25 * 3 * 5, myModel.determineCommissionFee("2"), 0.01);
    System.out.println(myModel.getPortfolioState("2"));
  }


  @Test
  public void buyByPercentage() {
    double cost = myModel.createPortfolio("1", information, 50000,
            "2018-09-13");
    assertEquals(50000, cost, 0.01);

    assertEquals("1:\n"
                    + "Code: MSFT, Shares: 309.98, Average Buy-in Price: 112.91\n"
                    + "Code: GOOG, Shares: 12.76, Average Buy-in Price: 1175.33\n"
                    + "The commission fee of this portfolio is $10.00\n",
            myModel.getPortfolioState("1"));

    myModel.buyByPercentage("1", "1", 2000, "2018-11-15");
    assertEquals("1:\n"
                    + "Code: MSFT, Shares: 323.03, Average Buy-in Price: 112.68\n"
                    + "Code: GOOG, Shares: 13.33, Average Buy-in Price: 1170.65\n"
                    + "The commission fee of this portfolio is $20.00\n",
            myModel.getPortfolioState("1"));
  }
}