package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class LoadPercentageView extends JFrame implements IView {
  private JLabel display;
  private JButton echoButton, exitButton;
  private JTextField fileName;

  public LoadPercentageView(String caption) {
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
    fileName = new JTextField(10);
    this.add(fileName);

    //echobutton
    echoButton = new JButton("Load a portfolio");
    echoButton.setActionCommand("LoadPercentage Echo Button");
    this.add(echoButton);

    //exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("LoadPercentage Exit Button");
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
    return "loadpercentage " + fileName.getText();
  }

  @Override
  public void clearInputString() {
    fileName.setText("");
  }


}


