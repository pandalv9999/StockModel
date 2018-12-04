package controller.commands;

import java.io.IOException;

import model.StockModel;

public class GetAllState {
  /**
   * This method will get the state of every portfolio.
   *
   * @param model a stock system model
   */
  public static void getAllState(StockModel model, Appendable output) throws IOException {

    String res = "";
    try {
      res = model.getPortfolioState();
    } catch (IllegalArgumentException e) {
      output.append(e.getMessage());
      output.append("\n");
      return;
    }
    output.append("The state of all portfolios:\n");
    output.append(res + "\n");
  }
}
