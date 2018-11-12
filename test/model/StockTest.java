package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StockTest {

  private Stock googleStock;
  private Stock microsoftStock;

  @Before
  public void setUp() {
    googleStock = new StockImpl("GOOG", 100, 30.666454);
    microsoftStock = new StockImpl("MSFT", 250, 7326.331122);
  }

  @Test
  public void testInvalidConstructor() {

    try {
      new StockImpl("", 22, 33.5);
      fail("Current test shall fails");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }

    try {
      new StockImpl("RRR", -133, 33.5);
      fail("Current test shall fails");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }

    try {
      new StockImpl("RRR", 222, -333.0);
      fail("Current test shall fails");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }

  }

  @Test
  public void getCode() {
    assertEquals("GOOG", googleStock.getCode());
    assertEquals("MSFT", microsoftStock.getCode());
  }

  @Test
  public void getShares() {
    assertEquals(100, googleStock.getShares());
    assertEquals(250, microsoftStock.getShares());
  }

  @Test
  public void getAverageBuyInPrice() {
    assertEquals(30.67, googleStock.getAverageBuyInPrice(), 0.0001);
    assertEquals(7326.33, microsoftStock.getAverageBuyInPrice(), 0.00001);
  }

  @Test
  public void getCurrentState() {
    assertEquals("Code: GOOG, Shares: 100, Average Buy-in Price: 30.67",
            googleStock.getCurrentState());
    assertEquals("Code: MSFT, Shares: 250, Average Buy-in Price: 7326.33",
            microsoftStock.getCurrentState());
  }
}