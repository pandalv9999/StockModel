import java.io.InputStreamReader;

import controller.StockControllerImpl;
import model.StockModelImpl;

/**
 * The class is the main class of the program.
 */

public class Main {

  /**
   * This is the main program of our project. It has the same effect when open the file in the .jar
   * file.
   */

  public static void main(String[] args) {
    new StockControllerImpl(new InputStreamReader(System.in), System.out)
            .start(StockModelImpl.getBuilder().build());
  }
}

