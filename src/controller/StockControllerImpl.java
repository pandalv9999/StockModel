package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
              + "getallstate or q/Q\n");
      String command = input(scan);

      if (isQuit(command)) {
        output("Quit.\n");
        return;
      }

      if (command.equals("createfixed")) {
        output("Please input the portfolio's name.\n");
        String portfolioName = input(scan);
        if (isQuit(portfolioName)) {
          output("Quit.\n");
          return;
        }
        output("Equal proportion or separate?(E/S)\n");
        String equal = input(scan);
        Map<String, Double> company = new HashMap<>();
        // List<Double> percentage = new ArrayList<>();
        output("Number of companies?\n");
        int n = Integer.parseInt(input(scan));
        for (int i = 0; i < n; i++) {
          output("Name of company?\n");
          String companyName = input(scan);
          double proportion = 1.0 / n;
          if (equal.equals("S") || equal.equals("s")) {
            output("Proportion in percentage? E.g. input 23 to represent 23%.\n");
            proportion = Double.parseDouble(input(scan)) / 100;
          }
          company.put(companyName, proportion);
        }

//        if (equal.equals("e") || equal.equals("E")) {
//          for (int i = 0; i < n; i++) {
//            percentage.add(1.0 / n);
//          }
//        }

        output("Amount of investment?\n");
        double amount = Double.parseDouble(input(scan));
        if (isQuit(portfolioName)) {
          output("Quit.\n");
          return;
        }

        output("Ongoing?\n");
        String onGoing = input(scan);
        if (isQuit(portfolioName)) {
          output("Quit.\n");
          return;
        }

        String startDate;
        String endDate = "N";
        if (onGoing.equals("Y") || onGoing.equals("y")) {

          output("Provide a start date?(yyyy-mm-dd / N/n)\n");
          startDate = input(scan);
          if (isQuit(portfolioName)) {
            output("Quit.\n");
            return;
          }

          if (!startDate.equals("N") && !startDate.equals("n")) {
            output("Provide a end date?(yyyy-mm-dd / N/n)\n");
            endDate = input(scan);
            if (isQuit(portfolioName)) {
              output("Quit.\n");
              return;
            }
          }

          try {
            model.dollarCostAverage(portfolioName, company, amount, startDate, endDate);
          } catch (IllegalArgumentException e) {
            output(e.getMessage());
            continue;
          }
        } else {
          output("Provide a buying date?(yyyy-mm-dd / N/n)\n");
          String date = input(scan);
          if (isQuit(portfolioName)) {
            output("Quit.\n");
            return;
          }

          try {
            model.createPortfolio(portfolioName, company, amount, date);
          } catch (IllegalArgumentException e) {
            output(e.getMessage());
            continue;
          }
        }
        output("Created a Fixed portfolio successfully.\n");
      }

      if (command.equals("create")) {
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
          continue;
        }
        output("Created a portfolio successfully.\n");
      }

      if (command.equals("buy")) {
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
        String date = input(scan);
        if (isQuit(date)) {
          output("Quit.\n");
          return;
        }
        Double res = 0.0;
        try {
          res = model.buy(portfolioName, companyName, shares, date, "low");
        } catch (IllegalArgumentException e) {
          output(e.getMessage());
          output("\n");
          continue;
        }
        output("Successfully bought " + companyName + " with " + shares + " shares on " + date
                + " and cost is  $" + res.toString() + "\n");
      }

      if (command.equals("determinecost")) {
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
          continue;
        }
        output("The total cost of buying in is $" + Double.toString(res) + "\n");
      }

      if (command.equals("determinevalue")) {
        output("Please input the portfolio's name.\n");
        String portfolioName = input(scan);
        if (isQuit(portfolioName)) {
          output("Quit.\n");
          return;
        }
        output("Please input the date you want to check in format yyyy-mm-dd.\n");
        String date = input(scan);
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
          continue;
        }
        output("The value of all stocks in this portfolio on " + date + " is $"
                + Double.toString(res) + "\n");
      }

      if (command.equals("getstate")) {
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
          continue;
        }
        output("The state of " + portfolioName + "\n");
        output(res);
      }

      if (command.equals("getallstate")) {
        String res = "";
        try {
          res = model.getPortfolioState();
        } catch (IllegalArgumentException e) {
          output(e.getMessage());
          output("\n");
          continue;
        }
        output("The state of all portfolios:\n");
        output(res + "\n");
      }
    }
  }
}
