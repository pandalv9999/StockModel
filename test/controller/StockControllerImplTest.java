package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import model.MockStockModel;

import static org.junit.Assert.*;

public class StockControllerImplTest {


  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void start() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("create portfolio1 buy portfolio1 GOOG 100 2018-11-9 determinecost portfolio1 determinevalue portfolio1 2018-11-5 getstate portfolio1 getallstate q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    System.out.println(log.toString());
  }
}