package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class SavePercentage {
  public static void savePercentage(StockModel model, Scanner scan, Appendable output, boolean console) throws IOException {

    String percentageName = Input.input(scan, "Please input the portfolio's name.\n", output, console);
    if (Input.isQuit(percentageName)) {
      output.append("Quit.\n");
      return;
    }

    String fileName = Input.input(scan, "Please input the file's name.\n", output, console);
    if (Input.isQuit(fileName)) {
      output.append("Quit.\n");
      return;
    }

    try {
      model.savePercentage(percentageName, fileName);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
    }
    output.append("Save percentage ").append(percentageName).append(" in file ").append(fileName).append(".csv.\n");
  }
}
