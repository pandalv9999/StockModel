package controller;

import java.io.IOException;
import java.util.Scanner;

import controller.commands.Buy;
import controller.commands.BuyByAmount;
import controller.commands.BuyByPercentage;
import controller.commands.Create;
import controller.commands.CreateFixed;
import controller.commands.CreatePercentage;
import controller.commands.DetermineCommissionFee;
import controller.commands.DetermineCost;
import controller.commands.DetermineValue;
import controller.commands.GetAllState;
import controller.commands.GetState;
import controller.commands.LoadPercentage;
import controller.commands.LoadPortfolio;
import controller.commands.SavePercentage;
import controller.commands.SavePortfolio;
import controller.utility.Input;
import model.StockModel;

/**
 * This class represents an implementation of a stock command line controller.
 */
public class StockControllerCommandImpl implements StockControllerCommand {
  private final Readable in;
  private final Appendable out;

  /**
   * This method will take a String object as an input and output the string to the output stream.
   *
   * @param st a string needed to output
   */
  private void output(String st) throws IllegalStateException {
    try {
      this.out.append(st);
    } catch (IOException e) {
      throw new IllegalStateException("Output fails.");
    }
  }

  /**
   * The constructor of this method. It will take in a inStream and outStream and initialize the
   * controller.
   *
   * @param rd the inStream
   * @param ap the outStream
   */
  public StockControllerCommandImpl(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Input and output should not be null.");
    }
    this.in = rd;
    this.out = ap;
  }

  /**
   * This method is the main method of the controller. It takes a model as the parameter and call
   * its methods.
   *
   * @param model a stock system model
   */
  public void start(StockModel model)
          throws IllegalArgumentException, IllegalStateException, IOException {
    if (model == null) {
      throw new IllegalArgumentException("Model should not be null.");
    }
    Scanner scan = new Scanner(this.in);
    output("Welcome to the stock trading system.\n");
    while (true) {
      String command = Input.input(scan, "You can input: create, createfixed, createp, buy, "
              + "buya, buyp, "
              + "determinecost, determinevalue, determinefee, getstate, "
              + "getallstate, saveportfolio, loadportfolio, savepercentage, "
              + "loadpercentage or Q/q\n", this.out, true);
      command = command.toLowerCase();

      if (Input.isQuit(command)) {
        output("Quit.\n");
        return;
      } else if (command.equals("createfixed")) {
        try {
          CreateFixed.createFixed(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("create")) { //done
        try {
          Create.create(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("createp")) { //done
        try {
          CreatePercentage.createPercentage(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("buy")) { //done
        try {
          Buy.buy(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("buya")) { //done
        try {
          BuyByAmount.buyByAmount(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("determinecost")) { //done
        try {
          DetermineCost.determineCost(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("determinevalue")) { //done
        try {
          DetermineValue.determineValue(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("determinefee")) { //done
        try {
          DetermineCommissionFee.determineCommissionFee(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("buyp")) { //done
        try {
          BuyByPercentage.buyByPercentage(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("getstate")) { //done
        try {
          GetState.getState(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("getallstate")) { //done
        try {
          GetAllState.getAllState(model, this.out);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("saveportfolio")) { //done
        try {
          SavePortfolio.savePortfolio(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("loadportfolio")) { //done
        try {
          LoadPortfolio.loadPortfolio(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("savepercentage")) { //done
        try {
          SavePercentage.savePercentage(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      } else if (command.equals("loadpercentage")) { //done
        try {
          LoadPercentage.loadPercentage(model, scan, this.out, true);
        } catch (Exception e) {
          output(e.getMessage());
          continue;
        }
      }
    }
  }
}