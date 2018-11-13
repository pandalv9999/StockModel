package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StockModelTest {

  private AlphaVantage alphaVantage;
  private StockModel myModel;

  private double price1;
  private double price2;
  private double price3;
  private double price4;

  @Before
  public void setUp() {
    alphaVantage = new AlphaVantageImpl();
    myModel = new StockModelImpl();
    myModel.createPortfolio("First Portfolio");
    myModel.createPortfolio("Second Portfolio");

    price1 = myModel.buy("First Portfolio", "Google", 100, "2018-05-30");
    price2 = myModel.buy("First Portfolio", "MicroSoft", 300, "2018-05-30");
    price3 = myModel.buy("First Portfolio", "Google", 150, "2018-06-04");
    price4 = myModel.buy("Second Portfolio", "MicroSoft", 200, "2018-05-29");

  }

  @Test
  public void buy() {

    assertEquals(alphaVantage.getLowPrice("GOOG", "2018-05-30") * 100, price1, 0.001);
    assertEquals(alphaVantage.getLowPrice("MSFT", "2018-05-30") * 300, price2, 0.001);
    assertEquals(alphaVantage.getLowPrice("GOOG", "2018-06-04") * 150, price3, 0.001);
    assertEquals(alphaVantage.getLowPrice("MSFT", "2018-05-29") * 200, price4, 0.001);
    System.out.printf("%f, %f, %f, %f", price1, price2, price3, price4);
  }

  @Test
  public void determineCost() {
    //assertEquals();
  }

  @Test
  public void determineValue() {
  }

  @Test
  public void getPortfolioState() {
  }

  @Test
  public void getPortfolioState1() {
  }
}