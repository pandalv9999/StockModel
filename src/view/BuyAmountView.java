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
 * This class represents the view of buying a stock by amount.
 */
public class BuyAmountView extends JFrame implements IView {
  private JLabel display;
  private JButton echoButton;
  private JButton exitButton;
  private JTextField portfolioName;
  private JTextField date;
  private JTextField companyName;
  private JTextField amount;
  private JTextArea sTextArea;

  /**
   * This is the constructor of this view. It will set up the places and id of the buttons and text
   * areas.
   *
   * @param caption the view's title
   */
  public BuyAmountView(String caption) {
    super(caption);
    JLabel display2;
    JLabel display3;
    JLabel display4;
    setSize(900, 600);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(null);

    //output area
    sTextArea = new JTextArea("Result will be displayed here.", 10, 20);
    JScrollPane scrollPane = new JScrollPane(sTextArea);
    sTextArea.setLineWrap(true);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Result"));
    scrollPane.setBounds(500, 20, 340, 470);
    this.add(scrollPane);

    display = new JLabel("Portfolio's name");
    display.setBounds(20, 10, 800, 20);
    this.add(display);

    //the textfield
    portfolioName = new JTextField(5);
    portfolioName.setBounds(20, 40, 200, 20);
    this.add(portfolioName);

    display2 = new JLabel("Company's name");
    display2.setBounds(20, 80, 800, 20);
    this.add(display2);

    //the textfield
    companyName = new JTextField(8);
    companyName.setBounds(20, 110, 200, 20);
    this.add(companyName);

    display3 = new JLabel("Amount of investment");
    display3.setBounds(20, 150, 800, 20);
    this.add(display3);

    //the textfield
    amount = new JTextField(8);
    amount.setBounds(20, 180, 200, 20);
    this.add(amount);

    display4 = new JLabel("Date: yyyyMMdd or N/n");
    display4.setBounds(20, 220, 800, 20);
    this.add(display4);

    //the textfield
    date = new JTextField(8);
    date.setBounds(20, 250, 200, 20);
    this.add(date);

    //echobutton
    echoButton = new JButton("Buy a stock");
    echoButton.setActionCommand("BuyAmount Echo Button");
    echoButton.setBounds(20, 460, 150, 20);
    this.add(echoButton);

    //exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("BuyAmount Exit Button");
    exitButton.setBounds(190, 460, 80, 20);
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
    return "buya " + portfolioName.getText() + " " + companyName.getText() + " "
            + amount.getText() + " " + date.getText();
  }

  @Override
  public void clearInputString() {
    portfolioName.setText("");
    date.setText("");
    companyName.setText("");
    amount.setText("");
  }


}


