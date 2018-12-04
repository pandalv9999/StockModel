package controller.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.utility.Input;
import model.StockModel;

/**
 * This class represents a creating an fixed portfolio behavior in a controller. It will parse the
 * command and pass it to the model.
 */
public class CreateFixed {

  /**
   * This method will parse the command of creating an fixed portfolio with a certain investing plan
   * of the same name.
   *
   * @param model   a stock system model
   * @param scan    a Scanner object
   * @param output  an appendable object for output
   * @param console whether this method is called by console controller or not
   */
  public static void createFixed(StockModel model, Scanner scan, Appendable output, boolean console)
          throws IOException {
    String portfolioName = Input.input(scan, "Please input the portfolio's name.\n", output,
            console);
    if (Input.isQuit(portfolioName)) {
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
                  "Proportion in percentage? E.g. input 30 to represent 30%.\n", output,
                  console)) / 100.0;
          if (proportion < 0) {
            throw new IllegalArgumentException("Proportion should be larger than 0.");

          }
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Illegal number.");
        }
      }
      company.put(companyName, proportion);
    }

    double amount = 0.0;
    try {
      amount = Double.parseDouble(Input.input(scan, "Amount of investment?\n", output,
              console));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal number.");
    }
    if (Input.isQuit(portfolioName)) {
      output.append("Quit.\n");
      return;
    }

    String onGoing = Input.input(scan, "Ongoing? Y/N\n", output, console);
    if (Input.isQuit(onGoing)) {
      output.append("Quit.\n");
      return;
    }

    String startDate;
    String endDate = "N";
    if (onGoing.equals("Y") || onGoing.equals("y")) {

      // startDate = input(scan);
      startDate = Input.inputDate(scan, "Provide a start date?(yyyy-mm-dd / N/n)\n", output,
              console);
      if (Input.isQuit(startDate)) {
        output.append("Quit.\n");
        return;
      }

      if (!startDate.equals("N") && !startDate.equals("n")) {
        // endDate = input(scan);
        endDate = Input.inputDate(scan, "Provide a end date?(yyyy-mm-dd / N/n)\n", output,
                console);
        if (Input.isQuit(endDate)) {
          output.append("Quit.\n");
          return;
        }
      }

      int interval = 30;
      if (!startDate.equals("N") && !startDate.equals("n")) {
        String st = Input.input(scan, "Provide a interval in days?(e.g 15, 30, 60 / N/n)\n", output,
                console);
        if (Input.isQuit(st)) {
          output.append("Quit.\n");
          return;
        }
        if (!st.equals("n") && !st.equals("N")) {
          try {
            interval = Integer.parseInt(st);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Illegal number.");
          }
        }
      }

      try {   // better to add a things to ask what should be the interval. 30 days, 60 days. Done.
        model.dollarCostAverage(portfolioName, company, amount, startDate, endDate, interval);
      } catch (IllegalArgumentException e) {
        output.append(e.getMessage());
        output.append("\n");
        return;
      }
    } else {
      // String date = input(scan);
      String date = Input.inputDate(scan, "Provide a buying date?(yyyy-mm-dd / N/n)\n", output,
              console);
      if (Input.isQuit(date)) {
        output.append("Quit.\n");
        return;
      }

      try {
        model.createPortfolio(portfolioName, company, amount, date);
      } catch (IllegalArgumentException e) {
        output.append(e.getMessage());
        output.append("\n");
        return;
      }
    }
    Double cost = model.determineCost(portfolioName);
    Double commissionFeeAfter = model.determineCommissionFee(portfolioName);
    output.append("Created a Fixed portfolio successfully " + "with commission fee $"
            + Double.toString(commissionFeeAfter) + "\n");
    output.append("The total cost is $"
            + Double.toString(cost
            + commissionFeeAfter) + "\n");
    output.append("Commission fee is of "
            + Double.toString(commissionFeeAfter
            / (cost + commissionFeeAfter)
            * 100.0)
            + "%\n");
  }
}
