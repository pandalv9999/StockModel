package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

/**
 * This class represents a saving portfolio behavior in a controller. It will parse the command and
 * pass it to the model.
 */
public class SavePortfolio {

  /**
   * This method will parse the command of saving portfolio and pass it to the model.
   *
   * @param model   a stock system model
   * @param scan    a Scanner object
   * @param output  an appendable object for output
   * @param console whether this method is called by console controller or not
   */
  public static void savePortfolio(StockModel model, Scanner scan, Appendable output,
                                   boolean console) throws IOException {

    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n", output,
            console);
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }

    String fileName = Input.input(scan, "Please input the file's name.\n", output, console);
    if (Input.isQuit(fileName)) {
      output.append("Quit.\n");
      return;
    }

    try {
      model.savePortfolio(portfolioName, fileName);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
    }
    output.append("Save portfolio ").append(portfolioName).append(" in file ").append(fileName)
            .append(".csv.\n");
  }
}
