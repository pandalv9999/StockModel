package controller;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import model.MockStockModel;
import model.StockModel;
import model.StockModelImpl;

import static org.junit.Assert.assertEquals;

public class StockControllerImplTest {

  @Test
  public void startMockTest() {
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
            + "buy portfolio1 GOOG 100.0 2018-11-09 low\n"
            + "determineCost portfolio1\n"
            + "determineValue portfolio1 2018-11-05\n"
            + "getPortfolioState portfolio1\n"
            + "getallstates\n", log.toString());
  }

  @Test
  public void createFixedEqualMockTesting() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("createfixed portfolio1 "
            + "E \n 4 Facebook "
            + "Apple netflix Google "
            + "2000 \nN 2018-11-05 q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("create fixed\n"
            + "portfolio1\n"
            + "[Google, Apple, netflix, Facebook]\n"
            + "[0.25, 0.25, 0.25, 0.25]\n"
            + "2000.0\n"
            + "2018-11-05\n", log.toString());
//    System.out.println(log.toString());
//    System.out.println(out.toString());
  }

  @Test
  public void createFixedSeparateMockTesting() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("createfixed portfolio1 "
            + "s \n 4 Facebook 40 "
            + "Apple 20  netflix 30 Google 10\n"
            + "5200 \nN 2018-11-05 q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("create fixed\n"
            + "portfolio1\n"
            + "[Google, Apple, netflix, Facebook]\n"
            + "[0.1, 0.2, 0.3, 0.4]\n"
            + "5200.0\n"
            + "2018-11-05\n", log.toString());
//    System.out.println(log.toString());
//    System.out.println(out.toString());
  }

  @Test
  public void createFixedSeparateOnGoingMockTesting() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("createfixed portfolio1 "
            + "s \n 4 Facebook 40 "
            + "Apple 20  netflix 30 Google 10\n"
            + "5200 \nY 2018-11-05\n 2018-11-12 q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("create fixed ongoing\n"
            + "portfolio1\n"
            + "[Google, Apple, netflix, Facebook]\n"
            + "[0.1, 0.2, 0.3, 0.4]\n"
            + "5200.0\n"
            + "2018-11-05\n"
            + "2018-11-12\n", log.toString());
    // System.out.println(out.toString());
  }

  @Test
  public void createFixedSeparateOnGoingMockTesting2() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("createfixed portfolio1 "
            + "s \n 4 Facebook 40 "
            + "Apple 20  netflix 30 Google 10\n"
            + "5200 \nY 2018-11-05\n N q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("create fixed ongoing\n"
            + "portfolio1\n"
            + "[Google, Apple, netflix, Facebook]\n"
            + "[0.1, 0.2, 0.3, 0.4]\n"
            + "5200.0\n"
            + "2018-11-05\n"
            + "N\n", log.toString());
//    System.out.println(out.toString());
  }

  @Test
  public void quitWhenCreating() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("create q "
            + "buy portfolio1 GOOG \n 100 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate "
            + "or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void quitWhenBuying() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("buy q GOOG \n 100 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n", out.toString());
  }


  @Test
  public void quitWhenBuying2() {
    //quit when buying
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("buy GOOG \n q 100 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void quitWhenBuying3() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("buy GOOG \n 100 q 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "How many shares you want to buy?\n"
            + "Quit.\n", out.toString());
  }


  @Test
  public void quitDetermineCost() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("determinecost q "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void quitDetermineValue() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("determinevalue \nq portfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void quitDetermineValue2() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("determinevalue \n portfolio1 q 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the date you want to check in format yyyy-mm-dd.\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void quitDetermineValue3() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("getstate  q portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n", out.toString());
  }


  @Test
  public void quitTesting() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("Q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void determineCommissionFee() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("determinefee p1 Q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("determineFee p1\n", log.toString());
  }

  @Test
  public void buyByPercentage() {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("buyp p1 200 2018-11-12 Q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("buyByPercentage p1 200.0 2018-11-12\n", log.toString());
  }


  @Test
  public void realTesting() {
    StockModel stockModel = StockModelImpl
            .getBuilder()
            .commissionFee(0.0)
            .build();

    Reader in = new StringReader("create portfolio1 "
            + "buy portfolio1 google \n 100 2018-11-09 "
            + "buy portfolio1 microsoft \n 200 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerImpl controller = new StockControllerImpl(in, out);
    controller.start(stockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate "
            + "or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Created a portfolio successfully.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate or"
            + " q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "How many shares you want to buy?\n"
            + "Please input the date you want to buy in format (yyyy-mm-dd/ N/n).\n"
            + "Successfully bought google with 100 shares on 2018-11-09 and cost is  "
            + "$105310.99999999999\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate"
            + " or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "How many shares you want to buy?\n"
            + "Please input the date you want to buy in format (yyyy-mm-dd/ N/n).\n"
            + "Successfully bought microsoft with 200 shares on 2018-11-09 and cost is  $21752.0\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate "
            + "or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "The total cost of buying in is $127062.99999999999\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate"
            + " or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the date you want to check in format yyyy-mm-dd.\n"
            + "The value of all stocks in this portfolio on 2018-11-05 is $127395.0\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate"
            + " or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "The state of portfolio1\n"
            + "portfolio1:\n"
            + "Code: MSFT, Shares: 200.00, Average Buy-in Price: 108.76\n"
            + "Code: GOOG, Shares: 100.00, Average Buy-in Price: 1053.11\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate"
            + " or q/Q\n"
            + "The state of all portfolios:\n"
            + "portfolio1:\n"
            + "Code: MSFT, Shares: 200.00, Average Buy-in Price: 108.76\n"
            + "Code: GOOG, Shares: 100.00, Average Buy-in Price: 1053.11\n"
            + "\n"
            + "\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate"
            + " or q/Q\n"
            + "Quit.\n", out.toString());
  }
}