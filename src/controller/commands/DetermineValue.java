package controller.commands;

import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class DetermineValue {

  /**
   * This method will determine a portfolio in a certain date.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  public static void determineValue(StockModel model, Scanner scan, StringBuilder output) {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    String date = Input.inputDate(scan, "Please input the date you want to check in format yyyy-mm-dd.\n");
    if (Input.isQuit(date)) {
      output.append("Quit.\n");
      return;
    }
    Double res = 0.0;
    try {
      res = model.determineValue(portfolioName, date);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
    }
    if (date.equals("n") || date.equals("N")) {
      date = "the closest transaction date";
    }
    output.append("The value of all stocks in this portfolio on " + date + " is $"
            + Double.toString(res) + "\n");
  }
}
