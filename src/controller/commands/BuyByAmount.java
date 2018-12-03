package controller.commands;

import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class BuyByAmount {
  /**
   * This method will buy a certain stock into a portfolio.
   *
   * @param model a stock system model
   * @param scan  a Scanner object
   */
  public static void buyByAmount(StockModel model, Scanner scan, StringBuilder output) {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n");
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    String companyName = Input.input(scan, "Please input the company's name.\n");
    if (Input.isQuit(companyName)) {
      output.append("Quit.\n");
      return;
    }
    double amount = 0;
    try {
      String st = Input.input(scan, "What is the amount of money you want to invest?\n");
      if (Input.isQuit(st)) {
        output.append("Quit.\n");
        return;
      }
      amount = Double.parseDouble(st);
    } catch (Exception e) {
      output.append("Invalid shares, input again.\n");
      return;
    }

    // String date = input(scan);
    String date = Input.inputDate(scan,
            "Please input the date you want to buy in format (yyyy-mm-dd/ N/n).\n");
    if (Input.isQuit(date)) {
      output.append("Quit.\n");
      return;
    }
    Double res = 0.0;

    Double commissionFeeBefore = model.determineCommissionFee(portfolioName);
    try {
      res = model.buyByAmount(portfolioName, companyName, amount, date);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
    }
    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);

    if (date.equals("n") || date.equals("N")) {
      date = "the closest transaction date";
    }
    output.append("Successfully bought " + companyName + " with $" + amount + " on " + date
            + " and total cost is $"
            + Double.toString(res + commissionFeeAfter - commissionFeeBefore)
            + " with commission fee $"
            + Double.toString(commissionFeeAfter - commissionFeeBefore) + "\n");
    output.append("Commission fee is of "
            + Double.toString((commissionFeeAfter - commissionFeeBefore)
            / (res + commissionFeeAfter - commissionFeeBefore)
            * 100.0)
            + "%\n");
  }
}
