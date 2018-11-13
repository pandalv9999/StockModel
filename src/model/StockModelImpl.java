package model;

import java.util.HashMap;
import java.util.Map;

public class StockModelImpl implements StockModel {

  private Map<String, Map<String, Stock>> portfolio;
  private AlphaVantageImpl alphaVantage;

  private StockModelImpl() {
    this.portfolio = new HashMap<>();
    this.alphaVantage = new AlphaVantageImpl();
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {

    if (portfolioName == null || portfolioName.isEmpty()) {
      throw new IllegalArgumentException("Portfolio name is invalid.");
    }

    Map<String, Stock> temp = new HashMap<>();
    portfolio.put(portfolioName, temp);
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
      price = alphaVantage.getLowPrice(code, date);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("There is no information for the provided company name.");
    }

    if (currentPortfolio.get(code) != null) { // not null: contains the stock of the company.

      Stock st = currentPortfolio.get(code);
      int oldShares = st.getShares();
      double oldPrice = st.getAverageBuyInPrice();
      int newShares = oldShares + shares;
      double newPrice = (oldPrice * oldShares + price * shares) / (shares + oldShares);
      currentPortfolio.remove(code);
      currentPortfolio.put(code, new StockImpl(code, newShares, newPrice));

    }

    else  {
      currentPortfolio.put(code, new StockImpl(code, shares, price));
    }

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
            .mapToDouble(b -> alphaVantage.getHighPrice(b.getCode(), date) * b.getShares())
            .sum();
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

    private StockModelBuilderImpl() {
    }

    public StockModel build() {
      return new StockModelImpl();
    }
  }
}
