package controller.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

public class CreatePercentage {
  public static void createPercentage(StockModel model, Scanner scan, Appendable output, boolean console) throws IOException {

    String percentagesName = Input.input(scan, "Please input the investment plan's name.\n", output, console);
    if (Input.isQuit(percentagesName)) {
      output.append("Quit.\n");
      return;
    }

    String equal = Input.input(scan, "Equal proportion or separate?(E/S)\n", output, console);
    if (Input.isQuit(equal)) {
      output.append("Quit.\n");
      return;
    }

    Map<String, Double> company = new HashMap<>();
    int n;
    try {
      n = Integer.parseInt(Input.input(scan, "Number of companies?\n", output, console));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal number.");
    }
    for (int i = 0; i < n; i++) {
      String companyName = Input.input(scan, "Name of company?\n", output, console);

      if (Input.isQuit(companyName)) {
        output.append("Quit.\n");
        return;
      }
      double proportion = 1.0 / n;
      if (equal.equals("S") || equal.equals("s")) {
        try {
          proportion = Double.parseDouble(Input.input(scan,
                  "Proportion in percentage? E.g. input 30 to represent 30%.\n", output, console)) / 100.0;

          if (proportion < 0) {
            throw new IllegalArgumentException("Proportion should be larger than 0.");

          }
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Illegal number.");
        }
      }
      company.put(companyName, proportion);
    }

    Double res = 0.0;
    try {
      model.createPercentage(percentagesName, company);
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
    }
    output.append("Successfully created investing plan " + percentagesName + ".\n");
  }
}
