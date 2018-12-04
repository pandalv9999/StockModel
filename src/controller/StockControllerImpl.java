package controller;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
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
import view.KeyboardListener;
import view.LoadPercentageView;
import view.LoadPortfolioView;
import view.SavePercentageView;
import view.SavePortfolioView;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;

/**
 * This class represents an implementation of a stock controller.
 */
public class StockControllerImpl implements StockController {
  private StockModel model;
  private IView view, mainView, createView, getAllStateView, getStateView, determineCostView,
          determineFeeView, determineValueView, buyView, buyPercentageView, buyAmountView,
          createFixedView, createPercentageView, savePortfolioView, savePercentageView,
          loadPortfolioView, loadPercentageView;

//  /**
//   * This method will take a scanner object as an input and get the string separated by blank space
//   * or \n.
//   *
//   * @param scan a scanner object
//   * @return a string input
//   */
//  private String input(Scanner scan, String cue) throws IllegalStateException {
//    String st = "";
//    try {
//      st = scan.next();
//    } catch (NoSuchElementException e) {
//      throw new IllegalStateException("Not enough parameters. " + cue);
//    }
//    return st;
//  }

//  /**
//   * This method will take a String object as an input and output the string to the output stream.
//   *
//   * @param st a string needed to output
//   */
//  private void output(String st) throws IllegalStateException {
//    try {
//      this.out.append(st);
//    } catch (IOException e) {
//      throw new IllegalStateException("Output fails.");
//    }
//  }

//  /**
//   * This method is a helper function. It can convert a date string from certain format to the
//   * paradigm format. If it fails to convert, it will return an empty string.
//   *
//   * @param paradigm the format of date string we need
//   * @param format   the format of the inputting string
//   * @param date     the date string
//   * @return the result of converting
//   */
//  private String convertDate(SimpleDateFormat paradigm, SimpleDateFormat format, String date) {
//    String res = "";
//    try {
//      format.setLenient(false);
//      Date curDate = format.parse(date);
//      date = paradigm.format(curDate);
//      res = date;
//    } catch (ParseException e) {
//      return res;
//      // This does nothing.
//    }
//    return res;
//  }

//  /**
//   * This method is used to input a date string and it will convert it to the paradigm format.
//   *
//   * @param scan a scanner object
//   * @return a paradigm date string or N/n/Q/q
//   */
//  private String inputDate(Scanner scan, String cue) throws IllegalArgumentException {
//    String date = input(scan, cue);
//    if (date.equals("N") || date.equals("n") || date.equals("Q") || date.equals("q")) {
//      return date;
//    }
//    SimpleDateFormat paradigm = new SimpleDateFormat("yyyy-MM-dd");
//    SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
//    SimpleDateFormat format2 = new SimpleDateFormat("yyyy:MM:dd");
//    SimpleDateFormat format3 = new SimpleDateFormat("yyyy.MM.dd");
//    SimpleDateFormat format4 = new SimpleDateFormat("yyyyMMdd");
//    SimpleDateFormat format5 = new SimpleDateFormat("yyyy,MM,dd");
//
//    try {
//      paradigm.setLenient(false);
//      paradigm.parse(date);
//      return date;
//    } catch (ParseException e) {
//      // This does nothing.
//    }
//
//    String res = "";
//
//    res = convertDate(paradigm, format1, date);
//    if (!res.equals("")) {
//      return res;
//    }
//
//    res = convertDate(paradigm, format2, date);
//    if (!res.equals("")) {
//      return res;
//    }
//
//    res = convertDate(paradigm, format3, date);
//    if (!res.equals("")) {
//      return res;
//    }
//
//    res = convertDate(paradigm, format4, date);
//    if (!res.equals("")) {
//      return res;
//    }
//
//    res = convertDate(paradigm, format5, date);
//    if (!res.equals("")) {
//      return res;
//    }
//
//    throw new IllegalArgumentException("Invalid date.");
//  }

  /**
   * The constructor of this method. It will take in a inStream and outStream and initialize the
   * controller.
   */
  private StockControllerImpl(StockModel m, IView mainView, IView createView, IView getAllStateView,
                              IView getStateView, IView determineCostView, IView determineFeeView,
                              IView determineValueView, IView buyView, IView buyPercentageView,
                              IView buyAmountView, IView createFixedView, IView createPercentageView,
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
//    if (rd == null || ap == null) {
//      throw new IllegalArgumentException("Input and output should not be null.");
//    }
//    this.in = rd;
//    this.out = ap;
  }

  public void setView(IView v) {
    view = v;
    //create and set the keyboard listener
  }

  private void configureKeyBoardListener() {
    // This method will do nothing.
  }

  // add some action here
  private void configureButtonListener() throws Exception{
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
      System.out.println("haha");
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
      System.out.println("haha2");
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

//    buttonClickedMap.put("GetAllState Echo Button", () -> {
//      String command = view.getInputString();
//      view.setEchoOutput(processCommand("getallstate"));
//
//      //clear input textfield
//      view.clearInputString();
//
//      //set focus back to main frame so that keyboard events work
//      view.resetFocus();
//
//    });

    buttonClickedMap.put("GetAllState Exit Button", () -> {
      this.setView(this.mainView);
      ((JFrameView) this.mainView).setVisible(true);
      ((GetAllStateView) this.getAllStateView).setVisible(false);
    });


    //DetermineCost frame
    buttonClickedMap.put("DetermineCost Button", () -> {
      System.out.println("haha2");
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
      System.out.println("haha2");
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
      System.out.println("haha2");
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
      String portfolioName = ((DetermineValueView)view).getPlotInputString();
      try {
        ((DetermineValueView) view).plot(PlotValue.plotValue(model, portfolioName));
        view.setEchoOutput("Plot the value of all stocks in this portfolio in the last 12 months.\n");
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
      System.out.println("haha2");
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
      System.out.println("haha2");
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
      System.out.println("haha2");
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
      System.out.println("haha2");
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
      System.out.println("haha2");
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
      System.out.println("SavePercentage Echo Button");
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
      System.out.println("LoadPortfolio Echo Button");
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
      System.out.println("LoadPercentage Echo Button");
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

//  /**
//   * This method will determine whether to quit or not.
//   *
//   * @param st the string to be predicated
//   * @return whether the string is a quitting message
//   */
//  private boolean isQuit(String st) {
//    return st.equals("q") || st.equals("Q");
//  }

  /**
   * This method is the main method of the controller. It takes a model as the parameter and call
   * its methods.
   */
  public String processCommand(String command)
          throws IllegalArgumentException, IllegalStateException, IOException {
    System.out.println(command);
    if (model == null) {
      throw new IllegalArgumentException("Model should not be null.");
    }

    Scanner scan = new Scanner(command);
    StringBuilder output = new StringBuilder();
    String cmd = null;

//    output("Welcome to the stock trading system.\n");
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

  //

//  /**
//   * This method will create a fixed portfolio according to the inputting message.
//   *
//   * @param model a stock system model
//   * @param scan  a scanner object
//   */
//  private void createFixed(StockModel model, Scanner scan, StringBuilder output) {
//    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
//    if (Input.isQuit(portfolioName)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    String equal = Input.input(scan, "Equal proportion or separate?(E/S)\n");
//    if (Input.isQuit(equal)) {
//      output.append("Quit.\n");
//      return;
//    }
//    Map<String, Double> company = new HashMap<>();
//    int n;
//    try {
//      n = Integer.parseInt(Input.input(scan, "Number of companies?\n"));
//    } catch (IllegalArgumentException e) {
//      throw new IllegalArgumentException("Illegal number.");
//    }
//    for (int i = 0; i < n; i++) {
//      String companyName = Input.input(scan, "Name of company?\n");
//
//      if (Input.isQuit(companyName)) {
//        output.append("Quit.\n");
//        return;
//      }
//      double proportion = 1.0 / n;
//      if (equal.equals("S") || equal.equals("s")) {
//        try {
//          proportion = Double.parseDouble(Input.input(scan,
//                  "Proportion in percentage? E.g. input 30 to represent 30%.\n")) / 100.0;
//          if (proportion < 0) {
//            throw new IllegalArgumentException("Proportion should be larger than 0.");
//
//          }
//        } catch (IllegalArgumentException e) {
//          throw new IllegalArgumentException("Illegal number.");
//        }
//      }
//      company.put(companyName, proportion);
//    }
//
//    double amount = 0.0;
//    try {
//      amount = Double.parseDouble(Input.input(scan, "Amount of investment?\n"));
//    } catch (IllegalArgumentException e) {
//      throw new IllegalArgumentException("Illegal number.");
//    }
//    if (Input.isQuit(portfolioName)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    String onGoing = Input.input(scan, "Ongoing? Y/N\n");
//    if (Input.isQuit(onGoing)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    String startDate;
//    String endDate = "N";
//    if (onGoing.equals("Y") || onGoing.equals("y")) {
//
//      // startDate = input(scan);
//      startDate = Input.inputDate(scan, "Provide a start date?(yyyy-mm-dd / N/n)\n");
//      if (Input.isQuit(startDate)) {
//        output.append("Quit.\n");
//        return;
//      }
//
//      if (!startDate.equals("N") && !startDate.equals("n")) {
//        // endDate = input(scan);
//        endDate = Input.inputDate(scan, "Provide a end date?(yyyy-mm-dd / N/n)\n");
//        if (Input.isQuit(endDate)) {
//          output.append("Quit.\n");
//          return;
//        }
//      }
//
//      int interval = 30;
//      if (!startDate.equals("N") && !startDate.equals("n")) {
//        String st = Input.input(scan, "Provide a interval in days?(e.g 15, 30, 60 / N/n)\n");
//        if (Input.isQuit(st)) {
//          output.append("Quit.\n");
//          return;
//        }
//        if (!st.equals("n") && !st.equals("N")) {
//          try {
//            interval = Integer.parseInt(st);
//          } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Illegal number.");
//          }
//        }
//      }
//
//      try {   // better to add a things to ask what should be the interval. 30 days, 60 days. Done.
//        model.dollarCostAverage(portfolioName, company, amount, startDate, endDate, interval);
//      } catch (IllegalArgumentException e) {
//        output.append(e.getMessage());
//        output.append("\n");
//        return;
//      }
//    } else {
//      // String date = input(scan);
//      String date = Input.inputDate(scan, "Provide a buying date?(yyyy-mm-dd / N/n)\n");
//      if (Input.isQuit(date)) {
//        output.append("Quit.\n");
//        return;
//      }
//
//      try {
//        model.createPortfolio(portfolioName, company, amount, date);
//      } catch (IllegalArgumentException e) {
//        output.append(e.getMessage());
//        output.append("\n");
//        return;
//      }
//    }
//    Double cost = model.determineCost(portfolioName);
//    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
//    output.append("Created a Fixed portfolio successfully " + "with commission fee $"
//            + Double.toString(commissionFeeAfter) + "\n");
//    output.append("The total cost is $"
//            + Double.toString(cost
//            + commissionFeeAfter) + "\n");
//    output.append("Commission fee is of "
//            + Double.toString(commissionFeeAfter
//            / (cost + commissionFeeAfter)
//            * 100.0)
//            + "%\n");
//  }

  //

//  /**
//   * This method will create an empty portfolio.
//   *
//   * @param model a stock system model
//   * @param scan  a Scanner object
//   */
//  private void create(StockModel model, Scanner scan, StringBuilder output) {
//    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
//    if (Input.isQuit(portfolioName)) {
//      output.append("Quit.\n");
//      return;
//    }
//    try {
//      model.createPortfolio(portfolioName);
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      return;
//    }
//    output.append("Created an empty portfolio successfully.\n");
//  }

//  private String create(String portfolioName) {
//    try {
//      model.createPortfolio(portfolioName);
//    } catch (IllegalArgumentException e) {
//      return e.getMessage();
//    }
//    return "Created an empty portfolio successfully.\n";
//  }
//

//  /**
//   * This method will buy a certain stock into a portfolio.
//   *
//   * @param model a stock system model
//   * @param scan  a Scanner object
//   */
//  private void buy(StockModel model, Scanner scan, StringBuilder output) {
//    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
//    if (Input.isQuit(portfolioName)) {
//      output.append("Quit.\n");
//      return;
//    }
//    String companyName = Input.input(scan, "Please input the company's name.\n");
//    if (Input.isQuit(companyName)) {
//      output.append("Quit.\n");
//      return;
//    }
//    int shares = 0;
//    try {
//      String st = Input.input(scan, "How many shares you want to buy?\n");
//      if (Input.isQuit(st)) {
//        output.append("Quit.\n");
//        return;
//      }
//      shares = Integer.parseInt(st);
//    } catch (Exception e) {
//      output.append("Invalid shares, input again.\n");
//      return;
//    }
//
//    // String date = input(scan);
//    String date = Input.inputDate(scan,
//            "Please input the date you want to buy in format (yyyy-mm-dd/ N/n).\n");
//    if (Input.isQuit(date)) {
//      output.append("Quit.\n");
//      return;
//    }
//    Double res = 0.0;
//
//    Double commissionFeeBefore = model.determineCommissionFee(portfolioName);
//    try {
//      res = model.buy(portfolioName, companyName, shares, date, "low");
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    if (date.equals("n") || date.equals("N")) {
//      date = "the closest transaction date";
//    }
//    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
//    output.append("Successfully bought " + companyName + " with " + shares + " shares on " + date
//            + " and total cost is $"
//            + Double.toString(res + commissionFeeAfter - commissionFeeBefore)
//            + " with commission fee $"
//            + Double.toString(commissionFeeAfter - commissionFeeBefore) + "\n");
//    output.append("Commission fee is of "
//            + Double.toString((commissionFeeAfter - commissionFeeBefore)
//            / (res + commissionFeeAfter - commissionFeeBefore)
//            * 100.0)
//            + "%\n");
//  }


//  /**
//   * This method will buy a certain stock into a portfolio.
//   *
//   * @param model a stock system model
//   * @param scan  a Scanner object
//   */
//  private void buyByAmount(StockModel model, Scanner scan, StringBuilder output) {
//    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
//    if (Input.isQuit(portfolioName)) {
//      output.append("Quit.\n");
//      return;
//    }
//    String companyName = Input.input(scan, "Please input the company's name.\n");
//    if (Input.isQuit(companyName)) {
//      output.append("Quit.\n");
//      return;
//    }
//    double amount = 0;
//    try {
//      String st = Input.input(scan, "What is the amount of money you want to invest?\n");
//      if (Input.isQuit(st)) {
//        output.append("Quit.\n");
//        return;
//      }
//      amount = Double.parseDouble(st);
//    } catch (Exception e) {
//      output.append("Invalid shares, input again.\n");
//      return;
//    }
//
//    // String date = input(scan);
//    String date = Input.inputDate(scan,
//            "Please input the date you want to buy in format (yyyy-mm-dd/ N/n).\n");
//    if (Input.isQuit(date)) {
//      output.append("Quit.\n");
//      return;
//    }
//    Double res = 0.0;
//
//    Double commissionFeeBefore = model.determineCommissionFee(portfolioName);
//    try {
//      res = model.buyByAmount(portfolioName, companyName, amount, date);
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
//
//    if (date.equals("n") || date.equals("N")) {
//      date = "the closest transaction date";
//    }
//    output.append("Successfully bought " + companyName + " with $" + amount + " on " + date
//            + " and total cost is $"
//            + Double.toString(res + commissionFeeAfter - commissionFeeBefore)
//            + " with commission fee $"
//            + Double.toString(commissionFeeAfter - commissionFeeBefore) + "\n");
//    output.append("Commission fee is of "
//            + Double.toString((commissionFeeAfter - commissionFeeBefore)
//            / (res + commissionFeeAfter - commissionFeeBefore)
//            * 100.0)
//            + "%\n");
//  }


//  /**
//   * This method will try to determine a portfolio's cost basis and its commission fee.
//   *
//   * @param model a stock system model
//   * @param scan  a Scanner object
//   */
//  private void determineCost(StockModel model, Scanner scan, StringBuilder output) {
//    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
//    if (Input.isQuit(portfolioName)) {
//      output.append("Quit.\n");
//      return;
//    }
//    Double res = 0.0;
//    try {
//      res = model.determineCost(portfolioName);
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
//    output.append("The cost basis of buying in " + portfolioName + " is $" + Double.toString(res)
//            + " with commission fee $"
//            + Double.toString(commissionFeeAfter)
//            + "\n");
//  }

//  /**
//   * This method will determine a portfolio in a certain date.
//   *
//   * @param model a stock system model
//   * @param scan  a Scanner object
//   */
//  private void determineValue(StockModel model, Scanner scan, StringBuilder output) {
//    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
//    if (Input.isQuit(portfolioName)) {
//      output.append("Quit.\n");
//      return;
//    }
//    String date = Input.inputDate(scan, "Please input the date you want to check in format yyyy-mm-dd.\n");
//    if (Input.isQuit(date)) {
//      output.append("Quit.\n");
//      return;
//    }
//    Double res = 0.0;
//    try {
//      res = model.determineValue(portfolioName, date);
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    if (date.equals("n") || date.equals("N")) {
//      date = "the closest transaction date";
//    }
//    output.append("The value of all stocks in this portfolio on " + date + " is $"
//            + Double.toString(res) + "\n");
//  }

//  /**
//   * This method will determine the commission fee of a portfolio.
//   *
//   * @param model a stock system model
//   * @param scan  a Scanner object
//   */
//  private void determineCommissionFee(StockModel model, Scanner scan, StringBuilder output) {
//    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
//    if (Input.isQuit(portfolioName)) {
//      output.append("Quit.\n");
//      return;
//    }
//    Double res = 0.0;
//    try {
//      res = model.determineCommissionFee(portfolioName);
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    output.append("The fee of all transactions in this portfolio is $"
//            + Double.toString(res) + "\n");
//  }

//  /**
//   * This method will buy a fixed portfolio in a certain date according to its percentage of each
//   * stock.
//   *
//   * @param model a stock system model
//   * @param scan  a Scanner object
//   */
//  private void buyByPercentage(StockModel model, Scanner scan, StringBuilder output) {
//    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
//    if (Input.isQuit(portfolioName)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    String percentagesName = Input.input(scan, "Please input the investment plan's name.\n");
//    if (Input.isQuit(percentagesName)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    double amount = 0.0;
//    try {
//      String st = Input.input(scan, "Amount of investment?\n");
//      if (Input.isQuit(st)) {
//        output.append("Quit.\n");
//        return;
//      }
//      amount = Double.parseDouble(st);
//    } catch (IllegalArgumentException e) {
//      throw new IllegalArgumentException("Illegal number");
//    }
//
//    // String date = input(scan);
//    String date = Input.inputDate(scan,
//            "Please input the date you want to buy in format yyyy-mm-dd/N/n.\n");
//    if (Input.isQuit(date)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    Double res = 0.0;
//    Double commissionFeeBefore;
//    try {
//      commissionFeeBefore = model.determineCommissionFee(portfolioName);
//      res = model.buyByPercentage(portfolioName, percentagesName, amount, date);
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
//    output.append("The cost of buying this portfolio is $"
//            + Double.toString(res + commissionFeeAfter - commissionFeeBefore)
//            + " with commission fee $"
//            + Double.toString(commissionFeeAfter - commissionFeeBefore) + "\n");
//    output.append("Commission fee is of "
//            + Double.toString((commissionFeeAfter - commissionFeeBefore)
//            / (res + commissionFeeAfter - commissionFeeBefore)
//            * 100.0)
//            + "% in this transaction.\n");
//  }


//  private void createPercentage(StockModel model, Scanner scan, StringBuilder output) {
//
//    String percentagesName = Input.input(scan, "Please input the investment plan's name.\n");
//    if (Input.isQuit(percentagesName)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    String equal = Input.input(scan, "Equal proportion or separate?(E/S)\n");
//    if (Input.isQuit(equal)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    Map<String, Double> company = new HashMap<>();
//    int n;
//    try {
//      n = Integer.parseInt(Input.input(scan, "Number of companies?\n"));
//    } catch (IllegalArgumentException e) {
//      throw new IllegalArgumentException("Illegal number.");
//    }
//    for (int i = 0; i < n; i++) {
//      String companyName = Input.input(scan, "Name of company?\n");
//
//      if (Input.isQuit(companyName)) {
//        output.append("Quit.\n");
//        return;
//      }
//      double proportion = 1.0 / n;
//      if (equal.equals("S") || equal.equals("s")) {
//        try {
//          proportion = Double.parseDouble(Input.input(scan,
//                  "Proportion in percentage? E.g. input 30 to represent 30%.\n")) / 100.0;
//
//          if (proportion < 0) {
//            throw new IllegalArgumentException("Proportion should be larger than 0.");
//
//          }
//        } catch (IllegalArgumentException e) {
//          throw new IllegalArgumentException("Illegal number.");
//        }
//      }
//      company.put(companyName, proportion);
//    }
//
//    Double res = 0.0;
//    try {
//      model.createPercentage(percentagesName, company);
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    output.append("Successfully created investing plan " + percentagesName + ".");
//  }

//  /**
//   * This method will get the state of a portfolio.
//   *
//   * @param model a stock system model
//   * @param scan  a Scanner object
//   */
//  private void getState(StockModel model, Scanner scan, StringBuilder output) {
//
//    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
//    if (Input.isQuit(portfolioName)) {
//      output.append("Quit.\n");
//      return;
//    }
//    String res = "";
//    try {
//      res = model.getPortfolioState(portfolioName);
//    } catch (Exception e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    output.append("The state of " + portfolioName + "\n");
//    output.append(res);
//  }


//  /**
//   * This method will get the state of every portfolio.
//   *
//   * @param model a stock system model
//   */
//  private void getAllState(StockModel model, StringBuilder output) {
//
//    String res = "";
//    try {
//      res = model.getPortfolioState();
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    output.append("The state of all portfolios:\n");
//    output.append(res + "\n");
//  }


//  private void savePortfolio(StockModel model, Scanner scan, StringBuilder output) {
//
//    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
//    if (Input.isQuit(portfolioName)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    String fileName = Input.input(scan, "Please input the file's name.\n");
//    if (Input.isQuit(fileName)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    try {
//      model.savePortfolio(portfolioName, fileName);
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    output.append("Save portfolio ").append(portfolioName).append(" in file ").append(fileName).append(".csv.\n");
//  }

//  private void savePercentage(StockModel model, Scanner scan, StringBuilder output) {
//
//    String percentageName = Input.input(scan, "Please input the portfolio's name.\n");
//    if (Input.isQuit(percentageName)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    String fileName = Input.input(scan, "Please input the file's name.\n");
//    if (Input.isQuit(fileName)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    try {
//      model.savePercentage(percentageName, fileName);
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    output.append("Save percentage ").append(percentageName).append(" in file ").append(fileName).append(".csv.\n");
//  }


//  private void loadPortfolio(StockModel model, Scanner scan, StringBuilder output) {
//
//    String fileName = Input.input(scan, "Please input the file's name.\n");
//    if (Input.isQuit(fileName)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    try {
//      model.loadPortfolio(fileName);
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    output.append("Load portfolio from ").append(fileName).append(".csv.\n");
//  }

//  private void loadPercentage(StockModel model, Scanner scan, StringBuilder output) {
//
//    String fileName = input(scan, "Please input the file's name.\n");
//    if (isQuit(fileName)) {
//      output.append("Quit.\n");
//      return;
//    }
//
//    try {
//      model.loadPercentage(fileName);
//    } catch (IllegalArgumentException e) {
//      output.append(e.getMessage());
//      output.append("\n");
//      return;
//    }
//    output.append("Load percentage from ").append(fileName).append(".csv.\n");
//  }


  public static StockControllerBuilderImpl getBuilder() {
    return new StockControllerBuilderImpl();
  }

  public static class StockControllerBuilderImpl {

    private IView mainView, createView, getAllStateView, getStateView, determineCostView,
            determineFeeView, determineValueView, buyView, buyPercentageView, buyAmountView,
            createFixedView, createPercentageView, savePortfolioView, savePercentageView,
            loadPortfolioView, loadPercentageView;
    private StockModel model;

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

    public StockControllerBuilderImpl model(StockModel model) {
      if (model == null) {
        throw new IllegalArgumentException("model should not be null.");

      }
      this.model = model;
      return this;
    }

    public StockControllerBuilderImpl mainView(IView mainView) {
      if (mainView == null) {
        throw new IllegalArgumentException("mainView should not be null.");

      }
      this.mainView = mainView;
      return this;
    }

    public StockControllerBuilderImpl createView(IView createView) {
      if (createView == null) {
        throw new IllegalArgumentException("createView should not be null.");

      }
      this.createView = createView;
      return this;
    }

    public StockControllerBuilderImpl getAllStateView(IView getAllStateView) {
      if (getAllStateView == null) {
        throw new IllegalArgumentException("getAllStateView should not be null.");

      }
      this.getAllStateView = getAllStateView;
      return this;
    }

    public StockControllerBuilderImpl getStateView(IView getStateView) {
      if (getStateView == null) {
        throw new IllegalArgumentException("getStateView should not be null.");

      }
      this.getStateView = getStateView;
      return this;
    }

    public StockControllerBuilderImpl determineCostView(IView determineCostView) {
      if (determineCostView == null) {
        throw new IllegalArgumentException("determineCostView should not be null.");

      }
      this.determineCostView = determineCostView;
      return this;
    }

    public StockControllerBuilderImpl determineFeeView(IView determineFeeView) {
      if (determineFeeView == null) {
        throw new IllegalArgumentException("determineFeeView should not be null.");

      }
      this.determineFeeView = determineFeeView;
      return this;
    }

    public StockControllerBuilderImpl determineValueView(IView determineValueView) {
      if (determineValueView == null) {
        throw new IllegalArgumentException("determineValueView should not be null.");

      }
      this.determineValueView = determineValueView;
      return this;
    }

    public StockControllerBuilderImpl buyView(IView buyView) {
      if (buyView == null) {
        throw new IllegalArgumentException("buyView should not be null.");

      }
      this.buyView = buyView;
      return this;
    }

    public StockControllerBuilderImpl buyPercentageView(IView buyPercentageView) {
      if (buyPercentageView == null) {
        throw new IllegalArgumentException("buyPercentageView should not be null.");

      }
      this.buyPercentageView = buyPercentageView;
      return this;
    }

    public StockControllerBuilderImpl buyAmountView(IView buyAmountView) {
      if (buyAmountView == null) {
        throw new IllegalArgumentException("buyAmountView should not be null.");

      }
      this.buyAmountView = buyAmountView;
      return this;
    }

    public StockControllerBuilderImpl createFixedView(IView createFixedView) {
      if (createFixedView == null) {
        throw new IllegalArgumentException("createFixedView should not be null.");

      }
      this.createFixedView = createFixedView;
      return this;
    }

    public StockControllerBuilderImpl createPercentageView(IView createPercentageView) {
      if (createPercentageView == null) {
        throw new IllegalArgumentException("createPercentageView should not be null.");

      }
      this.createPercentageView = createPercentageView;
      return this;
    }

    public StockControllerBuilderImpl savePortfolioView(IView savePortfolioView) {
      if (savePortfolioView == null) {
        throw new IllegalArgumentException("savePortfolioView should not be null.");

      }
      this.savePortfolioView = savePortfolioView;
      return this;
    }

    public StockControllerBuilderImpl savePercentageView(IView savePercentageView) {
      if (savePercentageView == null) {
        throw new IllegalArgumentException("savePercentageView should not be null.");

      }
      this.savePercentageView = savePercentageView;
      return this;
    }

    public StockControllerBuilderImpl loadPortfolioView(IView loadPortfolioView) {
      if (loadPortfolioView == null) {
        throw new IllegalArgumentException("loadPortfolioView should not be null.");

      }
      this.loadPortfolioView = loadPortfolioView;
      return this;
    }

    public StockControllerBuilderImpl loadPercentageView(IView loadPercentageView) {
      if (loadPercentageView == null) {
        throw new IllegalArgumentException("loadPercentageView should not be null.");

      }
      this.loadPercentageView = loadPercentageView;
      return this;
    }

    public StockController build() throws Exception {
      return new StockControllerImpl(this.model, this.mainView, this.createView, this.getAllStateView,
              this.getStateView, this.determineCostView, this.determineFeeView,
              this.determineValueView, this.buyView, this.buyPercentageView,
              this.buyAmountView, this.createFixedView, this.createPercentageView,
              this.savePortfolioView, this.savePercentageView, this.loadPortfolioView,
              this.loadPercentageView);
    }
  }
}
