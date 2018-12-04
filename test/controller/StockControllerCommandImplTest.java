package controller;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import model.MockStockModel;
import model.StockModel;
import model.StockModelImpl;

import static org.junit.Assert.assertEquals;

public class StockControllerCommandImplTest {

  @Test
  public void startMockTest() throws IOException {
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
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("create portfolio1\n"
            + "determineFee portfolio1\n"
            + "buy portfolio1 GOOG 100.0 2018-11-09 low\n"
            + "determineFee portfolio1\n"
            + "determineCost portfolio1\n"
            + "determineFee portfolio1\n"
            + "determineValue portfolio1 2018-11-05\n"
            + "getPortfolioState portfolio1\n"
            + "getallstates\n", log.toString());
  }

  @Test
  public void createFixedEqualMockTesting() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("createfixed portfolio1 "
            + "E \n 4 Facebook "
            + "Apple netflix Google "
            + "2000 \nN 2018-11-05 q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("create fixed\n"
            + "portfolio1\n"
            + "[Google, Apple, netflix, Facebook]\n"
            + "[0.25, 0.25, 0.25, 0.25]\n"
            + "2000.0\n"
            + "2018-11-05\n"
            + "determineCost portfolio1\n"
            + "determineFee portfolio1\n", log.toString());
  }

  @Test
  public void createFixedSeparateMockTesting() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("createfixed portfolio1 "
            + "s \n 4 Facebook 40 "
            + "Apple 20  netflix 30 Google 10\n"
            + "5200 \nN 2018-11-05 q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("create fixed\n"
            + "portfolio1\n"
            + "[Google, Apple, netflix, Facebook]\n"
            + "[0.1, 0.2, 0.3, 0.4]\n"
            + "5200.0\n"
            + "2018-11-05\n"
            + "determineCost portfolio1\n"
            + "determineFee portfolio1\n", log.toString());
  }

  @Test
  public void createFixedSeparateOnGoingMockTesting() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("createfixed portfolio1 "
            + "s \n 4 Facebook 40 "
            + "Apple 20  netflix 30 Google 10\n"
            + "5200 \nY 2018-01-05\n 2018-11-12 15 q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("create fixed ongoing\n"
            + "portfolio1\n"
            + "[Google, Apple, netflix, Facebook]\n"
            + "[0.1, 0.2, 0.3, 0.4]\n"
            + "5200.0\n"
            + "2018-01-05\n"
            + "2018-11-12\n"
            + "15\n"
            + "determineCost portfolio1\n"
            + "determineFee portfolio1\n", log.toString());
  }

  @Test
  public void createFixedSeparateOnGoingMockTesting2() throws Exception {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("createfixed portfolio1 "
            + "s \n 4 Facebook 40 "
            + "Apple 20  netflix 30 Google 10\n"
            + "5200 \nY 2018-11-05\n N  15 q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("create fixed ongoing\n"
            + "portfolio1\n"
            + "[Google, Apple, netflix, Facebook]\n"
            + "[0.1, 0.2, 0.3, 0.4]\n"
            + "5200.0\n"
            + "2018-11-05\n"
            + "N\n"
            + "15\n"
            + "determineCost portfolio1\n"
            + "determineFee portfolio1\n", log.toString());
  }

  @Test
  public void quitWhenCreating() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("create q q "
            + "buy portfolio1 GOOG \n 100 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void quitWhenBuying() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("buy q q GOOG \n 100 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }


  @Test
  public void quitWhenBuying2() throws IOException {
    //quit when buying
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("buy GOOG \n q q 100 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void quitWhenBuying3() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("buy GOOG \n 100 q q 2018-11-09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "How many shares you want to buy?\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }


  @Test
  public void quitDetermineCost() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("determinecost q q "
            + "determinevalue \nportfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void quitDetermineValue() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("determinevalue \nq q portfolio1 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void quitDetermineValue2() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("determinevalue \n portfolio1 q q 2018-11-05 \n"
            + "getstate portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate"
            + ", determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the date you want to check in format yyyy-mm-dd.\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate"
            + ", determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void quitDetermineValue3() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("getstate  q q portfolio1 "
            + "getallstate "
            + "q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }


  @Test
  public void quitTesting() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("Q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void determineCommissionFee() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("determinefee p1 Q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("determineFee p1\n", log.toString());
  }


  @Test
  public void determineCommissionFeeQuit() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("determinefee Q q p1 ");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("", log.toString());
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void buyByPercentage() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("buyp p1 200 2018-11-12 Q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("determineFee p1\n"
            + "buyByPercentage p1 200.0 2018-11-12\n"
            + "determineFee p1\n", log.toString());
  }

  @Test
  public void buyByPercentageQuit() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("buyp q q p1 200 2018-11-12 Q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void buyByPercentageQuit2() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("buyp p1 q q 200 2018-11-12 Q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Amount of investment?\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }


  @Test
  public void buyByPercentageQuit3() throws IOException {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    Reader in = new StringReader("buyp p1 200 q q 2018-11-12 Q");
    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(mockStockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Amount of investment?\n"
            + "Please input the date you want to buy in format yyyy-mm-dd/N/n.\n"
            + "Quit.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }


  @Test
  public void realCreateFixedTesting() throws IOException {
    StockModel stockModel = StockModelImpl
            .getBuilder()
            .commissionFee(5.0)
            .build();

    Reader in = new StringReader("createfixed p1 s 2 microsoft 40 netflix 60 "
            + "2000 y 20180101 20181112 30\n"
            + "getallstate "
            + "q");

    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(stockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Equal proportion or separate?(E/S)\n"
            + "Number of companies?\n"
            + "Name of company?\n"
            + "Proportion in percentage? E.g. input 30 to represent 30%.\n"
            + "Name of company?\n"
            + "Proportion in percentage? E.g. input 30 to represent 30%.\n"
            + "Amount of investment?\n"
            + "Ongoing? Y/N\n"
            + "Provide a start date?(yyyy-mm-dd / N/n)\n"
            + "Provide a end date?(yyyy-mm-dd / N/n)\n"
            + "Provide a interval in days?(e.g 15, 30, 60 / N/n)\n"
            + "Created a Fixed portfolio successfully with commission fee $110.0\n"
            + "The total cost is $22110.0\n"
            + "Commission fee is of 0.4975124378109453%\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "The state of all portfolios:\n"
            + "p1:\n"
            + "Code: NFLX, Shares: 43.20, Average Buy-in Price: 305.59\n"
            + "Code: MSFT, Shares: 89.31, Average Buy-in Price: 98.53\n"
            + "The commission fee of this portfolio is $110.00\n"
            + "\n"
            + "\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }

  @Test
  public void realTesting() throws IOException {
    StockModel stockModel = StockModelImpl
            .getBuilder()
            .commissionFee(5.0)
            .build();

    Reader in = new StringReader("create portfolio1 "
            + "buy portfolio1 google \n 100 2018-11-09 "
            + "buy portfolio1 microsoft \n 200 2018/11/09 "
            + "buy portfolio1 facebook \n 300 2018/11/09 "
            + "determinecost portfolio1 "
            + "determinevalue \nportfolio1 2018.11.8 \n"
            + "createfixed 2 e 2 google microsoft 2000 y 20180105 20181112 30\n"
            + "buyp 2 2000 n\n"
            + "buyp 2 3000 n\n"
            + "createfixed 3 s 2 google 30 microsoft 70 1000 y 2018/01/01 n 30\n"
            + "createfixed 4 s 2 google 30 microsoft 70 1000 n 20181112\n"
            + "getstate portfolio1 "
            + "determinefee 3 "
            + "getallstate "
            + "q");

    StringBuffer out = new StringBuffer();
    StockControllerCommandImpl controller = new StockControllerCommandImpl(in, out);
    controller.start(stockModel);
    assertEquals("Welcome to the stock trading system.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Created an empty portfolio successfully.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "How many shares you want to buy?\n"
            + "Please input the date you want to buy in format (yyyy-mm-dd/ N/n).\n"
            + "Successfully bought google with 100 shares on 2018-11-09 and total cost is "
            + "$105315.99999999999 with commission fee $5.0\n"
            + "Commission fee is of 0.004747616696418398%\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "How many shares you want to buy?\n"
            + "Please input the date you want to buy in format (yyyy-mm-dd/ N/n).\n"
            + "Successfully bought microsoft with 200 shares on 2018-11-09 and total cost is "
            + "$21757.0 with commission fee $5.0\n"
            + "Commission fee is of 0.02298110952796801%\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the company's name.\n"
            + "How many shares you want to buy?\n"
            + "Please input the date you want to buy in format (yyyy-mm-dd/ N/n).\n"
            + "Successfully bought facebook with 300 shares on 2018-11-09 and total cost is "
            + "$43226.0 with commission fee $5.0\n"
            + "Commission fee is of 0.011567112386063944%\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "The cost basis of buying in portfolio1 is $170284.0 with commission fee $15.0\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Please input the date you want to check in format yyyy-mm-dd.\n"
            + "The value of all stocks in this portfolio on 2018-11-08 is $177051.0\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Equal proportion or separate?(E/S)\n"
            + "Number of companies?\n"
            + "Name of company?\n"
            + "Name of company?\n"
            + "Amount of investment?\n"
            + "Ongoing? Y/N\n"
            + "Provide a start date?(yyyy-mm-dd / N/n)\n"
            + "Provide a end date?(yyyy-mm-dd / N/n)\n"
            + "Provide a interval in days?(e.g 15, 30, 60 / N/n)\n"
            + "Created a Fixed portfolio successfully with commission fee $110.0\n"
            + "The total cost is $22110.0\n"
            + "Commission fee is of 0.4975124378109453%\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate,"
            + " determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Amount of investment?\n"
            + "Please input the date you want to buy in format yyyy-mm-dd/N/n.\n"
            + "The cost of buying this portfolio is $2010.0 with commission fee $10.0\n"
            + "Commission fee is of 0.4975124378109453% in this transaction.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Amount of investment?\n"
            + "Please input the date you want to buy in format yyyy-mm-dd/N/n.\n"
            + "The cost of buying this portfolio is $3010.0 with commission fee $10.0\n"
            + "Commission fee is of 0.33222591362126247% in this transaction.\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Equal proportion or separate?(E/S)\n"
            + "Number of companies?\n"
            + "Name of company?\n"
            + "Proportion in percentage? E.g. input 30 to represent 30%.\n"
            + "Name of company?\n"
            + "Proportion in percentage? E.g. input 30 to represent 30%.\n"
            + "Amount of investment?\n"
            + "Ongoing? Y/N\n"
            + "Provide a start date?(yyyy-mm-dd / N/n)\n"
            + "Provide a end date?(yyyy-mm-dd / N/n)\n"
            + "Provide a interval in days?(e.g 15, 30, 60 / N/n)\n"
            + "Created a Fixed portfolio successfully with commission fee $110.0\n"
            + "The total cost is $11110.0\n"
            + "Commission fee is of 0.9900990099009901%\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "Equal proportion or separate?(E/S)\n"
            + "Number of companies?\n"
            + "Name of company?\n"
            + "Proportion in percentage? E.g. input 30 to represent 30%.\n"
            + "Name of company?\n"
            + "Proportion in percentage? E.g. input 30 to represent 30%.\n"
            + "Amount of investment?\n"
            + "Ongoing? Y/N\n"
            + "Provide a buying date?(yyyy-mm-dd / N/n)\n"
            + "Created a Fixed portfolio successfully with commission fee $10.0\n"
            + "The total cost is $1010.0\n"
            + "Commission fee is of 0.9900990099009901%\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "The state of portfolio1\n"
            + "portfolio1:\n"
            + "Code: MSFT, Shares: 200.00, Average Buy-in Price: 108.76\n"
            + "Code: GOOG, Shares: 100.00, Average Buy-in Price: 1053.11\n"
            + "Code: FB, Shares: 300.00, Average Buy-in Price: 144.07\n"
            + "The commission fee of this portfolio is $15.00\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Please input the portfolio's name.\n"
            + "The fee of all transactions in this portfolio is $110.0\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "The state of all portfolios:\n"
            + "2:\n"
            + "Code: MSFT, Shares: 134.87, Average Buy-in Price: 100.09\n"
            + "Code: GOOG, Shares: 12.27, Average Buy-in Price: 1100.04\n"
            + "The commission fee of this portfolio is $130.00\n"
            + "\n"
            + "3:\n"
            + "Code: MSFT, Shares: 78.15, Average Buy-in Price: 98.53\n"
            + "Code: GOOG, Shares: 2.98, Average Buy-in Price: 1108.28\n"
            + "The commission fee of this portfolio is $110.00\n"
            + "\n"
            + "4:\n"
            + "Code: MSFT, Shares: 6.55, Average Buy-in Price: 106.87\n"
            + "Code: GOOG, Shares: 0.29, Average Buy-in Price: 1038.63\n"
            + "The commission fee of this portfolio is $10.00\n"
            + "\n"
            + "portfolio1:\n"
            + "Code: MSFT, Shares: 200.00, Average Buy-in Price: 108.76\n"
            + "Code: GOOG, Shares: 100.00, Average Buy-in Price: 1053.11\n"
            + "Code: FB, Shares: 300.00, Average Buy-in Price: 144.07\n"
            + "The commission fee of this portfolio is $15.00\n"
            + "\n"
            + "\n"
            + "You can input: create, buy, determinecost, determinevalue, getstate, getallstate, "
            + "determinefee, createfixed, buyp, or q/Q\n"
            + "Quit.\n", out.toString());
  }
}