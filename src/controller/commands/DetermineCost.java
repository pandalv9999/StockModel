package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

/**
 * This class represents a determining cost behavior in a controller. It will parse the command and
 * pass it to the model.
 */
public class DetermineCost {

  /**
   * This method will parse the command of determining an portfolio's cost and pass it to the
   * model.
   *
   * @param model   a stock system model
   * @param scan    a Scanner object
   * @param output  an appendable object for output
   * @param console whether this method is called by console controller or not
   */
  public static void determineCost(StockModel model, Scanner scan, Appendable output,
                                   boolean console) throws IOException {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n", output,
            console);
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
