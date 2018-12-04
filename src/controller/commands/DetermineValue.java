package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

/**
 * This class represents a determining value behavior in a controller. It will parse the command and
 * pass it to the model.
 */
public class DetermineValue {

  /**
   * This method will parse the command of determining an portfolio's value on a certain date and
   * pass it to the model.
   *
   * @param model   a stock system model
   * @param scan    a Scanner object
   * @param output  an appendable object for output
   * @param console whether this method is called by console controller or not
   */
  public static void determineValue(StockModel model, Scanner scan, Appendable output,
                                    boolean console) throws IOException {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n", output,
            console);
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }
    String date = Input.inputDate(scan, "Please input the date you want to check in format "
            + "yyyy-mm-dd.\n", output, console);
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
