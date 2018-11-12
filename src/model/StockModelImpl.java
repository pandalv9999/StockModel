package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockModelImpl implements StockModel {

  private Map<String, Map<String, Stock>> portfolio;
  private AlphaVantageImpl alphaVantage;

  private StockModelImpl() {
    this.portfolio = new HashMap<>();
    this.alphaVantage = new AlphaVantageImpl();
  }

  @Override
  public void createPortfolio(String portfolioName) {
    Map<String, Stock> temp = new HashMap<>();
    portfolio.put(portfolioName, temp);
  }

  @Override
  public double buy(String portfolioName, String CompanyName, int shares) throws IllegalArgumentException {

    if (shares <= 0) {
      throw new IllegalArgumentException("The shares to buy must be positive");
    }

    if (!portfolio.containsKey(portfolioName)) {
      throw new IllegalArgumentException("The portfolio is not yet created!");
    }

    String code;
    double price;
    boolean existStock = false;
    Map<String, Stock> currentPortfolio = portfolio.get(portfolioName);

    try {
      code = alphaVantage.searchCode(CompanyName);
      price = 0.0;  // use the api to calculate price, but still confused how to do it.
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("There is no information for the provided company name.");
    }

    for (Stock st:currentPortfolio.values()) {

      if (st.getCode().equals(code)) {  // exist current company's share
        existStock = true;
        int oldShares = st.getShares();
        double oldPrice = st.getAverageBuyInPrice();
        int newShares = oldShares + shares;
        double newPrice = (oldPrice * oldShares + price * shares) / (shares + oldShares);
        currentPortfolio.remove(code);
        currentPortfolio.put(code, new StockImpl(code, newShares, newPrice));
      }
    }

    if (! existStock) {
      currentPortfolio.put(code, new StockImpl(code, shares, price));
    }

    return price;
  }

  @Override
  public double determineValue(String date) {
    return 0;
  }

  @Override
  public String getPortfolioState() {
    return null;
  }

  public static StockModelBuilderImpl getBuilder() {
    return new StockModelBuilderImpl();
  }

  public static class StockModelBuilderImpl implements StockModelBuilder {

    private StockModelBuilderImpl() {
    }

    public StockModelBuilderImpl build() {
      return new StockModelBuilderImpl();
    }
  }
}
