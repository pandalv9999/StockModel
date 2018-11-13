package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.StockModel;
import model.StockModelImpl;

public class StockControllerImpl implements StockController {
  private final Readable in;
  private final Appendable out;

  private String input(Scanner scan) throws IllegalStateException {
    String st = "";
    try {
      st = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Reader fails.");
    }
    return st;
  }

  private void output(String st) throws IllegalStateException {
    try {
      this.out.append(st);
    } catch (IOException e) {
      throw new IllegalStateException("Output fails.");
    }
  }

  public StockControllerImpl(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Input and output should not be null.");
    }
    this.in = rd;
    this.out = ap;
  }

  private boolean isQuit(String st) {
    return st.equals("q") || st.equals("Q");
  }

  public void start(StockModel model)
          throws IllegalArgumentException, IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("Model should not be null.");
    }
    Scanner scan = new Scanner(this.in);
    output("Welcome to the stock trading system.\n");
    while (true) {
      output("You can input: create, buy, determinecost, determinevalue, getstate, getallstate\n");
      String command = input(scan);

      if (isQuit(command)) {
        output("Quit.\n");
        return;
      }

      if (command.equals("create")) {
        output("Please input the portfolio's name.\n");
        String portfolioName = input(scan);
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
        output("Please input the company's name.\n");
        String companyName = input(scan);
        output("How many shares you want to buy?\n");
        int shares = 0;
        while (true) {
          try {
            shares = Integer.parseInt(input(scan));
          } catch (Exception e) {
            output("Invalid shares, input again.\n");
            continue;
          }
          break;
        }
        output("Please input the date you want to buy in format yyyy-mm-dd.\n");
        String date = input(scan);
        Double res = 0.0;
        try {
          res = model.buy(portfolioName, companyName, shares, date);
        } catch (IllegalArgumentException e) {
          output(e.getMessage());
          output("\n");
          continue;
        }
        output("cost: $" + res.toString() + "\n");
      }

      if (command.equals("determinecost")) {
        output("Please input the portfolio's name.\n");
        String portfolioName = input(scan);
        Double res = 0.0;
        try {
          res = model.determineCost(portfolioName);
        } catch (IllegalArgumentException e) {
          output(e.getMessage());
          output("\n");
          continue;
        }
        output(Double.toString(res) + "\n");
      }

      if (command.equals("determinevalue")) {
        output("Please input the portfolio's name.\n");
        String portfolioName = input(scan);
        output("Please input the date you want to check in format yyyy-mm-dd.\n");
        String date = input(scan);
        Double res = 0.0;
        try {
          res = model.determineValue(portfolioName, date);
        } catch (IllegalArgumentException e) {
          output(e.getMessage());
          output("\n");
          continue;
        }
        output(Double.toString(res) + "\n");
      }

      if (command.equals("getstate")) {
        output("Please input the portfolio's name.\n");
        String portfolioName = input(scan);
        String res = "";
        try {
          res = model.getPortfolioState(portfolioName);
        } catch (IllegalArgumentException e) {
          output(e.getMessage());
          output("\n");
          continue;
        }
        output(res + "\n");
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
        output(res + "\n");
      }
    }
  }
}
