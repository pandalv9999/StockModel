package controller.commands;

import java.util.List;

import model.StockModel;

/**
 * This class represents a plotting portfolio's value in a controller. It will parse the command and
 * pass it to the model.
 */
public class PlotValue {

  /**
   * This method will parse the command of plotting a portfolio and pass it to the model.
   *
   * @param model         a stock system model
   * @param portfolioName a portfolio's name you want to plot
   * @return a List of the portfolio's value in the last 12 months
   */
  public static List plotValue(StockModel model, String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Portfolio name should not be empty");
    }
    List res;
    try {
      res = model.determineValuePlot(portfolioName);
    } catch (IllegalArgumentException e) {
      return null;
    }
    return res;
  }
}
