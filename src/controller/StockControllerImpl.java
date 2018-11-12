package controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.StockModel;

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
    while (true) {
      output(model.getPortfolioState() + "\n");
      String command = input(scan);
      if (isQuit(command)) {
        output("Quit.\n");
        return;
      }
      if (command == "create") {
        String portfolioName = input(scan);
        model.createPortfolio(portfolioName);
        output("create a portfolio successfully\n");
      }
      if (command == "buy") {
        String portfolioName = input(scan);
        String companyName = input(scan);
        int shares = Integer.parseInt(input(scan));
        String date = input(scan);
        output("cost: $" + Double.toString(model.buy(portfolioName, companyName, shares, date)) + "\n");
      }
      if (command == "determinecost") {
        String portfolioName = input(scan);
        output(Double.toString(model.determineCost(portfolioName)) + "\n");
      }

      if (command == "determinevalue") {
        String portfolioName = input(scan);
        String date = input(scan);
        output(Double.toString(model.determineValue(portfolioName, date)) + "\n");
      }

      if (command == "getstate") {
        String portfolioName = input(scan);
        output(model.getPortfolioState(portfolioName) + "\n");
      }

      if (command == "getallstate") {
        output(model.getPortfolioState() + "\n");
      }
    }
  }
}
