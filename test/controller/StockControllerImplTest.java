package controller;

import org.junit.Test;

import model.MockStockModel;
import view.BuyAmountView;
import view.BuyPercentageView;
import view.BuyView;
import view.CreateFixedView;
import view.CreatePercentageView;
import view.CreateView;
import view.DetermineCostView;
import view.DetermineFeeView;
import view.DetermineValueView;
import view.GetAllStateView;
import view.GetStateView;
import view.IView;
import view.LoadPercentageView;
import view.LoadPortfolioView;
import view.SavePercentageView;
import view.SavePortfolioView;

import static org.junit.Assert.*;

public class StockControllerImplTest {


  @Test
  public void processCommand() throws Exception {
    StringBuilder log = new StringBuilder();
    MockStockModel mockStockModel = new MockStockModel(log);
    IView mainView = new CreateView("GStocks");
    IView createView = new CreateView("Create empty portfolio");
    IView getAllStateView = new GetAllStateView("Get all state");
    IView getStateView = new GetStateView("Get state");
    IView determineCostView = new DetermineCostView("Determine cost");
    IView determineFeeView = new DetermineFeeView("Determine Fee");
    IView determineValueView = new DetermineValueView("Determine Value");
    IView buyView = new BuyView("Buy a stock by shares");
    IView buyPercentageView = new BuyPercentageView("Buy a portfolio");
    IView buyAmountView = new BuyAmountView("Buy a stock by amount");
    IView createFixedView = new CreateFixedView("Buy a stock by amount");
    IView createPercentageView = new CreatePercentageView("Create an investing plan");
    IView savePortfolioView = new SavePortfolioView("Save a portfolio");
    IView savePercentageView = new SavePercentageView("Save an investing plan");
    IView loadPortfolioView = new LoadPortfolioView("Load a portfolio");
    IView loadPercentageView = new LoadPercentageView("Load an investing plan");
    StockController controller = StockControllerImpl
            .getBuilder()
            .model(mockStockModel)
            .mainView(mainView)
            .createView(createView)
            .getAllStateView(getAllStateView)
            .getStateView(getStateView)
            .determineCostView(determineCostView)
            .determineFeeView(determineFeeView)
            .determineValueView(determineValueView)
            .buyView(buyView)
            .buyPercentageView(buyPercentageView)
            .buyAmountView(buyAmountView)
            .createFixedView(createFixedView)
            .createPercentageView(createPercentageView)
            .savePortfolioView(savePortfolioView)
            .savePercentageView(savePercentageView)
            .loadPortfolioView(loadPortfolioView)
            .loadPercentageView(loadPercentageView)
            .build();

    controller.processCommand("create a");
    assertEquals("create a\n", log.toString());

    controller.processCommand("createfixed a e 2 goog facebook 1000 y 20180101 n n ");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n", log.toString());

    controller.processCommand("createp a e 2 google facebook");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n", log.toString());

    controller.processCommand("buy a google 200 n");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n"
            + "determineFee a\n"
            + "buy a google 200.0 n low\n"
            + "determineFee a\n", log.toString());

    controller.processCommand("determinecost a");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n"
            + "determineFee a\n"
            + "buy a google 200.0 n low\n"
            + "determineFee a\n"
            + "determineCost a\n"
            + "determineFee a\n", log.toString());

    controller.processCommand("determinevalue a n");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n"
            + "determineFee a\n"
            + "buy a google 200.0 n low\n"
            + "determineFee a\n"
            + "determineCost a\n"
            + "determineFee a\n"
            + "determineValue a n\n", log.toString());

    controller.processCommand("determinefee a");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n"
            + "determineFee a\n"
            + "buy a google 200.0 n low\n"
            + "determineFee a\n"
            + "determineCost a\n"
            + "determineFee a\n"
            + "determineValue a n\n"
            + "determineFee a\n", log.toString());

    controller.processCommand("buyp a b 10000 n");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n"
            + "determineFee a\n"
            + "buy a google 200.0 n low\n"
            + "determineFee a\n"
            + "determineCost a\n"
            + "determineFee a\n"
            + "determineValue a n\n"
            + "determineFee a\n"
            + "determineFee a\n"
            + "buyByPercentage a b 10000.0 n\n"
            + "determineFee a\n", log.toString());

    controller.processCommand("getstate a");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n"
            + "determineFee a\n"
            + "buy a google 200.0 n low\n"
            + "determineFee a\n"
            + "determineCost a\n"
            + "determineFee a\n"
            + "determineValue a n\n"
            + "determineFee a\n"
            + "determineFee a\n"
            + "buyByPercentage a b 10000.0 n\n"
            + "determineFee a\n"
            + "getPortfolioState a\n", log.toString());

    controller.processCommand("getallstate");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n"
            + "determineFee a\n"
            + "buy a google 200.0 n low\n"
            + "determineFee a\n"
            + "determineCost a\n"
            + "determineFee a\n"
            + "determineValue a n\n"
            + "determineFee a\n"
            + "determineFee a\n"
            + "buyByPercentage a b 10000.0 n\n"
            + "determineFee a\n"
            + "getPortfolioState a\n"
            + "getallstates\n", log.toString());

    controller.processCommand("saveportfolio a a1");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n"
            + "determineFee a\n"
            + "buy a google 200.0 n low\n"
            + "determineFee a\n"
            + "determineCost a\n"
            + "determineFee a\n"
            + "determineValue a n\n"
            + "determineFee a\n"
            + "determineFee a\n"
            + "buyByPercentage a b 10000.0 n\n"
            + "determineFee a\n"
            + "getPortfolioState a\n"
            + "getallstates\n"
            + "savePortfolio a a1\n", log.toString());

    controller.processCommand("loadportfolio a ");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n"
            + "determineFee a\n"
            + "buy a google 200.0 n low\n"
            + "determineFee a\n"
            + "determineCost a\n"
            + "determineFee a\n"
            + "determineValue a n\n"
            + "determineFee a\n"
            + "determineFee a\n"
            + "buyByPercentage a b 10000.0 n\n"
            + "determineFee a\n"
            + "getPortfolioState a\n"
            + "getallstates\n"
            + "savePortfolio a a1\n"
            + "loadPortfolio a\n", log.toString());


    controller.processCommand("savepercentage a a ");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n"
            + "determineFee a\n"
            + "buy a google 200.0 n low\n"
            + "determineFee a\n"
            + "determineCost a\n"
            + "determineFee a\n"
            + "determineValue a n\n"
            + "determineFee a\n"
            + "determineFee a\n"
            + "buyByPercentage a b 10000.0 n\n"
            + "determineFee a\n"
            + "getPortfolioState a\n"
            + "getallstates\n"
            + "savePortfolio a a1\n"
            + "loadPortfolio a\n"
            + "savePercentage a a\n", log.toString());

    controller.processCommand("loadpercentage a ");
    assertEquals("create a\n"
            + "create fixed ongoing\n"
            + "a [goog, facebook] [0.5, 0.5] 1000.0 2018-01-01 n 30 determineCost a\n"
            + "determineFee a\n"
            + "createp a {facebook=0.5, google=0.5}\n"
            + "determineFee a\n"
            + "buy a google 200.0 n low\n"
            + "determineFee a\n"
            + "determineCost a\n"
            + "determineFee a\n"
            + "determineValue a n\n"
            + "determineFee a\n"
            + "determineFee a\n"
            + "buyByPercentage a b 10000.0 n\n"
            + "determineFee a\n"
            + "getPortfolioState a\n"
            + "getallstates\n"
            + "savePortfolio a a1\n"
            + "loadPortfolio a\n"
            + "savePercentage a a\n"
            + "loadPercentage a\n", log.toString());

  }
}