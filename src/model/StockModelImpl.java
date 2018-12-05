package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class implements the stock Model.
 */

public class StockModelImpl implements StockModel {

  private Map<String, Map<String, Stock>> portfolio;
  private Map<String, Integer> counter;
  private Map<String, Map<String, Double>> percentages;
  private AlphaVantageImpl alphaVantage;
  private final double commissionFee;

  /**
   * The constructor build up a stock model with certain commission fee.
   *
   * @param commissionFee The commission fee in every transactions.
   */
  private StockModelImpl(double commissionFee) {
    this.portfolio = new HashMap<>();
    this.alphaVantage = AlphaVantageImpl.getInstance();
    this.commissionFee = commissionFee;
    this.counter = new HashMap<>();
    this.percentages = new HashMap<>();
  }

  /**
   * This method will count how many shares you can buy with an amount of money.
   *
   * @param companyName The company name you want to buy.
   * @param date        The date you want to buy.
   * @param priceType   The price type you want to buy.
   * @param amount      The amount of investment.
   * @return The amount of shares you can buy with this money.
   */
  private double countShares(String companyName, String date, String priceType, double amount) {

    double price;

    try {
      String code = alphaVantage.searchCode(companyName);
      price = alphaVantage.getPrice(code, date, priceType);
    } catch (IllegalArgumentException e) {
      throw e;
    }
    return amount / price;
  }

  /**
   * This method get the last available buying date of a certain company.
   *
   * @param code The company's you want to buy.
   * @return The string of date you can buy.
   */
  private String getLastAvailableDate(String code) {

    String nextDate = "";

    for (int i = 0; i < 10; i++) {

      try {
        Calendar today = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        today.setTime(date);
        today.add(Calendar.DAY_OF_YEAR, -i);
        nextDate = format.format(today.getTime());
        alphaVantage.getClosePrice(code, nextDate);
        break;

      } catch (Exception e) {
        // do nothing
      }
    }
    return nextDate;
  }

  /**
   * This method will find the next available trading date you can buy. The biggest interval is 15
   * days.
   *
   * @param curDate The date you want to buy.
   * @param code    The code of the company you want to buy.
   * @return The date you can buy.
   */
  private String getNextAvailableDate(String curDate, String code) {

    String nextDate = "";

    for (int i = 0; i < 15; i++) {

      try {
        Calendar today = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(curDate);
        today.setTime(date);
        today.add(Calendar.DAY_OF_YEAR, i);
        nextDate = format.format(today.getTime());
        alphaVantage.getClosePrice(code, nextDate);
        break;

      } catch (Exception e) {
        // do nothing
      }
    }
    if (nextDate.equals("")) {
      throw new IllegalArgumentException("This company does not have record in this month");
    }
    return nextDate;
  }


  /**
   * This method is a helper function. It will get a date that is n days after the current date you
   * provide.
   *
   * @param curDate The current date.
   * @param n       The interval's length.
   * @return A date string that is n days after the current date you provide.
   */
  private String getNextNDate(String curDate, int n) {

    String nextDate = "";

    try {
      Calendar today = Calendar.getInstance();
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      Date date = format.parse(curDate);
      today.setTime(date);
      today.add(Calendar.DAY_OF_YEAR, n);
      nextDate = format.format(today.getTime());

    } catch (Exception e) {
      return nextDate;
    }

    return nextDate;
  }

  /**
   * This method will compare two given dates and tell you which is earlier.
   *
   * @param dateOne The date you want to compare.
   * @param dateTwo The date you want to compare.
   * @return which is earlier
   */
  private int compareDate(String dateOne, String dateTwo) {

    int yearOne = Integer.parseInt(dateOne.substring(0, 4));
    int yearTwo = Integer.parseInt(dateTwo.substring(0, 4));
    int monthOne = Integer.parseInt(dateOne.substring(5, 7));
    int monthTwo = Integer.parseInt(dateTwo.substring(5, 7));
    int dayOne = Integer.parseInt(dateOne.substring(8));
    int dayTwo = Integer.parseInt(dateTwo.substring(8));

    if (yearOne > yearTwo) {
      return 1;
    } else if (yearOne < yearTwo) {
      return -1;
    } else {
      if (monthOne > monthTwo) {
        return 1;
      } else if (monthOne < monthTwo) {
        return -1;
      } else {
        return Integer.compare(dayOne, dayTwo);
      }
    }
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {

    if (portfolioName == null || portfolioName.isEmpty() || portfolio.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Portfolio name is invalid.");
    }

    Map<String, Stock> temp = new HashMap<>();
    portfolio.put(portfolioName, temp);
    counter.put(portfolioName, 0);
  }

  @Override
  public double createPortfolio(String portfolioName, Map<String, Double> information,
                                double amt, String date)
          throws IllegalArgumentException {

    if (information == null || information.isEmpty() || date.isEmpty()) {
      throw new IllegalArgumentException("Invalid argument!");
    }

    if (Double.compare(information.values().stream().mapToDouble(b -> b).sum(), 1.0) != 0) {
      throw new IllegalArgumentException("The sum of all percentage is not one!");
    }

    String randomCode = "";
    for (String name : information.keySet()) {
      try {
        randomCode = alphaVantage.searchCode(name);
        break;
      } catch (IllegalArgumentException e) {
        // do nothing
      }
    }
    if (randomCode.equals("")) {
      throw new IllegalArgumentException("None of the companies exists!");
    }

    //Automatically get the date.
    if (date.equals("N") || date.equals("n")) {
      date = getLastAvailableDate(randomCode);
    }

    createPortfolio(portfolioName);
    this.percentages.put(portfolioName, information);

    return buyByPercentage(portfolioName, portfolioName, amt, date);
  }

  @Override
  public void createPercentage(String percentagesName, Map<String, Double> information)
          throws IllegalArgumentException {

    if (information == null || information.isEmpty() || percentagesName == null
            || percentagesName.equals("")) {
      throw new IllegalArgumentException("Invalid argument!");
    }

    if (Double.compare(information.values().stream().mapToDouble(b -> b).sum(), 1.0) != 0) {
      throw new IllegalArgumentException("The sum of all percentage is not one!");
    }

    if (this.percentages.containsKey(percentagesName)) {
      throw new IllegalArgumentException("The investment plan's name already exists!");
    }

//    String randomCode = "";
//    for (String name : information.keySet()) {
//      try {
//        randomCode = alphaVantage.searchCode(name);
//        if (randomCode.equals("")) {
//          throw new IllegalArgumentException("Some of the companies does not exist!");
//        }
//      } catch (IllegalArgumentException e) {
//        throw new IllegalArgumentException("Some of the companies does not exist!");
//      }
//    }

    this.percentages.put(percentagesName, information);
  }

  @Override
  public double dollarCostAverage(String portfolioName, Map<String, Double> information,
                                  double amt, String startDate, String endDate, int interval)
          throws IllegalArgumentException {

    if (information == null || information.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
      throw new IllegalArgumentException("Invalid argument!");
    }

    String randomCode = "";
    for (String name : information.keySet()) {
      try {
        randomCode = alphaVantage.searchCode(name);
        break;
      } catch (IllegalArgumentException e) {
        // do nothing
      }
    }
    if (randomCode.equals("")) {
      throw new IllegalArgumentException("None of the companies exists!");
    }

    //Automatically get the start date and end date.
    if (startDate.equals("N") || startDate.equals("n")) {
      startDate = getLastAvailableDate(randomCode);
    }

    if (endDate.equals("N") || endDate.equals("n")) {
      endDate = getLastAvailableDate(randomCode);
    }

    double totalCost = createPortfolio(portfolioName, information, amt, startDate);
    String nextDate = getNextNDate(startDate, interval);

    while (compareDate(nextDate, endDate) < 0) {

      for (Map.Entry<String, Double> e : information.entrySet()) {

        String company = e.getKey();
        String code = alphaVantage.searchCode(company);
        String buyDate = getNextAvailableDate(nextDate, code); // Use a new function instead.
        double specificMoney = amt * e.getValue();
        double numOfShares = countShares(company, buyDate, "close", specificMoney);
        totalCost += buy(portfolioName, company, numOfShares, buyDate, "close");

      }
      nextDate = getNextNDate(nextDate, interval);
    }
    return totalCost;
  }

  @Override
  public double buy(String portfolioName, String companyName,
                    double shares, String date, String priceType) throws IllegalArgumentException {

    if (shares <= 0) {
      throw new IllegalArgumentException("The shares to buy must be positive");
    }

    if (!portfolio.containsKey(portfolioName)) {
      throw new IllegalArgumentException("The portfolio is not yet created!");
    }

    if (date == null || portfolioName == null || companyName == null || date.isEmpty()
            || priceType == null || priceType.isEmpty()) {
      throw new IllegalArgumentException("String entered is invalid.");
    }

    String code;
    double price;
    Map<String, Stock> currentPortfolio = portfolio.get(portfolioName);

    try {
      code = alphaVantage.searchCode(companyName);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("There is no information for the provided company name.");
    }

    // Automatically fill the date.
    if (date.equals("N") || date.equals("n")) {
      date = getLastAvailableDate(code);
    }

    try {
      price = alphaVantage.getPrice(code, date, priceType);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("There is no price on this date.");
    }

    if (currentPortfolio.get(code) != null) { // not null: contains the stock of the company.

      Stock st = currentPortfolio.get(code);
      double oldShares = st.getShares();
      double oldPrice = st.getAverageBuyInPrice();
      double newShares = oldShares + shares;
      double newPrice = (oldPrice * oldShares + price * shares) / (shares + oldShares);
      // currentPortfolio.remove(code);
      currentPortfolio.put(code, new StockImpl(code, newShares, newPrice));

    } else {
      currentPortfolio.put(code, new StockImpl(code, shares, price));
    }

    int count = counter.get(portfolioName);
    // counter.remove(portfolioName);
    counter.put(portfolioName, count + 1);
    return price * shares; // return the total cost
  }

  @Override
  public double buyByAmount(String portfolioName, String companyName,
                            double amount, String date) {
    String code;
    try {
      code = alphaVantage.searchCode(companyName);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("There is no information for the provided company name.");
    }
    if (date.equals("N") || date.equals("n")) {
      date = getLastAvailableDate(code);
    }
    double totalAmt = 0.0;
    double numOfShares = countShares(companyName, date, "low", amount);
    totalAmt += buy(portfolioName, companyName, numOfShares, date, "low");
    return totalAmt;
  }

  @Override
  public double determineCost(String portfolioName) throws IllegalArgumentException {

    if (!portfolio.containsKey(portfolioName)) {
      throw new IllegalArgumentException("The portfolio is not yet created!");
    }

    return portfolio.get(portfolioName).values()
            .stream()
            .mapToDouble(b -> b.getAverageBuyInPrice() * b.getShares())
            .sum();
  }

  @Override
  public double determineValue(String portfolioName, String date) throws IllegalArgumentException {

    if (date == null || date.isEmpty()) {
      throw new IllegalArgumentException("Date entered is invalid.");
    }

    String date2;
    if (date.equals("N") || date.equals("n")) {
      date2 = getLastAvailableDate("GOOG");
    } else {
      date2 = date;
    }


    return portfolio.get(portfolioName).values()
            .stream()
            .mapToDouble(b -> alphaVantage.getPrice(b.getCode(), date2, "high") * b.getShares())
            .sum();
  }

  @Override
  public List<Double> determineValuePlot(String portfolioName) throws IllegalArgumentException {

    if (portfolioName == null || portfolioName.isEmpty() || portfolioName.equals("")) {
      throw new IllegalArgumentException("Portfolio name should not be empty");
    }

    if (portfolio.get(portfolioName) == null) {
      throw new IllegalArgumentException("This portfolio does not exist.");

    }

    List<Double> res = new ArrayList();

    String endDate = getLastAvailableDate("GOOG");


    String nextDate = getNextNDate(endDate, -365);

    for (int i = 0; i < 12; i++) {
      String buyDate = getNextAvailableDate(nextDate, "GOOG");
      res.add(determineValue(portfolioName, buyDate));
      nextDate = getNextNDate(nextDate, 30);
    }


    return res;
  }


  @Override
  public double determineCommissionFee(String portfolioName) throws IllegalArgumentException {
    if (counter.get(portfolioName) == null) {
      throw new IllegalArgumentException("There is no such portfolio.");
    }
    return counter.get(portfolioName) * commissionFee;
  }

  @Override
  public double buyByPercentage(String portfolioName, String percentagesName, double amt, String date)
          throws IllegalArgumentException {

    double totalAmt = 0.0;
    Map<String, Double> information;
    try {
      information = this.percentages.get(percentagesName);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("There is no such buying plan.");
    }

    if (date.equals("n") || date.equals("N")) {
      for (Map.Entry<String, Double> e : information.entrySet()) {
        try {
          String company = e.getKey();
          String randomCode = alphaVantage.searchCode(company);
          date = getLastAvailableDate(randomCode);
          break;
        } catch (IllegalArgumentException g) {
          continue;
        }
      }
    }

    for (Map.Entry<String, Double> e : information.entrySet()) {
      String company = e.getKey();
      double specificMoney = amt * e.getValue();
      String buyDate = getNextAvailableDate(date, alphaVantage.searchCode(company));
      double numOfShares = countShares(company, buyDate, "close", specificMoney);
      totalAmt += buy(portfolioName, company, numOfShares, buyDate, "close");
    }

    return totalAmt;
  }

  @Override
  public String getPortfolioState() {

    StringBuilder state = new StringBuilder();

    for (String pf : portfolio.keySet()) {
      state.append(getPortfolioState(pf)).append("\n");
    }

    return state.toString();
  }

  @Override
  public String getPortfolioState(String portfolioName) throws IllegalArgumentException {

    if (!portfolio.containsKey(portfolioName)) {
      throw new IllegalArgumentException("The portfolio is not yet created!");
    }

    StringBuilder state = new StringBuilder().append(portfolioName).append(":\n");

    for (Stock st : portfolio.get(portfolioName).values()) {
      state.append(st.getCurrentState()).append("\n");
    }
    state.append("The commission fee of this portfolio is $")
            .append(String.format("%.2f", determineCommissionFee(portfolioName))).append("\n");
    return state.toString();
  }

  /**
   * This function will write some content to a csv file.
   *
   * @param fileName the file you want to write
   * @param content  the content you want to write
   */
  private void outputToFile(String fileName, String content) {
    try {
      File writeName = new File(fileName);
      writeName.createNewFile();
      BufferedWriter out = new BufferedWriter(new FileWriter(writeName));
      out.write(content);
      out.flush();
      out.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void savePortfolio(String portfolioName, String fileName) {

    if (portfolioName.equals("") || fileName.equals("")) {
      throw new IllegalArgumentException("The names should not be empty!");
    }

    if (!portfolio.containsKey(portfolioName)) {
      throw new IllegalArgumentException("The portfolio is not yet created!");
    }

    StringBuilder state = new StringBuilder().append(portfolioName).append(",");
    String res;

    for (Stock st : portfolio.get(portfolioName).values()) {
      state.append(st.getCode()).append(",")
              .append(st.getShares()).append(",")
              .append(st.getAverageBuyInPrice()).append(",");
    }
    state.append(counter.get(portfolioName));
    res = state.toString();
    outputToFile(fileName + ".csv", res);
  }

  @Override
  public void savePercentage(String percentageName, String fileName) {

    if (percentageName.equals("") || fileName.equals("")) {
      throw new IllegalArgumentException("The names should not be empty!");
    }

    if (!percentages.containsKey(percentageName)) {
      throw new IllegalArgumentException("The investing plan is not yet created!");
    }

    StringBuilder state = new StringBuilder().append(percentageName).append(",");
    Map<String, Double> percentage = this.percentages.get(percentageName);
    String res;

    for (Map.Entry<String, Double> e : percentage.entrySet()) {
      state.append(e.getKey()).append(",").append(e.getValue()).append(",");
    }
    res = state.toString();
    outputToFile(fileName + ".csv", res);

  }

  /**
   * This function will read from a csv file and save it as an array of strings.
   *
   * @param fileName the file you want to read
   * @return the file's content in a string array
   */
  private String[] readFile(String fileName) throws IllegalArgumentException {
    StringBuilder state = new StringBuilder();
    String res;
    String line = "";
    try {
      BufferedReader in = new BufferedReader(new FileReader(fileName));
      line = in.readLine();
      while (line != null) {
        state.append(line);
        line = in.readLine();
      }
      in.close();
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot find this file.");
    }
    res = state.toString();
    return res.split(",");
  }

  @Override
  public void loadPortfolio(String fileName) {
    String[] st;
    try {
      st = readFile(fileName);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    if (st[0].equals("")) {
      throw new IllegalArgumentException("Portfolio name should not be empty.");
    }
    if (st[st.length - 1].equals("")) {
      throw new IllegalArgumentException("Commission fee counter should not be empty.");
    }
    int commissionCount = 0;
    try {
      commissionCount = Integer.parseInt(st[st.length - 1]);
    } catch (Exception e) {
      throw new IllegalArgumentException("Commission fee counter should be integer.");
    }
    createPortfolio(st[0]);
    this.counter.put(st[0], commissionCount);
    Map<String, Stock> currentPortfolio = this.portfolio.get(st[0]);

    int start = 1;
    while (start + 2 < st.length) {

      String code = st[start];
      double shares;
      double price;

      try {
        shares = Double.parseDouble(st[start + 1]);
      } catch (Exception e) {
        throw new IllegalArgumentException("Share should be double.");
      }

      try {
        price = Double.parseDouble(st[start + 2]);
      } catch (Exception e) {
        throw new IllegalArgumentException("Price should be double.");
      }

      currentPortfolio.put(code, new StockImpl(code, shares, price));
      start += 3;
    }

  }

  @Override
  public void loadPercentage(String fileName) {

    String[] st;
    try {
      st = readFile(fileName);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    if (st[0].equals("")) {
      throw new IllegalArgumentException("Portfolio name should not be empty.");
    }
    Map<String, Double> information = new HashMap<>();
    int start = 1;
    while (start + 1 < st.length) {

      String companyName = st[start];
      double proportion;

      try {
        proportion = Double.parseDouble(st[start + 1]);
      } catch (Exception e) {
        throw new IllegalArgumentException("Proportion should be double.");
      }
      information.put(companyName, proportion);

      start += 2;
    }
    createPercentage(st[0], information);

  }

  /**
   * This will get you the builder of this class.
   */
  public static StockModelBuilderImpl getBuilder() {
    return new StockModelBuilderImpl();
  }

  /**
   * This class is the builder of this stock model class.
   */
  public static class StockModelBuilderImpl implements StockModelBuilder {

    private double fee;

    private StockModelBuilderImpl() {
      this.fee = 0.0;
    }

    @Override
    public StockModelBuilder commissionFee(double fee) {
      if (fee < 0) {
        throw new IllegalArgumentException("Fee should be at least 0.");
      }
      this.fee = fee;
      return this;
    }

    public StockModel build() {
      return new StockModelImpl(this.fee);
    }
  }
}
