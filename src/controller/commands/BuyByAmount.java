package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

/**
 * This class represents a buying by amount behavior in a controller. It will parse the command and
 * pass it to the model.
 */
public class BuyByAmount {

  /**
   * This method will parse the command of buying a certain stock by amount of money into a
   * portfolio.
   *
   * @param model   a stock system model
   * @param scan    a Scanner object
   * @param output  an appendable object for output
   * @param console whether this method is called by console controller or not
   */
  public static void buyByAmount(StockModel model, Scanner scan, Appendable output,
                                 boolean console) throws IOException {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n", output,
            console);
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    String companyName = Input.input(scan, "Please input the company's name.\n", output,
            console);
    if (Input.isQuit(companyName)) {
      output.append("Quit.\n");
      return;
    }
    double amount = 0;
    try {
      String st = Input.input(scan, "What is the amount of money you want to invest?\n",
              output, console);
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
            "Please input the date you want to buy in format (yyyy-mm-dd/ N/n).\n",
            output, console);
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
