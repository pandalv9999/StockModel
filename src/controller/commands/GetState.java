package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

/**
 * This class represents a getting state behavior in a controller. It will parse the command and
 * pass it to the model.
 */
public class GetState {

  /**
   * This method will parse the command of getting an portfolio's state and pass it to the model.
   *
   * @param model   a stock system model
   * @param scan    a Scanner object
   * @param output  an appendable object for output
   * @param console whether this method is called by console controller or not
   */
  public static void getState(StockModel model, Scanner scan, Appendable output, boolean console)
          throws IOException {

    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n", output,
            console);
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
