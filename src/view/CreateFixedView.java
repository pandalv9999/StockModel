package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.event.ActionListener;

/**
 * This class represents the view of creating a fixed portfolio with certain buying plan..
 */
public class CreateFixedView extends JFrame implements IView {
  private JLabel display;
  private JButton echoButton;
  private JButton exitButton;
  private JTextField portfolioName;
  private JTextField equal;
  private JTextField companyNumber;
  private JTextField amount;
  private JTextField onGoing;
  private JTextField startDate;
  private JTextField endDate;
  private JTextField interval;
  private JTextArea sTextArea;
  private JTextArea companyNameSet;

  /**
   * This is the constructor of this view. It will set up the places and id of the buttons and text
   * areas.
   *
   * @param caption the view's title
   */
  public CreateFixedView(String caption) {
    super(caption);
    JLabel display2;
    JLabel display3;
    JLabel display4;
    JLabel display5;
    JLabel display6;
    JLabel display7;
    JLabel display8;
    JLabel display9;
    setSize(900, 900);
    setLocation(100, 100);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    this.setLayout(null);

    //output area
    sTextArea = new JTextArea("Result will be displayed here.", 10, 20);
    JScrollPane scrollPane = new JScrollPane(sTextArea);
    sTextArea.setLineWrap(true);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Result"));
    scrollPane.setBounds(500, 20, 340, 470);
    this.add(scrollPane);

    display = new JLabel("Please input the portfolio's name.");
    display.setBounds(20, 10, 800, 20);
    this.add(display);

    //the textfield
    portfolioName = new JTextField(5);
    portfolioName.setBounds(20, 40, 200, 20);
    this.add(portfolioName);


    display2 = new JLabel("Equal or seperate? (E/S)");
    display2.setBounds(20, 80, 800, 20);
    this.add(display2);


    //the textfield
    equal = new JTextField("E/S", 8);
    equal.setBounds(20, 110, 200, 20);
    this.add(equal);

    display3 = new JLabel("Number of companies");
    display3.setBounds(20, 150, 800, 20);
    this.add(display3);


    //the textfield
    companyNumber = new JTextField(8);
    companyNumber.setBounds(20, 180, 200, 20);
    this.add(companyNumber);


    display4 = new JLabel("One company name one line. If separated you should provide "
            + "proportion.");
    display4.setBounds(20, 220, 800, 20);
    this.add(display4);


    //the textfield
    companyNameSet = new JTextArea("e.g separated, 3 companies:\n"
            + "facebook 30\n"
            + "google 30\n"
            + "netflix 40\n\n\n"
            + "e.g equal, two companies:\n"
            + "facebook\n"
            + "google\n"
            + "netflix\n", 10, 20);
    JScrollPane scrollPane2 = new JScrollPane(companyNameSet);
    companyNameSet.setLineWrap(true);
    //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane2.setBorder(BorderFactory.createTitledBorder("Company set"));
    scrollPane2.setBounds(20, 250, 300, 200);
    this.add(scrollPane2);

    display5 = new JLabel("Amount of investment");
    display5.setBounds(20, 470, 800, 20);
    this.add(display5);

    //the textfield
    amount = new JTextField(8);
    amount.setBounds(20, 500, 200, 20);
    this.add(amount);

    display6 = new JLabel("Ongoing or not? (Y/N)");
    display6.setBounds(20, 540, 800, 20);
    this.add(display6);

    //the textfield
    onGoing = new JTextField("Y/N", 8);
    onGoing.setBounds(20, 570, 200, 20);
    this.add(onGoing);

    display7 = new JLabel("Starting date or (N/n)");
    display7.setBounds(20, 610, 800, 20);
    this.add(display7);


    //the textfield
    startDate = new JTextField("yyyyMMdd/N/n", 8);
    startDate.setBounds(20, 640, 200, 20);
    this.add(startDate);

    display8 = new JLabel("Ending date or (N/n)");
    display8.setBounds(20, 680, 800, 20);
    this.add(display8);

    //the textfield
    endDate = new JTextField("yyyyMMdd/N/n", 8);
    endDate.setBounds(20, 710, 200, 20);
    this.add(endDate);


    display9 = new JLabel("Buying interval or (N/n)");
    display9.setBounds(20, 750, 800, 20);
    this.add(display9);

    //the textfield
    interval = new JTextField(8);
    interval.setBounds(20, 780, 200, 20);
    this.add(interval);


    //echobutton
    echoButton = new JButton("Create a fixed portfolio");
    echoButton.setActionCommand("CreateFixed Echo Button");
    echoButton.setBounds(500, 510, 180, 20);
    this.add(echoButton);


    //exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("CreateFixed Exit Button");
    exitButton.setBounds(700, 510, 80, 20);
    this.add(exitButton);

    setVisible(false);

  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    echoButton.addActionListener(actionListener);
    exitButton.addActionListener(actionListener);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void toggleColor() {
    if (this.display.getForeground().equals(Color.RED)) {
      this.display.setForeground(Color.BLACK);
    } else {
      this.display.setForeground(Color.RED);
    }
  }


  @Override
  public void setEchoOutput(String s) {
    sTextArea.setText(s);
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
    equal.setText("E/S");
    companyNumber.setText("");
    companyNameSet.setText("");
    amount.setText("");
    onGoing.setText("Y/N");
    startDate.setText("yyyyMMdd/N/n");
    endDate.setText("yyyyMMdd/N/n");
    interval.setText("integer/N/n");
  }


}


