package controller.commands;

import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class GetState {
  /**
   * This method will get the state of a portfolio.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  public static void getState(StockModel model, Scanner scan, StringBuilder output) {

    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    String res = "";
    try {
      res = model.getPortfolioState(portfolioName);
    } catch (Exception e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
    }
    output.append("The state of " + portfolioName + "\n");
    output.append(res);
  }
}
