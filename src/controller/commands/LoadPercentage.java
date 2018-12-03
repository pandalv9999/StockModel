package controller.commands;

import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class LoadPercentage {
  public static void loadPercentage(StockModel model, Scanner scan, StringBuilder output) {

    String fileName = Input.input(scan, "Please input the file's name.\n");
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
