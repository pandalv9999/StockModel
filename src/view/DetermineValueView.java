package view;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * This class represents the view of determining a portfolio's value.
 */
public class DetermineValueView extends JFrame implements IView {
  private JLabel display;
  private JButton echoButton;
  private JButton exitButton;
  private JButton plotButton;
  private JTextField portfolioName;
  private JTextField date;
  private JTextArea sTextArea;

  /**
   * This is the constructor of this view. It will set up the places and id of the buttons and text
   * areas.
   *
   * @param caption the view's title
   */
  public DetermineValueView(String caption) {
    super(caption);
    JLabel display2;
    setSize(900, 600);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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


    setVisible(false);

  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    echoButton.addActionListener(actionListener);
    plotButton.addActionListener(actionListener);
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

  /**
   * This method will use the xChart package and help you plot the data in the last 12 months.
   *
   * @param data the value data need to be plotted
   */
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


