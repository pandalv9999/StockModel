package controller.commands;

import java.io.IOException;

import model.StockModel;

/**
 * This class represents a getting all states behavior in a controller.
 */
public class GetAllState {

  /**
   * This method will call model's method of getting all portfolios' states.
   *
   * @param model  a stock system model
   * @param output an appendable object for output
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
