package view;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;

import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class DetermineValueView extends JFrame implements IView {
  private JLabel display, display2;
  private JButton echoButton, exitButton, plotButton;
  private JTextField portfolioName, date;
  private JTextArea sTextArea;

  public DetermineValueView(String caption) {
    super(caption);
    setSize(900, 600);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.setResizable(false);
//		this.setMinimumSize(new Dimension(300,300));


    this.setLayout(new FlowLayout());
    this.setLayout(null);
    display = new JLabel("Portfolio's name.");
    display.setBounds(20, 10, 800, 20);
    this.add(display);

    //the textfield
    portfolioName = new JTextField(5);
    portfolioName.setBounds(20, 40, 200, 20);
    this.add(portfolioName);

    //output area
    sTextArea = new JTextArea("Result will be displayed here. If you want to plot a "
            + "portfolio's value in the last 12 months, entering the name is just fine.",
            10, 20);
    JScrollPane scrollPane = new JScrollPane(sTextArea);
    sTextArea.setLineWrap(true);
    //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Result"));
    scrollPane.setBounds(500, 20, 340, 470);
    this.add(scrollPane);


    display2 = new JLabel("Date: yyyyMMdd or N/n");
    display2.setBounds(20, 80, 800, 20);
    this.add(display2);

    //the textfield
    date = new JTextField(10);
    date.setBounds(20, 110, 200, 20);
    this.add(date);

    //echobutton
    echoButton = new JButton("Determine Value");
    echoButton.setActionCommand("DetermineValue Echo Button");
    echoButton.setBounds(20, 460, 150, 20);
    this.add(echoButton);


    //echobutton
    plotButton = new JButton("Plot Value in 12 months");
    plotButton.setActionCommand("DetermineValue Plot Button");
    plotButton.setBounds(190, 460, 200, 20);
    this.add(plotButton);

    //exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("DetermineValue Exit Button");
    exitButton.setBounds(410, 460, 80, 20);
    this.add(exitButton);


    //pack();
    setVisible(false);

  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    echoButton.addActionListener(actionListener);
    plotButton.addActionListener(actionListener);
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

  public void plot(List<Double> data) {
    final JFrame frame = new JFrame("Portfolio's value in the last 12 months");
    frame.setSize(800, 600);
    double[] xData = new double[12];
    double[] yData = new double[12];
    for (int i = 0; i < 12; i++) {
      xData[i] = i - 12;
      yData[i] = data.get(i);
    }

    // Create Chart
    XYChart chart = QuickChart.getChart("Portfolio's value in the last 12 months",
            "months", "dollars", "change of portfolio's value", xData, yData);

    XChartPanel chartPanel = new XChartPanel(chart);
    chartPanel.setBounds(20, 80, 800, 600);
    frame.add(chartPanel);
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public String getInputString() {
    return "determinevalue " + portfolioName.getText() + " " + date.getText();
  }

  public String getPlotInputString() {
    return portfolioName.getText();
  }

  @Override
  public void clearInputString() {
    portfolioName.setText("");
    date.setText("");
  }


}


