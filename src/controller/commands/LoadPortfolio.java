package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

/**
 * This class represents a loading portfolio in a controller. It will parse the command and pass it
 * to the model.
 */
public class LoadPortfolio {

  /**
   * This method will parse the command of loading a portfolio and pass it to the model.
   *
   * @param model   a stock system model
   * @param scan    a Scanner object
   * @param output  an appendable object for output
   * @param console whether this method is called by console controller or not
   */
  public static void loadPortfolio(StockModel model, Scanner scan, Appendable output,
                                   boolean console) throws IOException {

    String fileName = Input.input(scan, "Please input the file's name.\n", output, console);
    if (Input.isQuit(fileName)) {
      output.append("Quit.\n");
      return;
    }

    try {
      model.loadPortfolio(fileName);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
    }
    output.append("Load portfolio from ").append(fileName).append(".csv.\n");
  }
}
