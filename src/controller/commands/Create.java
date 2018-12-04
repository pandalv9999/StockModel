package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

/**
 * This class represents a creating an empty portfolio behavior in a controller. It will parse the
 * command and pass it to the model.
 */
public class Create {

  /**
   * This method will parse the command of creating an empty portfolio.
   *
   * @param model   a stock system model
   * @param scan    a Scanner object
   * @param output  an appendable object for output
   * @param console whether this method is called by console controller or not
   */
  public static void create(StockModel model, Scanner scan, Appendable output, boolean console)
          throws IOException {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n", output,
            console);
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    try {
      model.createPortfolio(portfolioName);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      return;
    }
    output.append("Created an empty portfolio successfully.\n");
  }
}
