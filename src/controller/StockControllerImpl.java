package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.StockModel;

/**
 * This class represents an implementation of a stock controller.
 */
public class StockControllerImpl implements StockController {
  private final Readable in;
  private final Appendable out;

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
      throw new IllegalStateException("Reader fails.");
    }
    return st;
  }

  /**
   * This method will take a String object as an input and output the string to the output stream.
   *
   * @param st a string needed to output
   */
  private void output(String st) throws IllegalStateException {
    try {
      this.out.append(st);
    } catch (IOException e) {
      throw new IllegalStateException("Output fails.");
    }
  }

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
   *
   * @param rd the inStream
   * @param ap the outStream
   */
  public StockControllerImpl(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Input and output should not be null.");
    }
    this.in = rd;
    this.out = ap;
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
   *
   * @param model a stock system model
   */
  public void start(StockModel model)
          throws IllegalArgumentException, IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("Model should not be null.");
    }
    Scanner scan = new Scanner(this.in);
    output("Welcome to the stock trading system.\n");
    while (true) {
      output("You can input: create, buy, determinecost, determinevalue, getstate, "
              + "getallstate, determinefee, createfixed, buyp, or q/Q\n");
      String command = input(scan);

      if (isQuit(command)) {
        output("Quit.\n");
        return;
      }

      if (command.equals("createfixed")) {
        try {
          createFixed(model, scan);
        } catch (IllegalArgumentException e) {
          continue;
        }
      }

      if (command.equals("create")) {
        try {
          create(model, scan);
        } catch (IllegalArgumentException e) {
          continue;
        }
      }

      if (command.equals("buy")) {
        try {
          buy(model, scan);
        } catch (IllegalArgumentException e) {
          continue;
        }
      }

      if (command.equals("determinecost")) {
        try {
          determineCost(model, scan);
        } catch (Exception e) {
          continue;
        }
      }

      if (command.equals("determinevalue")) {
        try {
          determineValue(model, scan);
        } catch (Exception e) {
          continue;
        }
      }

      if (command.equals("determinefee")) {
        try {
          determineCommissionFee(model, scan);
        } catch (Exception e) {
          continue;
        }
      }

      if (command.equals("buyp")) {
        try {
          buyByPercentage(model, scan);
        } catch (IllegalArgumentException e) {
          continue;
        }
      }

      if (command.equals("getstate")) {
        try {
          getState(model, scan);
        } catch (IllegalArgumentException e) {
          continue;
        }
      }

      if (command.equals("getallstate")) {
        try {
          getAllState(model);
        } catch (IllegalArgumentException e) {
          continue;
        }
      }
    }
  }

  private void createFixed(StockModel model, Scanner scan) {
    output("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output("Quit.\n");
      return;
    }
    output("Equal proportion or separate?(E/S)\n");
    String equal = input(scan);
    if (isQuit(equal)) {
      output("Quit.\n");
      return;
    }
    Map<String, Double> company = new HashMap<>();
    output("Number of companies?\n");
    int n;
    try {
      n = Integer.parseInt(input(scan));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal number.");
    }
    for (int i = 0; i < n; i++) {
      output("Name of company?\n");
      String companyName = input(scan);

      if (isQuit(companyName)) {
        output("Quit.\n");
        return;
      }
      double proportion = 1.0 / n;
      if (equal.equals("S") || equal.equals("s")) {
        output("Proportion in percentage? E.g. input 30 to represent 30%.\n");
        try {
          proportion = Double.parseDouble(input(scan)) / 100.0;
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Illegal number.");
        }
      }
      company.put(companyName, proportion);
    }

    double amount = 0.0;
    output("Amount of investment?\n");
    try {
      amount = Double.parseDouble(input(scan));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal number.");
    }
    if (isQuit(portfolioName)) {
      output("Quit.\n");
      return;
    }

    output("Ongoing? Y/N\n");
    String onGoing = input(scan);
    if (isQuit(onGoing)) {
      output("Quit.\n");
      return;
    }

    String startDate;
    String endDate = "N";
    if (onGoing.equals("Y") || onGoing.equals("y")) {

      output("Provide a start date?(yyyy-mm-dd / N/n)\n");
      // startDate = input(scan);
      startDate = inputDate(scan);
      if (isQuit(startDate)) {
        output("Quit.\n");
        return;
      }

      if (!startDate.equals("N") && !startDate.equals("n")) {
        output("Provide a end date?(yyyy-mm-dd / N/n)\n");
        // endDate = input(scan);
        endDate = inputDate(scan);
        if (isQuit(endDate)) {
          output("Quit.\n");
          return;
        }
      }

      int interval = 30;
      if (!startDate.equals("N") && !startDate.equals("n")) {
        output("Provide a interval in days?(e.g 15, 30, 60 / N/n)\n");
        String st = input(scan);
        if (isQuit(st)) {
          output("Quit.\n");
          return;
        }
        try {
          interval = Integer.parseInt(st);
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Illegal number.");
        }
      }

      try {   // better to add a things to ask what should be the interval. 30 days, 60 days. Done.
        model.dollarCostAverage(portfolioName, company, amount, startDate, endDate, interval);
      } catch (IllegalArgumentException e) {
        output(e.getMessage());
        output("\n");
        throw new IllegalArgumentException();
      }
    } else {
      output("Provide a buying date?(yyyy-mm-dd / N/n)\n");
      // String date = input(scan);
      String date = inputDate(scan);
      if (isQuit(date)) {
        output("Quit.\n");
        return;
      }

      try {
        model.createPortfolio(portfolioName, company, amount, date);
      } catch (IllegalArgumentException e) {
        output(e.getMessage());
        output("\n");
        throw new IllegalArgumentException();
      }
    }
    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
    output("Created a Fixed portfolio successfully " + "with commission fee $"
            + Double.toString(commissionFeeAfter) + "\n");
    output("The total cost is $"
            + Double.toString(model.determineCost(portfolioName)
            + model.determineCommissionFee(portfolioName)) + "\n");
    output("Commission fee is of "
            + Double.toString(model.determineCommissionFee(portfolioName)
            / (model.determineCost(portfolioName) + model.determineCommissionFee(portfolioName))
            * 100.0)
            + "%\n");
  }

  private void create(StockModel model, Scanner scan) {
    output("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output("Quit.\n");
      return;
    }
    try {
      model.createPortfolio(portfolioName);
    } catch (IllegalArgumentException e) {
      output(e.getMessage());
      throw new IllegalArgumentException();
    }
    output("Created an empty portfolio successfully.\n");
  }

  private void buy(StockModel model, Scanner scan) {
    output("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output("Quit.\n");
      return;
    }
    output("Please input the company's name.\n");
    String companyName = input(scan);
    if (isQuit(companyName)) {
      output("Quit.\n");
      return;
    }
    output("How many shares you want to buy?\n");
    int shares = 0;
    while (true) {
      try {
        String st = input(scan);
        if (isQuit(st)) {
          output("Quit.\n");
          return;
        }
        shares = Integer.parseInt(st);
      } catch (Exception e) {
        output("Invalid shares, input again.\n");
        continue;
      }
      break;
    }
    output("Please input the date you want to buy in format (yyyy-mm-dd/ N/n).\n");
    // String date = input(scan);
    String date = inputDate(scan);
    if (isQuit(date)) {
      output("Quit.\n");
      return;
    }
    Double res = 0.0;

    Double commissionFeeBefore = model.determineCommissionFee(portfolioName);
    try {
      res = model.buy(portfolioName, companyName, shares, date, "low");
    } catch (IllegalArgumentException e) {
      output(e.getMessage());
      output("\n");
      throw new IllegalArgumentException();
    }
    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
    output("Successfully bought " + companyName + " with " + shares + " shares on " + date
            + " and total cost is $"
            + Double.toString(res + commissionFeeAfter - commissionFeeBefore)
            + " with commission fee $"
            + Double.toString(commissionFeeAfter - commissionFeeBefore) + "\n");
    output("Commission fee is of "
            + Double.toString((commissionFeeAfter - commissionFeeBefore)
            / (res + commissionFeeAfter - commissionFeeBefore)
            * 100.0)
            + "%\n");
  }

  private void determineCost(StockModel model, Scanner scan) {
    output("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output("Quit.\n");
      return;
    }
    Double res = 0.0;
    try {
      res = model.determineCost(portfolioName);
    } catch (IllegalArgumentException e) {
      output(e.getMessage());
      output("\n");
      throw new IllegalArgumentException();
    }
    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
    output("The cost basis of buying in " + portfolioName + " is $" + Double.toString(res)
            + " with commission fee $"
            + Double.toString(commissionFeeAfter)
            + "\n");
  }

  private void determineValue(StockModel model, Scanner scan) {
    output("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output("Quit.\n");
      return;
    }
    output("Please input the date you want to check in format yyyy-mm-dd.\n");
    String date = inputDate(scan);
    if (isQuit(date)) {
      output("Quit.\n");
      return;
    }
    Double res = 0.0;
    try {
      res = model.determineValue(portfolioName, date);
    } catch (IllegalArgumentException e) {
      output(e.getMessage());
      output("\n");
      throw new IllegalArgumentException();
    }
    output("The value of all stocks in this portfolio on " + date + " is $"
            + Double.toString(res) + "\n");
  }

  private void determineCommissionFee(StockModel model, Scanner scan) {
    output("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output("Quit.\n");
      return;
    }
    Double res = 0.0;
    try {
      res = model.determineCommissionFee(portfolioName);
    } catch (IllegalArgumentException e) {
      output(e.getMessage());
      output("\n");
      throw new IllegalArgumentException();
    }
    output("The fee of all transactions in this portfolio is $"
            + Double.toString(res) + "\n");
  }

  private void buyByPercentage(StockModel model, Scanner scan) {
    output("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output("Quit.\n");
      return;
    }

    double amount = 0.0;
    output("Amount of investment?\n");
    try {
      amount = Double.parseDouble(input(scan));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal number");
    }

    output("Please input the date you want to buy in format yyyy-mm-dd/N/n.\n");
    // String date = input(scan);
    String date = inputDate(scan);
    if (isQuit(date)) {
      output("Quit.\n");
      return;
    }

    Double res = 0.0;
    Double commissionFeeBefore = model.determineCommissionFee(portfolioName);
    try {
      res = model.buyByPercentage(portfolioName, amount, date);
    } catch (IllegalArgumentException e) {
      output(e.getMessage());
      output("\n");
      throw new IllegalArgumentException();
    }
    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
    output("The cost of buying this portfolio is $"
            + Double.toString(res + commissionFeeAfter - commissionFeeBefore)
            + " with commission fee $"
            + Double.toString(commissionFeeAfter - commissionFeeBefore) + "\n");
    output("Commission fee is of "
            + Double.toString((commissionFeeAfter - commissionFeeBefore)
            / (res + commissionFeeAfter - commissionFeeBefore)
            * 100.0)
            + "% in this transaction.\n");
  }

  private void getState(StockModel model, Scanner scan) {

    output("Please input the portfolio's name.\n");
    String portfolioName = input(scan);
    if (isQuit(portfolioName)) {
      output("Quit.\n");
      return;
    }
    String res = "";
    try {
      res = model.getPortfolioState(portfolioName);
    } catch (IllegalArgumentException e) {
      output(e.getMessage());
      output("\n");
      throw new IllegalArgumentException();
    }
    output("The state of " + portfolioName + "\n");
    output(res);
  }

  private void getAllState(StockModel model) {

    String res = "";
    try {
      res = model.getPortfolioState();
    } catch (IllegalArgumentException e) {
      output(e.getMessage());
      output("\n");
      throw new IllegalArgumentException();
    }
    output("The state of all portfolios:\n");
    output(res + "\n");
  }
}
