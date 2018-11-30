import java.io.InputStreamReader;

import controller.StockControllerImpl;
import model.Stock;
import model.StockModel;
import model.StockModelImpl;
import view.CreateView;
import view.IView;
import view.JFrameView;

/**
 * The class is the main class of the program.
 */

public class Main {

  /**
   * This is the main program of our project. It has the same effect when open the file in the .jar
   * file.
   *
   * @param args No argument needed.
   */

  public static void main(String[] args) {
    StockModel model = StockModelImpl.getBuilder().commissionFee(5.0).build();
    IView mainView = new JFrameView("GStocks");
    IView createView = new CreateView("Create empty portfolio");
    StockControllerImpl controller = new StockControllerImpl(model, mainView, createView);
    //controller.setView(mainView);
  }
}

