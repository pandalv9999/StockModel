import java.io.InputStreamReader;
import java.util.Scanner;

import controller.StockController;
import controller.StockControllerCommandImpl;
import controller.StockControllerImpl;
import model.Stock;
import model.StockModel;
import model.StockModelImpl;
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
import view.JFrameView;
import view.LoadPercentageView;
import view.LoadPortfolioView;
import view.SavePercentageView;
import view.SavePortfolioView;

/**
 * The class is the main class of the program.
 */

public class Main {

  /**
   * This is the main program of our project. It has the same effect when open the file in the .jar
   * file.
   *
   * @param args No argument needed.
   */

  public static void main(String[] args) throws Exception {
    StockModel model = StockModelImpl
            .getBuilder()
            .commissionFee(5.0)
            .build();

    System.out.println("Console or gui? (c/g)");
    InputStreamReader input = new InputStreamReader(System.in);
    Scanner scanner = new Scanner(input);
    String command = scanner.next();
    command = command.toLowerCase();

    if (command.equals("gui") || command.equals("gu") || command.equals("g")) {
      IView mainView = new JFrameView("GStocks");
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
              .model(model)
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
    } else if (command.equals("console") || command.equals("c")) {

      new StockControllerCommandImpl(input, System.out)
              .start(model);
    }
//    StockControllerImpl controller = new StockControllerImpl(model, mainView, createView,
//            getAllStateView, getStateView, determineCostView, determineFeeView, determineValueView,
//            buyView, buyPercentageView, buyAmountView, createFixedView);
    //controller.setView(mainView);
  }
}

