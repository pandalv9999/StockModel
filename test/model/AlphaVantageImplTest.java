package model;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class AlphaVantageImplTest {

  @org.junit.Before
  public void setUp() throws Exception {
  }

  @org.junit.Test
  public void searchCode() {
  }

  @org.junit.Test
  public void getOpenPrice() {

    AlphaVantageImpl a = new AlphaVantageImpl();
    System.out.println("Company name -> get code");
    String code = a.searchCode("Google");
    System.out.println(code);
    System.out.println("end");
    //double price = a.getLowPrice("GOOG")
    //System.out.println(a.getOpenPrice("GOOG", "2018-11-09"));
    //System.out.println(a.getHighPrice("MSFT", "2018-11-09"));
    //System.out.println(a.getLowPrice("BA", "2018-11-09"));
    //System.out.println(a.getClosePrice("BABA", "2018-11-09"));
    //System.out.println(a.getVolume("BAC", "2018-11-09"));
    //System.out.println(a.getOpenPrice("GOOG", "2018-05-30"));
    //System.out.println(a.getHighPrice("GOOG", "2018-05-30"));
    //System.out.println(a.getLowPrice("GOOG", "2018-05-30"));
    //System.out.println(a.getClosePrice("GOOG", "2018-05-30"));
    //System.out.println(a.getVolume("GOOG", "2018-05-30"));

    try {
      a.getHighPrice("GOOG", "2018-11-04");
      fail("There is no information on this date.");
    } catch (IllegalArgumentException e) {
      assertEquals("There is no information on this date.", e.getMessage());
    }
  }

  @org.junit.Test
  public void getHighPrice() {
  }

  @org.junit.Test
  public void getLowPrice() {
  }

  @org.junit.Test
  public void getClosePrice() {
  }

  @org.junit.Test
  public void getVolume() {
  }
}