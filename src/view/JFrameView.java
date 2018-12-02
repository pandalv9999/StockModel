package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controller.StockControllerImpl;

public class JFrameView extends JFrame implements IView {
  private JLabel display;
  private JButton echoButton, exitButton, createButton, getAllStateButton, getStateButton,
          determineCostButton, determineFeeButton, determineValueButton, buyButton,
          buyPercentageButton, buyAmountButton, createFixedButton, createPercentageButton,
          savePortfolioButton, savePercentageButton;
  private JTextField input;

  public JFrameView(String caption) {
    super(caption);
    setSize(800, 600);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.setResizable(false);
//		this.setMinimumSize(new Dimension(300,300));


    this.setLayout(new FlowLayout());
    display = new JLabel("You can input: create, buy, determinecost, determinevalue, getstate, "
            + "getallstate, determinefee, createfixed, buyp, or q/Q\n");


    this.add(display);

    //the textfield
    input = new JTextField(20);
    this.add(input);

    //echobutton
    echoButton = new JButton("Go");
    echoButton.setActionCommand("Echo Button");
    this.add(echoButton);

    //createButton
    createButton = new JButton("Create");
    createButton.setActionCommand("Create Button");
    this.add(createButton);

    //getAllStateButton
    getAllStateButton = new JButton("Get all state");
    getAllStateButton.setActionCommand("GetAllState Button");
    this.add(getAllStateButton);

    //getStateButton
    getStateButton = new JButton("Get state");
    getStateButton.setActionCommand("GetState Button");
    this.add(getStateButton);

    //determineCostButton
    determineCostButton = new JButton("Determine Cost");
    determineCostButton.setActionCommand("DetermineCost Button");
    this.add(determineCostButton);


    //determineCostButton
    determineFeeButton = new JButton("Determine Fee");
    determineFeeButton.setActionCommand("DetermineFee Button");
    this.add(determineFeeButton);


    //determineValueButton
    determineValueButton = new JButton("Determine Value");
    determineValueButton.setActionCommand("DetermineValue Button");
    this.add(determineValueButton);

    //buyButton
    buyButton = new JButton("Buy a stock by shares");
    buyButton.setActionCommand("Buy Button");
    this.add(buyButton);

    //buyPercentageButton
    buyPercentageButton = new JButton("Buy a Portfolio");
    buyPercentageButton.setActionCommand("BuyPercentage Button");
    this.add(buyPercentageButton);

    //buyAmountButton
    buyAmountButton = new JButton("Buy a stock by amount");
    buyAmountButton.setActionCommand("BuyAmount Button");
    this.add(buyAmountButton);

    //createFixedButton
    createFixedButton = new JButton("Create a fixed portfolio");
    createFixedButton.setActionCommand("CreateFixed Button");
    this.add(createFixedButton);

    //createFixedButton
    createPercentageButton = new JButton("Create an investing plan");
    createPercentageButton.setActionCommand("SavePortfolio Button");
    this.add(createPercentageButton);

    //savePortfolioButton
    savePortfolioButton = new JButton("Save a portfolio");
    savePortfolioButton.setActionCommand("SavePortfolio Button");
    this.add(savePortfolioButton);

    //savePortfolioButton
    savePercentageButton = new JButton("Save an investing plan");
    savePercentageButton.setActionCommand("SavePercentage Button");
    this.add(savePercentageButton);

    //exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit Button");
    this.add(exitButton);


    //pack();
    setVisible(true);

  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    echoButton.addActionListener(actionListener);
    createButton.addActionListener(actionListener);
    exitButton.addActionListener(actionListener);
    getAllStateButton.addActionListener(actionListener);
    getStateButton.addActionListener(actionListener);
    determineCostButton.addActionListener(actionListener);
    determineFeeButton.addActionListener(actionListener);
    determineValueButton.addActionListener(actionListener);
    buyButton.addActionListener(actionListener);
    buyPercentageButton.addActionListener(actionListener);
    buyAmountButton.addActionListener(actionListener);
    createFixedButton.addActionListener(actionListener);
    createPercentageButton.addActionListener(actionListener);
    savePortfolioButton.addActionListener(actionListener);
    savePercentageButton.addActionListener(actionListener);
  }


  /*
      In order to make this frame respond to keyboard events, it must be within strong focus.
      Since there could be multiple components on the screen that listen to keyboard events,
      we must set one as the "currently focussed" one so that all keyboard events are
      passed to that component. This component is said to have "strong focus".

      We do this by first making the component focusable and then requesting focus to it.
      Requesting focus makes the component have focus AND removes focus from whoever had it
      before.
       */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void toggleColor() {
    if (this.display.getForeground().equals(Color.RED))
      this.display.setForeground(Color.BLACK);
    else
      this.display.setForeground(Color.RED);
  }


  @Override
  public void setEchoOutput(String s) {
    display.setText(s);
  }

  @Override
  public String getInputString() {
    return input.getText();
  }

  @Override
  public void clearInputString() {
    input.setText("");
  }


}

