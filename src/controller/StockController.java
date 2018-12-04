package controller;

import java.io.IOException;

import view.IView;

public interface StockController {
  void setView(IView v);

  String processCommand(String command) throws IllegalArgumentException, IllegalStateException,
          IOException;
}
