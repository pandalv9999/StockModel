package model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// still confused
public class StockModelImpl implements StockModel {
  private Map<String, StockImpl> portfolio;

  private StockModelImpl() {
    this.portfolio = new HashMap<>();
  }

  @Override
  public void createPortfolio() {
  }

  @Override
  public double buy(String code, int shares) {
    return 0.0;
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
