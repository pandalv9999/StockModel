package model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockModelImpl implements StockModel {

  private Map<String, Map<String, Stock>> portfolio;
  private Map<String, Integer> counter;
  private Map<String, Map<String, Double>> percentages;
  private AlphaVantageImpl alphaVantage;
  private final double commissionFee;

  private StockModelImpl(double commissionFee) {
    this.portfolio = new HashMap<>();
    this.alphaVantage = AlphaVantageImpl.getInstance();
    this.commissionFee = commissionFee;
    this.counter = new HashMap<>();
    this.percentages = new HashMap<>();
  }

  // priceType can be high, low, open, close
  private double countShares(String companyName, String date, String priceType, double amount) {
    double price = 0.0;
    try {
      String code = alphaVantage.searchCode(companyName);
      price = alphaVantage.getPrice(code, date, priceType);
    } catch (IllegalArgumentException e) {
      throw e;
    }
    return amount / price;
  }

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

  private String getNextAvailableDate(String curDate, String code) {
    String nextDate = "";
    for (int i = 0; i < 10; i++) {
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
  public double createPortfolio(String portfolioName, Map<String, Double> information, double amt, String date)
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

    return buyByPercentage(portfolioName, amt, date);
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
//        System.out.println(company);
//        System.out.println(buyDate);
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
      currentPortfolio.remove(code);
      currentPortfolio.put(code, new StockImpl(code, newShares, newPrice));

    } else {
      currentPortfolio.put(code, new StockImpl(code, shares, price));
    }

    int count = counter.get(portfolioName);
    counter.remove(portfolioName);
    counter.put(portfolioName, count + 1);
    return price * shares; // return the total cost
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

    return portfolio.get(portfolioName).values()
            .stream()
            .mapToDouble(b -> alphaVantage.getPrice(b.getCode(), date, "high") * b.getShares())
            .sum();
  }

  @Override
  public double determineCommissionFee(String portfolioName) {
    return counter.get(portfolioName) * commissionFee;
  }

  @Override
  public double buyByPercentage(String portfolioName, double amt, String date) throws IllegalArgumentException {

    double totalAmt = 0.0;
    Map<String, Double> information;
    try {
      information = this.percentages.get(portfolioName);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("There is no such buying plan.");
    }

    for (Map.Entry<String, Double> e : information.entrySet()) {
      double specificMoney = amt * e.getValue();
      String company = e.getKey();
      double numOfShares = countShares(company, date, "close", specificMoney);
      totalAmt += buy(portfolioName, company, numOfShares, date, "close"); // change previous share to double
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

    return state.toString();
  }

  public static StockModelBuilderImpl getBuilder() {
    return new StockModelBuilderImpl();
  }

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
