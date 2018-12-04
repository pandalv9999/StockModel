package controller.commands;

import java.util.List;

import controller.utility.Input;
import model.StockModel;

public class PlotValue {
  public static List plotValue(StockModel model, String portfolioName) {
    if (portfolioName.equals("")) {
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
