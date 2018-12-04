package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class LoadPortfolio {
  public static void loadPortfolio(StockModel model, Scanner scan, Appendable output, boolean console) throws IOException {

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
