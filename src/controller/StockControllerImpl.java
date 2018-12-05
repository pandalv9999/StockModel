package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.commands.Buy;
import controller.commands.BuyByAmount;
import controller.commands.BuyByPercentage;
import controller.commands.Create;
import controller.commands.CreateFixed;
import controller.commands.CreatePercentage;
import controller.commands.DetermineCommissionFee;
import controller.commands.DetermineCost;
import controller.commands.DetermineValue;
import controller.commands.GetAllState;
import controller.commands.GetState;
import controller.commands.LoadPercentage;
import controller.commands.LoadPortfolio;
import controller.commands.PlotValue;
import controller.commands.SavePercentage;
import controller.commands.SavePortfolio;
import controller.utility.Input;
import model.StockModel;
import view.ButtonListener;
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
 * This class represents an implementation of a stock controller with gui.
 */
public class StockControllerImpl implements StockController {
  private StockModel model;
  private IView view;
  private IView mainView;
  private IView createView;
  private IView getAllStateView;
  private IView getStateView;
  private IView determineCostView;
  private IView determineFeeView;
  private IView determineValueView;
  private IView buyView;
  private IView buyPercentageView;
  private IView buyAmountView;
  private IView createFixedView;
  private IView createPercentageView;
  private IView savePortfolioView;
  private IView savePercentageView;
  private IView loadPortfolioView;
  private IView loadPercentageView;


  /**
   * The constructor of this method. It will take several views and model.
   */
  private StockControllerImpl(StockModel m, IView mainView, IView createView, IView getAllStateView,
                              IView getStateView, IView determineCostView, IView determineFeeView,
                              IView determineValueView, IView buyView, IView buyPercentageView,
                              IView buyAmountView, IView createFixedView,
                              IView createPercentageView,
                              IView savePortfolioView, IView savePercentageView,
                              IView loadPortfolioView, IView loadPercentageView) throws Exception {
    this.view = mainView;
    this.model = m;
    this.mainView = mainView;
    this.createView = createView;
    this.getAllStateView = getAllStateView;
    this.getStateView = getStateView;
    this.determineCostView = determineCostView;
    this.determineFeeView = determineFeeView;
    this.determineValueView = determineValueView;
    this.buyView = buyView;
    this.buyPercentageView = buyPercentageView;
    this.buyAmountView = buyAmountView;
    this.createFixedView = createFixedView;
    this.createPercentageView = createPercentageView;
    this.savePortfolioView = savePortfolioView;
    this.savePercentageView = savePercentageView;
    this.loadPortfolioView = loadPortfolioView;
    this.loadPercentageView = loadPercentageView;
    configureKeyBoardListener();
    configureButtonListener();
  }

  /**
   * This method set the current view of the controller.
   *
   * @param v the current view
   */
  public void setView(IView v) {
    view = v;
  }

  private void configureKeyBoardListener() {
    // This method will do nothing.
  }

  /**
   * This method will configure the button listener in view.
   */
  private void configureButtonListener() throws Exception {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("Exit Button", () -> {
      System.exit(0);
    });

    //create empty portfolio frame
    buttonClickedMap.put("Create Button", () -> {
      // System.out.println("haha");
      this.setView(this.createView);
      ((JFrameView) this.mainView).setVisible(false);
      ((CreateView) this.createView).setVisible(true);
    });

    buttonClickedMap.put("Create Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("Create Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((CreateView) this.createView).setVisible(false);
    });


    //GetState frame
    buttonClickedMap.put("GetState Button", () -> {
      // System.out.println("haha2");
      this.setView(this.getStateView);
      ((JFrameView) this.mainView).setVisible(false);
      ((GetStateView) this.getStateView).setVisible(true);
    });

    buttonClickedMap.put("GetState Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("GetState Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((GetStateView) this.getStateView).setVisible(false);
    });


    //GetAllState frame
    buttonClickedMap.put("GetAllState Button", () -> {
      this.setView(this.getAllStateView);
      ((JFrameView) this.mainView).setVisible(false);
      ((GetAllStateView) this.getAllStateView).setVisible(true);
      try {
        view.setEchoOutput(processCommand("getallstate"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });


    buttonClickedMap.put("GetAllState Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((GetAllStateView) this.getAllStateView).setVisible(false);
    });


    //DetermineCost frame
    buttonClickedMap.put("DetermineCost Button", () -> {
      // System.out.println("haha2");
      this.setView(this.determineCostView);
      ((JFrameView) this.mainView).setVisible(false);
      ((DetermineCostView) this.determineCostView).setVisible(true);
    });

    buttonClickedMap.put("DetermineCost Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("DetermineCost Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((DetermineCostView) this.determineCostView).setVisible(false);
    });


    //DetermineFee frame
    buttonClickedMap.put("DetermineFee Button", () -> {
      // System.out.println("haha2");
      this.setView(this.determineFeeView);
      ((JFrameView) this.mainView).setVisible(false);
      ((DetermineFeeView) this.determineFeeView).setVisible(true);
    });

    buttonClickedMap.put("DetermineFee Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("DetermineFee Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((DetermineFeeView) this.determineFeeView).setVisible(false);
    });


    //DetermineValue frame
    buttonClickedMap.put("DetermineValue Button", () -> {
      // System.out.println("haha2");
      this.setView(this.determineValueView);
      ((JFrameView) this.mainView).setVisible(false);
      ((DetermineValueView) this.determineValueView).setVisible(true);
    });

    buttonClickedMap.put("DetermineValue Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();


    });

    buttonClickedMap.put("DetermineValue Plot Button", () -> {
      String portfolioName = ((DetermineValueView) view).getPlotInputString();
      try {
        ((DetermineValueView) view).plot(PlotValue.plotValue(model, portfolioName));
        view.setEchoOutput("Plot the value of all stocks in this portfolio "
                + "in the last 12 months.\n");
      } catch (Exception e) {
        view.setEchoOutput("This portfolio does not exist!\n");

      }
      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();
    });

    buttonClickedMap.put("DetermineValue Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((DetermineValueView) this.determineValueView).setVisible(false);
    });


    //Buy frame
    buttonClickedMap.put("Buy Button", () -> {
      // System.out.println("haha2");
      this.setView(this.buyView);
      ((JFrameView) this.mainView).setVisible(false);
      ((BuyView) this.buyView).setVisible(true);
    });

    buttonClickedMap.put("Buy Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("Buy Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((BuyView) this.buyView).setVisible(false);
    });

    //BuyPercentage frame
    buttonClickedMap.put("BuyPercentage Button", () -> {
      // System.out.println("haha2");
      this.setView(this.buyPercentageView);
      ((JFrameView) this.mainView).setVisible(false);
      ((BuyPercentageView) this.buyPercentageView).setVisible(true);
    });

    buttonClickedMap.put("BuyPercentage Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("BuyPercentage Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((BuyPercentageView) this.buyPercentageView).setVisible(false);
    });

    //BuyAmount frame
    buttonClickedMap.put("BuyAmount Button", () -> {
      // System.out.println("haha2");
      this.setView(this.buyAmountView);
      ((JFrameView) this.mainView).setVisible(false);
      ((BuyAmountView) this.buyAmountView).setVisible(true);
    });

    buttonClickedMap.put("BuyAmount Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("BuyAmount Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((BuyAmountView) this.buyAmountView).setVisible(false);
    });


    //CreateFixed frame
    buttonClickedMap.put("CreateFixed Button", () -> {
      // System.out.println("haha2");
      this.setView(this.createFixedView);
      ((JFrameView) this.mainView).setVisible(false);
      ((CreateFixedView) this.createFixedView).setVisible(true);
    });

    buttonClickedMap.put("CreateFixed Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("CreateFixed Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((CreateFixedView) this.createFixedView).setVisible(false);
    });


    //CreatePercentage frame
    buttonClickedMap.put("CreatePercentage Button", () -> {
      // System.out.println("haha2");
      this.setView(this.createPercentageView);
      ((JFrameView) this.mainView).setVisible(false);
      ((CreatePercentageView) this.createPercentageView).setVisible(true);
    });

    buttonClickedMap.put("CreatePercentage Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("CreatePercentage Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((CreatePercentageView) this.createPercentageView).setVisible(false);
    });


    //SavePortfolio frame
    buttonClickedMap.put("SavePortfolio Button", () -> {
      this.setView(this.savePortfolioView);
      ((JFrameView) this.mainView).setVisible(false);
      ((SavePortfolioView) this.savePortfolioView).setVisible(true);
    });

    buttonClickedMap.put("SavePortfolio Echo Button", () -> {
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("SavePortfolio Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((SavePortfolioView) this.savePortfolioView).setVisible(false);
    });

    //SavePercentage frame
    buttonClickedMap.put("SavePercentage Button", () -> {
      this.setView(this.savePercentageView);
      ((JFrameView) this.mainView).setVisible(false);
      ((SavePercentageView) this.savePercentageView).setVisible(true);
    });

    buttonClickedMap.put("SavePercentage Echo Button", () -> {
      // System.out.println("SavePercentage Echo Button");
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("SavePercentage Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((SavePercentageView) this.savePercentageView).setVisible(false);
    });

    //LoadPortfolio frame
    buttonClickedMap.put("LoadPortfolio Button", () -> {
      this.setView(this.loadPortfolioView);
      ((JFrameView) this.mainView).setVisible(false);
      ((LoadPortfolioView) this.loadPortfolioView).setVisible(true);
    });

    buttonClickedMap.put("LoadPortfolio Echo Button", () -> {
      // System.out.println("LoadPortfolio Echo Button");
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();

      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("LoadPortfolio Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((LoadPortfolioView) this.loadPortfolioView).setVisible(false);
    });


    //LoadPercentage frame
    buttonClickedMap.put("LoadPercentage Button", () -> {
      this.setView(this.loadPercentageView);
      ((JFrameView) this.mainView).setVisible(false);
      ((LoadPercentageView) this.loadPercentageView).setVisible(true);
    });

    buttonClickedMap.put("LoadPercentage Echo Button", () -> {
      // System.out.println("LoadPercentage Echo Button");
      String command = view.getInputString();
      try {
        view.setEchoOutput(processCommand(command));
      } catch (IOException e) {
        e.printStackTrace();
      }

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });

    buttonClickedMap.put("LoadPercentage Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((LoadPercentageView) this.loadPercentageView).setVisible(false);
    });


    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.mainView.addActionListener(buttonListener);
    this.createView.addActionListener(buttonListener);
    this.getAllStateView.addActionListener(buttonListener);
    this.getStateView.addActionListener(buttonListener);
    this.determineCostView.addActionListener(buttonListener);
    this.determineFeeView.addActionListener(buttonListener);
    this.determineValueView.addActionListener(buttonListener);
    this.buyView.addActionListener(buttonListener);
    this.buyPercentageView.addActionListener(buttonListener);
    this.buyAmountView.addActionListener(buttonListener);
    this.createFixedView.addActionListener(buttonListener);
    this.createPercentageView.addActionListener(buttonListener);
    this.savePortfolioView.addActionListener(buttonListener);
    this.savePercentageView.addActionListener(buttonListener);
    this.loadPortfolioView.addActionListener(buttonListener);
    this.loadPercentageView.addActionListener(buttonListener);
  }

  /**
   * This method can parse a command and call the model with certain parameters.
   *
   * @param command a command passed by the view
   * @return a result string
   */
  public String processCommand(String command)
          throws IllegalArgumentException, IllegalStateException, IOException {
    // System.out.println(command);
    if (model == null) {
      throw new IllegalArgumentException("Model should not be null.");
    }

    Scanner scan = new Scanner(command);
    StringBuilder output = new StringBuilder();
    String cmd = null;

    while (scan.hasNext()) {
      String in = Input.input(scan, "Please input a command.", output, false).toLowerCase();

      if (Input.isQuit(in)) {
        output.append("Quit.\n");
        return output.toString();
      } else if (in.equals("createfixed")) {
        try {
          CreateFixed.createFixed(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("create")) { //done
        try {
          Create.create(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("createp")) { //done
        try {
          CreatePercentage.createPercentage(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("buy")) { //done
        try {
          Buy.buy(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("buya")) { //done
        try {
          BuyByAmount.buyByAmount(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("determinecost")) { //done
        try {
          DetermineCost.determineCost(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("determinevalue")) { //done
        try {
          DetermineValue.determineValue(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("determinefee")) { //done
        try {
          DetermineCommissionFee.determineCommissionFee(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("buyp")) { //done
        try {
          BuyByPercentage.buyByPercentage(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("getstate")) { //done
        try {
          GetState.getState(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("getallstate")) { //done
        try {
          GetAllState.getAllState(model, output);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("saveportfolio")) { //done
        try {
          SavePortfolio.savePortfolio(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("loadportfolio")) { //done
        try {
          LoadPortfolio.loadPortfolio(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("savepercentage")) { //done
        try {
          SavePercentage.savePercentage(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else if (in.equals("loadpercentage")) { //done
        try {
          LoadPercentage.loadPercentage(model, scan, output, false);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      } else {
        output.append("You can input: create, buy, determinecost, determinevalue, getstate, "
                + "getallstate, determinefee, createfixed, buyp, or q/Q\n");
      }
    }
    return output.toString();
  }


  /**
   * This method will get you the builder of this controller.
   *
   * @return a instance of this controller's builder
   */
  public static StockControllerBuilderImpl getBuilder() {
    return new StockControllerBuilderImpl();
  }

  public static class StockControllerBuilderImpl {

    private StockModel model;
    private IView mainView;
    private IView createView;
    private IView getAllStateView;
    private IView getStateView;
    private IView determineCostView;
    private IView determineFeeView;
    private IView determineValueView;
    private IView buyView;
    private IView buyPercentageView;
    private IView buyAmountView;
    private IView createFixedView;
    private IView createPercentageView;
    private IView savePortfolioView;
    private IView savePercentageView;
    private IView loadPortfolioView;
    private IView loadPercentageView;

    /**
     * This method will initialize the builder.
     *
     * @return a instance of this controller's builder
     */
    private StockControllerBuilderImpl() {
      this.mainView = null;
      this.createView = null;
      this.getAllStateView = null;
      this.getStateView = null;
      this.determineCostView = null;
      this.determineFeeView = null;
      this.determineValueView = null;
      this.buyView = null;
      this.buyPercentageView = null;
      this.buyAmountView = null;
      this.createFixedView = null;
      this.createPercentageView = null;
      this.savePortfolioView = null;
      this.savePercentageView = null;
      this.loadPortfolioView = null;
      this.loadPercentageView = null;
    }

    /**
     * This method will set up the model in the controller.
     *
     * @param model the model you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl model(StockModel model) {
      if (model == null) {
        throw new IllegalArgumentException("model should not be null.");

      }
      this.model = model;
      return this;
    }


    /**
     * This method will set up the main view in the controller.
     *
     * @param mainView the mainView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl mainView(IView mainView) {
      if (mainView == null) {
        throw new IllegalArgumentException("mainView should not be null.");

      }
      this.mainView = mainView;
      return this;
    }

    /**
     * This method will set up the createView in the controller.
     *
     * @param createView the createView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl createView(IView createView) {
      if (createView == null) {
        throw new IllegalArgumentException("createView should not be null.");

      }
      this.createView = createView;
      return this;
    }

    /**
     * This method will set up the getAllStateView in the controller.
     *
     * @param getAllStateView the getAllStateView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl getAllStateView(IView getAllStateView) {
      if (getAllStateView == null) {
        throw new IllegalArgumentException("getAllStateView should not be null.");

      }
      this.getAllStateView = getAllStateView;
      return this;
    }

    /**
     * This method will set up the getStateView in the controller.
     *
     * @param getStateView the getStateView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl getStateView(IView getStateView) {
      if (getStateView == null) {
        throw new IllegalArgumentException("getStateView should not be null.");

      }
      this.getStateView = getStateView;
      return this;
    }

    /**
     * This method will set up the determineCostView in the controller.
     *
     * @param determineCostView the determineCostView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl determineCostView(IView determineCostView) {
      if (determineCostView == null) {
        throw new IllegalArgumentException("determineCostView should not be null.");

      }
      this.determineCostView = determineCostView;
      return this;
    }

    /**
     * This method will set up the determineFeeView in the controller.
     *
     * @param determineFeeView the determineFeeView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl determineFeeView(IView determineFeeView) {
      if (determineFeeView == null) {
        throw new IllegalArgumentException("determineFeeView should not be null.");

      }
      this.determineFeeView = determineFeeView;
      return this;
    }

    /**
     * This method will set up the determineValueView in the controller.
     *
     * @param determineValueView the determineValueView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl determineValueView(IView determineValueView) {
      if (determineValueView == null) {
        throw new IllegalArgumentException("determineValueView should not be null.");

      }
      this.determineValueView = determineValueView;
      return this;
    }

    /**
     * This method will set up the buyView in the controller.
     *
     * @param buyView the buyView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl buyView(IView buyView) {
      if (buyView == null) {
        throw new IllegalArgumentException("buyView should not be null.");

      }
      this.buyView = buyView;
      return this;
    }

    /**
     * This method will set up the buyPercentageView in the controller.
     *
     * @param buyPercentageView the buyPercentageView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl buyPercentageView(IView buyPercentageView) {
      if (buyPercentageView == null) {
        throw new IllegalArgumentException("buyPercentageView should not be null.");

      }
      this.buyPercentageView = buyPercentageView;
      return this;
    }

    /**
     * This method will set up the buyAmountView in the controller.
     *
     * @param buyAmountView the buyAmountView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl buyAmountView(IView buyAmountView) {
      if (buyAmountView == null) {
        throw new IllegalArgumentException("buyAmountView should not be null.");

      }
      this.buyAmountView = buyAmountView;
      return this;
    }

    /**
     * This method will set up the createFixedView in the controller.
     *
     * @param createFixedView the createFixedView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl createFixedView(IView createFixedView) {
      if (createFixedView == null) {
        throw new IllegalArgumentException("createFixedView should not be null.");

      }
      this.createFixedView = createFixedView;
      return this;
    }

    /**
     * This method will set up the createPercentageView in the controller.
     *
     * @param createPercentageView the createPercentageView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl createPercentageView(IView createPercentageView) {
      if (createPercentageView == null) {
        throw new IllegalArgumentException("createPercentageView should not be null.");

      }
      this.createPercentageView = createPercentageView;
      return this;
    }

    /**
     * This method will set up the savePortfolioView in the controller.
     *
     * @param savePortfolioView the savePortfolioView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl savePortfolioView(IView savePortfolioView) {
      if (savePortfolioView == null) {
        throw new IllegalArgumentException("savePortfolioView should not be null.");

      }
      this.savePortfolioView = savePortfolioView;
      return this;
    }

    /**
     * This method will set up the savePercentageView in the controller.
     *
     * @param savePercentageView the savePercentageView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl savePercentageView(IView savePercentageView) {
      if (savePercentageView == null) {
        throw new IllegalArgumentException("savePercentageView should not be null.");

      }
      this.savePercentageView = savePercentageView;
      return this;
    }

    /**
     * This method will set up the loadPortfolioView in the controller.
     *
     * @param loadPortfolioView the loadPortfolioView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl loadPortfolioView(IView loadPortfolioView) {
      if (loadPortfolioView == null) {
        throw new IllegalArgumentException("loadPortfolioView should not be null.");

      }
      this.loadPortfolioView = loadPortfolioView;
      return this;
    }

    /**
     * This method will set up the loadPercentageView in the controller.
     *
     * @param loadPercentageView the loadPercentageView you want to use.
     * @return a builder implementation
     */
    public StockControllerBuilderImpl loadPercentageView(IView loadPercentageView) {
      if (loadPercentageView == null) {
        throw new IllegalArgumentException("loadPercentageView should not be null.");

      }
      this.loadPercentageView = loadPercentageView;
      return this;
    }

    /**
     * This method will build the controller for you. This is the builder pattern.
     *
     * @return a StockController build by the builder.
     */
    public StockController build() throws Exception {
      return new StockControllerImpl(this.model, this.mainView, this.createView,
              this.getAllStateView, this.getStateView, this.determineCostView,
              this.determineFeeView, this.determineValueView, this.buyView, this.buyPercentageView,
              this.buyAmountView, this.createFixedView, this.createPercentageView,
              this.savePortfolioView, this.savePercentageView, this.loadPortfolioView,
              this.loadPercentageView);
    }
  }
}
