package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class CreatePercentageView extends JFrame implements IView {
  private JLabel display, display2, display3, display4;
  private JButton echoButton, exitButton;
  private JTextField percentageName, equal, companyNumber;
  private JTextArea sTextArea, companyNameSet;

  public CreatePercentageView(String caption) {
    super(caption);
    setSize(900, 600);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.setResizable(false);
//		this.setMinimumSize(new Dimension(300,300));


    this.setLayout(new FlowLayout());
    this.setLayout(null);


    //output area
    sTextArea = new JTextArea("Result will be displayed here.", 10, 20);
    JScrollPane scrollPane = new JScrollPane(sTextArea);
    sTextArea.setLineWrap(true);
    //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Result"));
    scrollPane.setBounds(500, 20, 340, 470);
    this.add(scrollPane);


    display = new JLabel("Please input the investing plan's name.");
    display.setBounds(20, 10, 800, 20);
    this.add(display);

    //the textfield
    percentageName = new JTextField(5);
    percentageName.setBounds(20, 40, 200, 20);
    this.add(percentageName);


    display2 = new JLabel("Equal or seperate? (E/S)");
    display2.setBounds(20, 80, 800, 20);
    this.add(display2);


    //the textfield
    equal = new JTextField(8);
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

//    (
//            //output area
//            sTextArea = new JTextArea("Result will be displayed here.", 10, 20);
//    JScrollPane scrollPane = new JScrollPane(sTextArea);
//    sTextArea.setLineWrap(true);
//    //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//    scrollPane.setBorder(BorderFactory.createTitledBorder("Result"));
//    scrollPane.setBounds(500, 20, 340, 470);
//    this.add(scrollPane);)


    //echobutton
    echoButton = new JButton("Create an investing plan");
    echoButton.setActionCommand("CreatePercentage Echo Button");
    echoButton.setBounds(20, 460, 180, 20);
    this.add(echoButton);


    //exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("CreatePercentage Exit Button");
    exitButton.setBounds(220, 460, 80, 20);
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
    sTextArea.setText(s);
  }

  @Override
  public String getInputString() {
    return "createp " + percentageName.getText() + " " + equal.getText() + " "
            + companyNumber.getText() + " " + companyNameSet.getText();
  }

  @Override
  public void clearInputString() {
    percentageName.setText("");
    equal.setText("");
    companyNumber.setText("");
    companyNameSet.setText("");
  }


}


