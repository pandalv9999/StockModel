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

import model.StockModel;
import view.ButtonListener;
import view.IView;
import view.KeyboardListener;

/**
 * This class represents an implementation of a stock controller.
 */
public class StockControllerImpl implements StockController {
  private StockModel model;
  private IView view;

  /**
   * This method will take a scanner object as an input and get the string separated by blank space
   * or \n.
   *
   * @param scan a scanner object
   * @return a string input
   */
  private String input(Scanner scan) throws IllegalStateException {
    String st = "";
    try {
      st = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Not enough parameters");
    }
    return st;
  }

  /**
   * This method will take a String object as an input and output the string to the output stream.
   *
   * @param st a string needed to output
   */
//  private void output(String st) throws IllegalStateException {
//    try {
//      this.out.append(st);
//    } catch (IOException e) {
//      throw new IllegalStateException("Output fails.");
//    }
//  }

  /**
   * This method is a helper function. It can convert a date string from certain format to the
   * paradigm format. If it fails to convert, it will return an empty string.
   *
   * @param paradigm the format of date string we need
   * @param format   the format of the inputting string
   * @param date     the date string
   * @return the result of converting
   */
  private String convertDate(SimpleDateFormat paradigm, SimpleDateFormat format, String date) {
    String res = "";
    try {
      format.setLenient(false);
      Date curDate = format.parse(date);
      date = paradigm.format(curDate);
      res = date;
    } catch (ParseException e) {
      return res;
      // This does nothing.
    }
    return res;
  }

  /**
   * This method is used to input a date string and it will convert it to the paradigm format.
   *
   * @param scan a scanner object
   * @return a paradigm date string or N/n/Q/q
   */
  private String inputDate(Scanner scan) throws IllegalArgumentException {
    String date = input(scan);
    if (date.equals("N") || date.equals("n") || date.equals("Q") || date.equals("q")) {
      return date;
    }
    SimpleDateFormat paradigm = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy:MM:dd");
    SimpleDateFormat format3 = new SimpleDateFormat("yyyy.MM.dd");
    SimpleDateFormat format4 = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat format5 = new SimpleDateFormat("yyyy,MM,dd");

    try {
      paradigm.setLenient(false);
      paradigm.parse(date);
      return date;
    } catch (ParseException e) {
      // This does nothing.
    }

    String res = "";

    res = convertDate(paradigm, format1, date);
    if (!res.equals("")) {
      return res;
    }

    res = convertDate(paradigm, format2, date);
    if (!res.equals("")) {
      return res;
    }

    res = convertDate(paradigm, format3, date);
    if (!res.equals("")) {
      return res;
    }

    res = convertDate(paradigm, format4, date);
    if (!res.equals("")) {
      return res;
    }

    res = convertDate(paradigm, format5, date);
    if (!res.equals("")) {
      return res;
    }

    throw new IllegalArgumentException("Invalid date.");
  }

  /**
   * The constructor of this method. It will take in a inStream and outStream and initialize the
   * controller.
   */
  public StockControllerImpl(StockModel m) throws IllegalArgumentException {
    this.model = m;
//    if (rd == null || ap == null) {
//      throw new IllegalArgumentException("Input and output should not be null.");
//    }
//    this.in = rd;
//    this.out = ap;
  }

  public void setView(IView v) {
    view = v;
    //create and set the keyboard listener
    configureKeyBoardListener();
    configureButtonListener();
  }

  private void configureKeyBoardListener() {
    // This method will do nothing.
  }

  // add some action here
  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("Echo Button", () -> {
      String command = view.getInputString();
      view.setEchoOutput(processCommand(command));

      //clear input textfield
      view.clearInputString();

      //set focus back to main frame so that keyboard events work
      view.resetFocus();

    });


    buttonClickedMap.put("Exit Button", () -> {
      System.exit(0);
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  /**
   * This method will determine whether to quit or not.
   *
   * @param st the string to be predicated
   * @return whether the string is a quitting message
   */
  private boolean isQuit(String st) {
    return st.equals("q") || st.equals("Q");
  }

  /**
   * This method is the main method of the controller. It takes a model as the parameter and call
   * its methods.
   */
  public String processCommand(String command)
          throws IllegalArgumentException, IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("Model should not be null.");
    }
    if (command.length() == 0) {
      throw new IllegalArgumentException("command should not be null.");
    }
    System.out.println(command);
    Scanner scan = new Scanner(command);
    StringBuilder output = new StringBuilder();
    String cmd = null;

//    output("Welcome to the stock trading system.\n");
    while (scan.hasNext()) {
      String in = input(scan).toLowerCase();

      if (isQuit(in)) {
        output.append("Quit.\n");
        return output.toString();
      }

      if (in.equals("createfixed")) {
        try {
          createFixed(model, scan, output);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      }

      if (in.equals("create")) {
        try {
          create(model, scan, output);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      }

      if (in.equals("buy")) {
        try {
          buy(model, scan, output);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      }

      if (in.equals("determinecost")) {
        try {
          determineCost(model, scan, output);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      }

      if (in.equals("determinevalue")) {
        try {
          determineValue(model, scan, output);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      }

      if (in.equals("determinefee")) {
        try {
          determineCommissionFee(model, scan, output);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      }

      if (in.equals("buyp")) {
        try {
          buyByPercentage(model, scan, output);
        } catch (IllegalArgumentException e) {
          output.append(e.getMessage());
        }
      }

      if (in.equals("getstate")) {
        try {
          getState(model, scan, output);
        } catch (IllegalArgumentException e) {
          output.append(e.getMessage());
        }
      }

      if (in.equals("getallstate")) {
        try {
          getAllState(model, output);
        } catch (Exception e) {
          output.append(e.getMessage());
        }
      }
    }
    return output.toString();
  }

  //

  /**
   * This method will create a fixed portfolio according to the inputting message.
   *
   * @param model a stock system model
   * @param scan  a scanner object
   */
  private void createFixed(StockModel model, Scanner scan, StringBuilder output) {
    output.append("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    output.append("Equal proportion or separate?(E/S)\n");
    String equal = input(scan);
    if (isQuit(equal)) {
      output.append("Quit.\n");
      return;
    }
    Map<String, Double> company = new HashMap<>();
    output.append("Number of companies?\n");
    int n;
    try {
      n = Integer.parseInt(input(scan));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal number.");
    }
    for (int i = 0; i < n; i++) {
      output.append("Name of company?\n");
      String companyName = input(scan);

      if (isQuit(companyName)) {
        output.append("Quit.\n");
        return;
      }
      double proportion = 1.0 / n;
      if (equal.equals("S") || equal.equals("s")) {
        output.append("Proportion in percentage? E.g. input 30 to represent 30%.\n");
        try {
          proportion = Double.parseDouble(input(scan)) / 100.0;
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Illegal number.");
        }
      }
      company.put(companyName, proportion);
    }

    double amount = 0.0;
    output.append("Amount of investment?\n");
    try {
      amount = Double.parseDouble(input(scan));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal number.");
    }
    if (isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }

    output.append("Ongoing? Y/N\n");
    String onGoing = input(scan);
    if (isQuit(onGoing)) {
      output.append("Quit.\n");
      return;
    }

    String startDate;
    String endDate = "N";
    if (onGoing.equals("Y") || onGoing.equals("y")) {

      output.append("Provide a start date?(yyyy-mm-dd / N/n)\n");
      // startDate = input(scan);
      startDate = inputDate(scan);
      if (isQuit(startDate)) {
        output.append("Quit.\n");
        return;
      }

      if (!startDate.equals("N") && !startDate.equals("n")) {
        output.append("Provide a end date?(yyyy-mm-dd / N/n)\n");
        // endDate = input(scan);
        endDate = inputDate(scan);
        if (isQuit(endDate)) {
          output.append("Quit.\n");
          return;
        }
      }

      int interval = 30;
      if (!startDate.equals("N") && !startDate.equals("n")) {
        output.append("Provide a interval in days?(e.g 15, 30, 60 / N/n)\n");
        String st = input(scan);
        if (isQuit(st)) {
          output.append("Quit.\n");
          return;
        }
        if (!st.equals("n") && !st.equals("N")) {
          try {
            interval = Integer.parseInt(st);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Illegal number.");
          }
        }
      }

      try {   // better to add a things to ask what should be the interval. 30 days, 60 days. Done.
        model.dollarCostAverage(portfolioName, company, amount, startDate, endDate, interval);
      } catch (IllegalArgumentException e) {
        output.append(e.getMessage());
        output.append("\n");
        throw new IllegalArgumentException();
      }
    } else {
      output.append("Provide a buying date?(yyyy-mm-dd / N/n)\n");
      // String date = input(scan);
      String date = inputDate(scan);
      if (isQuit(date)) {
        output.append("Quit.\n");
        return;
      }

      try {
        model.createPortfolio(portfolioName, company, amount, date);
      } catch (IllegalArgumentException e) {
        output.append(e.getMessage());
        output.append("\n");
        throw new IllegalArgumentException();
      }
    }
    Double cost = model.determineCost(portfolioName);
    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
    output.append("Created a Fixed portfolio successfully " + "with commission fee $"
            + Double.toString(commissionFeeAfter) + "\n");
    output.append("The total cost is $"
            + Double.toString(cost
            + commissionFeeAfter) + "\n");
    output.append("Commission fee is of "
            + Double.toString(commissionFeeAfter
            / (cost + commissionFeeAfter)
            * 100.0)
            + "%\n");
  }

  //

  /**
   * This method will create an empty portfolio.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  private void create(StockModel model, Scanner scan, StringBuilder output) {
    output.append("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    try {
      model.createPortfolio(portfolioName);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      return;
    }
    output.append("Created an empty portfolio successfully.\n");
  }

//  private String create(String portfolioName) {
//    try {
//      model.createPortfolio(portfolioName);
//    } catch (IllegalArgumentException e) {
//      return e.getMessage();
//    }
//    return "Created an empty portfolio successfully.\n";
//  }
//

  /**
   * This method will buy a certain stock into a portfolio.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  private void buy(StockModel model, Scanner scan, StringBuilder output) {
    output.append("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    output.append("Please input the company's name.\n");
    String companyName = input(scan);
    if (isQuit(companyName)) {
      output.append("Quit.\n");
      return;
    }
    output.append("How many shares you want to buy?\n");
    int shares = 0;
    try {
      String st = input(scan);
      if (isQuit(st)) {
        output.append("Quit.\n");
        return;
      }
      shares = Integer.parseInt(st);
    } catch (Exception e) {
      output.append("Invalid shares, input again.\n");
      return;
    }

    output.append("Please input the date you want to buy in format (yyyy-mm-dd/ N/n).\n");
    // String date = input(scan);
    String date = inputDate(scan);
    if (isQuit(date)) {
      output.append("Quit.\n");
      return;
    }
    Double res = 0.0;

    Double commissionFeeBefore = model.determineCommissionFee(portfolioName);
    try {
      res = model.buy(portfolioName, companyName, shares, date, "low");
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      throw new IllegalArgumentException();
    }
    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
    output.append("Successfully bought " + companyName + " with " + shares + " shares on " + date
            + " and total cost is $"
            + Double.toString(res + commissionFeeAfter - commissionFeeBefore)
            + " with commission fee $"
            + Double.toString(commissionFeeAfter - commissionFeeBefore) + "\n");
    output.append("Commission fee is of "
            + Double.toString((commissionFeeAfter - commissionFeeBefore)
            / (res + commissionFeeAfter - commissionFeeBefore)
            * 100.0)
            + "%\n");
  }


  /**
   * This method will try to determine a portfolio's cost basis and its commission fee.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  private void determineCost(StockModel model, Scanner scan, StringBuilder output) {
    output.append("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    Double res = 0.0;
    try {
      res = model.determineCost(portfolioName);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      throw new IllegalArgumentException();
    }
    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
    output.append("The cost basis of buying in " + portfolioName + " is $" + Double.toString(res)
            + " with commission fee $"
            + Double.toString(commissionFeeAfter)
            + "\n");
  }

  /**
   * This method will determine a portfolio in a certain date.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  private void determineValue(StockModel model, Scanner scan, StringBuilder output) {
    output.append("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    output.append("Please input the date you want to check in format yyyy-mm-dd.\n");
    String date = inputDate(scan);
    if (isQuit(date)) {
      output.append("Quit.\n");
      return;
    }
    Double res = 0.0;
    try {
      res = model.determineValue(portfolioName, date);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      throw new IllegalArgumentException();
    }
    output.append("The value of all stocks in this portfolio on " + date + " is $"
            + Double.toString(res) + "\n");
  }

  /**
   * This method will determine the commission fee of a portfolio.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  private void determineCommissionFee(StockModel model, Scanner scan, StringBuilder output) {
    output.append("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    Double res = 0.0;
    try {
      res = model.determineCommissionFee(portfolioName);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      throw new IllegalArgumentException();
    }
    output.append("The fee of all transactions in this portfolio is $"
            + Double.toString(res) + "\n");
  }

  /**
   * This method will buy a fixed portfolio in a certain date according to its percentage of each
   * stock.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  private void buyByPercentage(StockModel model, Scanner scan, StringBuilder output) {
    output.append("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }

    double amount = 0.0;
    output.append("Amount of investment?\n");
    try {
      String st = input(scan);
      if (isQuit(st)) {
        output.append("Quit.\n");
        return;
      }
      amount = Double.parseDouble(st);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal number");
    }

    output.append("Please input the date you want to buy in format yyyy-mm-dd/N/n.\n");
    // String date = input(scan);
    String date = inputDate(scan);
    if (isQuit(date)) {
      output.append("Quit.\n");
      return;
    }

    Double res = 0.0;
    Double commissionFeeBefore = model.determineCommissionFee(portfolioName);
    try {
      res = model.buyByPercentage(portfolioName, amount, date);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      throw new IllegalArgumentException();
    }
    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
    output.append("The cost of buying this portfolio is $"
            + Double.toString(res + commissionFeeAfter - commissionFeeBefore)
            + " with commission fee $"
            + Double.toString(commissionFeeAfter - commissionFeeBefore) + "\n");
    output.append("Commission fee is of "
            + Double.toString((commissionFeeAfter - commissionFeeBefore)
            / (res + commissionFeeAfter - commissionFeeBefore)
            * 100.0)
            + "% in this transaction.\n");
  }

  /**
   * This method will get the state of a portfolio.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  private void getState(StockModel model, Scanner scan, StringBuilder output) {

    output.append("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    String res = "";
    try {
      res = model.getPortfolioState(portfolioName);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      throw new IllegalArgumentException();
    }
    output.append("The state of " + portfolioName + "\n");
    output.append(res);
  }


  /**
   * This method will get the state of every portfolio.
   *
   * @param model a stock system model
   */
  private void getAllState(StockModel model, StringBuilder output) {

    String res = "";
    try {
      res = model.getPortfolioState();
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      throw new IllegalArgumentException();
    }
    output.append("The state of all portfolios:\n");
    output.append(res + "\n");
  }
}
