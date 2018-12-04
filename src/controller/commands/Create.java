package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class Create {
  /**
   * This method will create an empty portfolio.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  public static void create(StockModel model, Scanner scan, Appendable output, boolean console) throws IOException {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n", output, console);
    if (Input.isQuit(portfolioName)) {
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
}
