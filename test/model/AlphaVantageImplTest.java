package model;

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
    System.out.println(a.getOpenPrice("GOOG", "2018-11-09"));
    System.out.println(a.getHighPrice("GOOG", "2018-11-09"));
    System.out.println(a.getLowPrice("GOOG", "2018-11-09"));
    System.out.println(a.getClosePrice("GOOG", "2018-11-09"));
    System.out.println(a.getVolume("GOOG", "2018-11-09"));
    System.out.println(a.getOpenPrice("GOOG", "2018-05-30"));
    System.out.println(a.getHighPrice("GOOG", "2018-05-30"));
    System.out.println(a.getLowPrice("GOOG", "2018-05-30"));
    System.out.println(a.getClosePrice("GOOG", "2018-05-30"));
    System.out.println(a.getVolume("GOOG", "2018-05-30"));
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