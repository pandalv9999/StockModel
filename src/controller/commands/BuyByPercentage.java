package controller.commands;

import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class BuyByPercentage {
  /**
   * This method will buy a fixed portfolio in a certain date according to its percentage of each
   * stock.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  public static void buyByPercentage(StockModel model, Scanner scan, StringBuilder output) {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }

    String percentagesName = Input.input(scan, "Please input the investment plan's name.\n");
    if (Input.isQuit(percentagesName)) {
      output.append("Quit.\n");
      return;
    }

    double amount = 0.0;
    try {
      String st = Input.input(scan, "Amount of investment?\n");
      if (Input.isQuit(st)) {
        output.append("Quit.\n");
        return;
      }
      amount = Double.parseDouble(st);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal number");
    }

    // String date = input(scan);
    String date = Input.inputDate(scan,
            "Please input the date you want to buy in format yyyy-mm-dd/N/n.\n");
    if (Input.isQuit(date)) {
      output.append("Quit.\n");
      return;
    }

    Double res = 0.0;
    Double commissionFeeBefore;
    try {
      commissionFeeBefore = model.determineCommissionFee(portfolioName);
      res = model.buyByPercentage(portfolioName, percentagesName, amount, date);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
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
}
