package controller;

import java.io.IOException;

import view.IView;

/**
 * This represents the interface of stock controller with gui. It configure views and their
 * listeners and it can process command pass by view..
 */
public interface StockController {

  /**
   * This method set the current view of the controller.
   *
   * @param v the current view
   */
  void setView(IView v);

  /**
   * This method can parse a command and call the model with certain parameters.
   *
   * @param command a command passed by the view
   * @return a result string
   */
  String processCommand(String command) throws IllegalArgumentException, IllegalStateException,
          IOException;
}
