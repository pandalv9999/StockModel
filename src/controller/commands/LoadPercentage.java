package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class LoadPercentage {
  public static void loadPercentage(StockModel model, Scanner scan, Appendable output, boolean console) throws IOException {

    String fileName = Input.input(scan, "Please input the file's name.\n", output, console);
    if (Input.isQuit(fileName)) {
      output.append("Quit.\n");
      return;
    }

    try {
      model.loadPercentage(fileName);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
    }
    output.append("Load percentage from ").append(fileName).append(".csv.\n");
  }
}
