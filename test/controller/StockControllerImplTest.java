package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import model.MockStockModel;
import model.StockModel;
import model.StockModelImpl;

import static org.junit.Assert.*;

public class StockControllerImplTest {


  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void start() {
    //mock testing
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("create portfolio1 "
            + "buy portfolio1 GOOG \n 100 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("create portfolio1\n"
            + "buy portfolio1 GOOG 100 2018-11-09 \n"
            + "determineCost portfolio1\n"
            + "determineValue portfolio1 2018-11-05\n"
            + "getPortfolioState portfolio1\n"
            + "getallstates\n", log.toString());

    // real testing
    StockModel stockModel = StockModelImpl.getBuilder().build();
    in = new StringReader("create portfolio1 "
            + "buy portfolio1 google \n 100 2018-11-09 "
            + "buy portfolio1 microsoft \n 200 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    out = new StringBuffer();
    controller = new StockControllerImpl(in, out);
    controller.start(stockModel);
    System.out.println(out.toString());
  }
}