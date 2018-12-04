package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class DetermineCommissionFee {
  /**
   * This method will determine the commission fee of a portfolio.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  public static void determineCommissionFee(StockModel model, Scanner scan, Appendable output, boolean console) throws IOException {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n", output, console);
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    Double res = 0.0;
    try {
      res = model.determineCommissionFee(portfolioName);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
    }
    output.append("The fee of all transactions in this portfolio is $"
            + Double.toString(res) + "\n");
  }
}
