package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

/**
 * This class represents a buying an investing plan behavior in a controller. It will parse the
 * command and pass it to the model.
 */
public class BuyByPercentage {

  /**
   * This method will parse the command of buying a certain investing plan by amount of money on an
   * certain date into a portfolio.
   *
   * @param model   a stock system model
   * @param scan    a Scanner object
   * @param output  an appendable object for output
   * @param console whether this method is called by console controller or not
   */
  public static void buyByPercentage(StockModel model, Scanner scan, Appendable output,
                                     boolean console) throws IOException {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n", output,
            console);
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }

    String percentagesName = Input.input(scan, "Please input the investment plan's name.\n",
            output, console);
    if (Input.isQuit(percentagesName)) {
      output.append("Quit.\n");
      return;
    }

    double amount = 0.0;
    try {
      String st = Input.input(scan, "Amount of investment?\n", output, console);
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
            "Please input the date you want to buy in format yyyy-mm-dd/N/n.\n", output,
            console);
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
