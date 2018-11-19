package model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockModelImpl implements StockModel {

  private Map<String, Map<String, Stock>> portfolio;
  private Map<String, Integer> counter;
  private Map<String, List<Double>> percentages;
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

  private String getLastAvailableDate(String code, boolean previous) {
    String nextDate = "";
    for (int i = 0; i < 10; i++) {
      try {
        Calendar today = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        today.setTime(date);
        if (previous) {
          today.add(Calendar.DAY_OF_YEAR, -i);
        } else {
          today.add(Calendar.DAY_OF_YEAR, i);
        }
        nextDate = format.format(today.getTime());
        alphaVantage.getClosePrice(code, nextDate);
        break;
      } catch (Exception e) {
        continue;
      }
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
  public double createPortfolio(String portfolioName, List<String> companyName,
                                List<Double> percentage, double amt, String date)
          throws IllegalArgumentException {

    if (companyName.isEmpty() || percentage.isEmpty()
            || date.isEmpty() || percentage.size() != companyName.size()) {
      throw new IllegalArgumentException("Invalid argument!");
    }

    if (Double.compare(percentage.stream().mapToDouble(b -> b).sum(), 1.0) != 0) {
      throw new IllegalArgumentException("The sum of all percentage is not one!");
    }

    String randomCode = "";
    for (int i = 0; i < companyName.size(); i++) {
      try {
        randomCode = alphaVantage.searchCode(companyName.get(i));
        break;
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    if (randomCode.equals("")) {
      throw new IllegalArgumentException("None of the companies exists!");
    }

    //Automatically get the date.
    if (date.equals("N") || date.equals("n")) {
      date = getLastAvailableDate(randomCode, true);
    }

    double totalAmt = 0.0;

    createPortfolio(portfolioName);
    this.percentages.put(portfolioName, percentage);

    for (int i = 0; i < companyName.size(); i++) {
      double specificMoney = amt * percentage.get(i);
      String company = companyName.get(i);
      double numOfShares = countShares(company, date, "low", specificMoney);
      totalAmt += buy(portfolioName, company, (int) numOfShares, date); // change previous share to double, or just as this?
    }

    return totalAmt;
  }

  @Override
  public double dollarCostAverage(String portfolioName, List<String> companyName,
                                List<Double> percentage, double amt, String startDate,
                                String endDate)
          throws IllegalArgumentException {

    if (companyName.isEmpty() || percentage.isEmpty() || endDate.isEmpty()
            || startDate.isEmpty() || percentage.size() != companyName.size()) {
      throw new IllegalArgumentException("Invalid argument!");
    }

    if (Double.compare(percentage.stream().mapToDouble(b -> b).sum(), 1.0) != 0) {
      throw new IllegalArgumentException("The sum of all percentage is not one!");
    }

    String randomCode = "";
    for (int i = 0; i < companyName.size(); i++) {
      try {
        randomCode = alphaVantage.searchCode(companyName.get(i));
        break;
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    if (randomCode.equals("")) {
      throw new IllegalArgumentException("None of the companies exists!");
    }


    //Automatically get the start date and end date.
    if (startDate.equals("N") || startDate.equals("n")) {
      startDate = getLastAvailableDate(randomCode, true);
    }

    if (endDate.equals("N") || endDate.equals("n")) {
      endDate = getLastAvailableDate(randomCode, true);
    }

    double totalCost = createPortfolio(portfolioName, companyName, percentage, amt, startDate);
    String nextDate = getNextNDate(startDate, 30);

    while (compareDate(nextDate, endDate) < 0) {
      for (int i = 0; i < companyName.size(); i++) {
        String company = companyName.get(i);
        String code = alphaVantage.searchCode(company);
        String buyDate = getLastAvailableDate(code, false);
        double specificMoney = amt * percentage.get(i);
        double numOfShares = countShares(company, buyDate, "low", specificMoney);
        totalCost += buy(portfolioName, company, (int) numOfShares, buyDate);
        nextDate = getNextNDate(nextDate, 30);
      }
    }
    return totalCost;
  }

  @Override
  public double buy(String portfolioName, String companyName,
                    int shares, String date) throws IllegalArgumentException {

    if (shares <= 0) {
      throw new IllegalArgumentException("The shares to buy must be positive");
    }

    if (!portfolio.containsKey(portfolioName)) {
      throw new IllegalArgumentException("The portfolio is not yet created!");
    }

    if (date == null || portfolioName == null || companyName == null || date.isEmpty()) {
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
      date = getLastAvailableDate(code, true);
    }

    try {
      price = alphaVantage.getPrice(code, date, "low");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("There is no price on this date.");
    }

    if (currentPortfolio.get(code) != null) { // not null: contains the stock of the company.

      Stock st = currentPortfolio.get(code);
      int oldShares = st.getShares();
      double oldPrice = st.getAverageBuyInPrice();
      int newShares = oldShares + shares;
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
  public double buyByPercentage(String portfolioName, double amt) throws IllegalArgumentException {

    List<Double> currPercentage = percentages.get(portfolioName);
    Map<String, Stock> currPortfolio = portfolio.get(portfolioName);

    if (currPercentage == null) {
      throw new IllegalArgumentException("The portfolio has no preset percentage.");
    }

    // To Be implemented

    return 0;
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
