package model;

import org.junit.Before;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class AlphaVantageImplTest {

  AlphaVantageImpl a;

  @Before
  public void setUp() {
    a = AlphaVantageImpl.getInstance();
  }

  @org.junit.Test
  public void searchCode() {
    try {
      a.searchCode("asdf");
      fail("There is no code for this company.");
    } catch (IllegalArgumentException e) {
      assertEquals("No code data found for asdf", e.getMessage());
    }
    assertEquals("GOOG", a.searchCode("Google"));
    assertEquals("MSFT", a.searchCode("microsoft"));
  }

  @org.junit.Test
  public void getOpenPrice() {
    // Test getting an open price.
    double price = a.getOpenPrice("GOOG", "2018-11-09");
    assertEquals(1073.99, price, 0.01);
    price = a.getPrice("GOOG", "2018-11-09", "open");
    assertEquals(1073.99, price, 0.01);
    try {
      a.getHighPrice("asdfasdfasdf", "2018-11-09");
      fail("Invalid code.");
    } catch (IllegalArgumentException e) {
      // This should be empty.
    }

    try {
      a.getHighPrice("GOOG", "2018-11-04");
      fail("There is no information on this date.");
    } catch (IllegalArgumentException e) {
      // This should be empty.
    }
  }

  @org.junit.Test
  public void getHighPrice() {
    // Test getting a high price.
    Double price = a.getHighPrice("GOOG", "2018-11-09");
    assertEquals(1075.56, price, 0.01);
    price = a.getPrice("GOOG", "2018-11-09", "high");
    assertEquals(1075.56, price, 0.01);
    try {
      a.getHighPrice("asdfasdfasdf", "2018-11-09");
      fail("Invalid code.");
    } catch (IllegalArgumentException e) {
      // This should be empty.
    }

    try {
      a.getHighPrice("GOOG", "2018-11-04");
      fail("There is no information on this date.");
    } catch (IllegalArgumentException e) {
      // This should be empty.
    }
  }

  @org.junit.Test
  public void getLowPrice() {
    // Test getting a low price.
    double price = a.getLowPrice("GOOG", "2018-11-09");
    assertEquals(1053.11, price, 0.01);
    price = a.getPrice("GOOG", "2018-11-09", "low");
    assertEquals(1053.11, price, 0.01);
    try {
      a.getLowPrice("asdfasdfasdf", "2018-11-09");
      fail("Invalid code.");
    } catch (IllegalArgumentException e) {
      // This should be empty.
    }

    try {
      a.getLowPrice("MSFT", "2018-11-04");
      fail("There is no information on this date.");
    } catch (IllegalArgumentException e) {
      // This should be empty.
    }
  }

  @org.junit.Test
  public void getClosePrice() {
    // Test getting a close price.
    double price = a.getClosePrice("GOOG", "2018-11-09");
    assertEquals(1066.15, price, 0.01);
    price = a.getPrice("GOOG", "2018-11-09", "close");
    assertEquals(1066.15, price, 0.01);

    try {
      a.getClosePrice("asdfasdfasdf", "2018-11-09");
      fail("Invalid code.");
    } catch (IllegalArgumentException e) {
      // This should be empty.
    }

    try {
      a.getClosePrice("MSFT", "2018-11-04");
      fail("There is no information on this date.");
    } catch (IllegalArgumentException e) {
      // This should be empty.
    }
  }


  @org.junit.Test
  public void getVolume() {
    // Test getting a volume
    Double volume = a.getVolume("GOOG", "2018-11-09");
    assertEquals(1343313.0, volume, 0.01);

    try {
      a.getVolume("asdfasdfasdf", "2018-11-09");
      fail("Invalid code.");
    } catch (IllegalArgumentException e) {
      // This should be empty.
    }

    try {
      a.getVolume("MSFT", "2018-11-04");
      fail("There is no information on this date.");
    } catch (IllegalArgumentException e) {
      // This should be empty.
    }
  }
}