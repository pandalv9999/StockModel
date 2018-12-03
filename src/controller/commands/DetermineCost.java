package controller.commands;

import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class DetermineCost {
  /**
   * This method will try to determine a portfolio's cost basis and its commission fee.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  public static void determineCost(StockModel model, Scanner scan, StringBuilder output) {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    Double res = 0.0;
    try {
      res = model.determineCost(portfolioName);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
    }
    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
    output.append("The cost basis of buying in " + portfolioName + " is $" + Double.toString(res)
            + " with commission fee $"
            + Double.toString(commissionFeeAfter)
            + "\n");
  }
}
