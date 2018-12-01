package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class CreateFixedView extends JFrame implements IView {
  private JLabel display;
  private JButton echoButton, exitButton;
  private JTextField portfolioName, equal, companyNumber, companyNameSet, amount, onGoing, startDate,
          endDate, interval;

  public CreateFixedView(String caption) {
    super(caption);
    setSize(800, 600);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.setResizable(false);
//		this.setMinimumSize(new Dimension(300,300));


    this.setLayout(new FlowLayout());
    display = new JLabel("Please input the portfolio's name.");


    this.add(display);

    //the textfield
    portfolioName = new JTextField(5);
    this.add(portfolioName);


    //the textfield
    equal = new JTextField(8);
    this.add(equal);


    //the textfield
    companyNumber = new JTextField(8);
    this.add(companyNumber);


    //the textfield
    companyNameSet = new JTextField(8);
    this.add(companyNameSet);


    //the textfield
    amount = new JTextField(8);
    this.add(amount);


    //the textfield
    onGoing = new JTextField(8);
    this.add(onGoing);


    //the textfield
    startDate = new JTextField(8);
    this.add(startDate);


    //the textfield
    endDate = new JTextField(8);
    this.add(endDate);


    //the textfield
    interval = new JTextField(8);
    this.add(interval);


    //echobutton
    echoButton = new JButton("Create a fixed portfolio");
    echoButton.setActionCommand("CreateFixed Echo Button");
    this.add(echoButton);


    //exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("CreateFixed Exit Button");
    this.add(exitButton);


    //pack();
    setVisible(false);

  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    echoButton.addActionListener(actionListener);
    exitButton.addActionListener(actionListener);
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
    return "createfixed " + portfolioName.getText() + " " + equal.getText() + " "
            + companyNumber.getText() + " " + companyNameSet.getText() + " " + amount.getText()
            + " " + onGoing.getText() + " " + startDate.getText() + " " + endDate.getText() + " "
            + interval.getText();
  }

  @Override
  public void clearInputString() {
    portfolioName.setText("");
    equal.setText("");
    companyNumber.setText("");
    companyNameSet.setText("");
    amount.setText("");
    onGoing.setText("");
    startDate.setText("");
    endDate.setText("");
    interval.setText("");
  }


}


