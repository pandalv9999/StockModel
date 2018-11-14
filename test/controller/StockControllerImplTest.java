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

    // quit when creating
    log = new StringBuilder();
    mockStockModel = new MockStockModel(log);
    in = new StringReader("create q "
            + "buy portfolio1 GOOG \n 100 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    out = new StringBuffer();
    controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate "
            + "or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n", out.toString());

    //quit when buying
    log = new StringBuilder();
    mockStockModel = new MockStockModel(log);
    in = new StringReader("buy q GOOG \n 100 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    out = new StringBuffer();
    controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n", out.toString());

    //quit when buying
    log = new StringBuilder();
    mockStockModel = new MockStockModel(log);
    in = new StringReader("buy GOOG \n q 100 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    out = new StringBuffer();
    controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "Quit.\n", out.toString());

    //quit when buying
    log = new StringBuilder();
    mockStockModel = new MockStockModel(log);
    in = new StringReader("buy GOOG \n 100 q 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    out = new StringBuffer();
    controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "How many shares you want to buy?\n"
            + "Quit.\n", out.toString());

    //quit when determine cost
    log = new StringBuilder();
    mockStockModel = new MockStockModel(log);
    in = new StringReader("determinecost q "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    out = new StringBuffer();
    controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n", out.toString());

    //quit when determine value
    log = new StringBuilder();
    mockStockModel = new MockStockModel(log);
    in = new StringReader("determinevalue \nq portfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    out = new StringBuffer();
    controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n", out.toString());

    //quit when determine value
    log = new StringBuilder();
    mockStockModel = new MockStockModel(log);
    in = new StringReader("determinevalue \n portfolio1 q 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    out = new StringBuffer();
    controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the date you want to check in format yyyy-mm-dd.\n"
            + "Quit.\n", out.toString());

    //quit when determine value
    log = new StringBuilder();
    mockStockModel = new MockStockModel(log);
    in = new StringReader("getstate  q portfolio1 "
            + "getallstate "
            + "q");
    out = new StringBuffer();
    controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n", out.toString());

    //quit
    log = new StringBuilder();
    mockStockModel = new MockStockModel(log);
    in = new StringReader("Q");
    out = new StringBuffer();
    controller = new StockControllerImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate or q/Q\n"
            + "Quit.\n", out.toString());


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
            + "Please input the date you want to buy in format yyyy-mm-dd.\n"
            + "Successfully bought google with 100 shares on 2018-11-09 and cost is  "
            + "$105310.99999999999\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate"
            + " or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "How many shares you want to buy?\n"
            + "Please input the date you want to buy in format yyyy-mm-dd.\n"
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
            + "Code: MSFT, Shares: 200, Average Buy-in Price: 108.76\n"
            + "Code: GOOG, Shares: 100, Average Buy-in Price: 1053.11\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate"
            + " or q/Q\n"
            + "The state of all portfolios:\n"
            + "portfolio1:\n"
            + "Code: MSFT, Shares: 200, Average Buy-in Price: 108.76\n"
            + "Code: GOOG, Shares: 100, Average Buy-in Price: 1053.11\n"
            + "\n"
            + "\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate"
            + " or q/Q\n"
            + "Quit.\n", out.toString());
  }
}