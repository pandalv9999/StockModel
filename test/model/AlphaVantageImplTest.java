package model;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class AlphaVantageImplTest {

  private AlphaVantageImpl a = new AlphaVantageImpl();

  @org.junit.Test
  public void APITest() {

    try {
      a.searchCode("asdf");
      fail("There is no code for this company.");
    } catch (IllegalArgumentException e) {
      assertEquals("No code data found for asdf", e.getMessage());
    }
    assertEquals("GOOG", a.searchCode("Google"));
    assertEquals("MSFT", a.searchCode("microsoft"));


    // Test getting an open price.
    double price = a.getOpenPrice("GOOG", "2018-11-09");
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

    // Test getting a high price.
    price = a.getHighPrice("GOOG", "2018-11-09");
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

    // Test getting a low price.
    price = a.getLowPrice("GOOG", "2018-11-09");
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

    // Test getting a close price.
    price = a.getClosePrice("GOOG", "2018-11-09");
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