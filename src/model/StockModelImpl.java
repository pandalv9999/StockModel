package model;

import java.util.Date;
import java.util.List;

// still confused
public class StockModelImpl implements StockModel {
  private StockModelImpl() {

  }

  @Override
  public List<SingleTransaction> createPortfolio() {
    return null;
  }

  @Override
  public void buy(List<SingleTransaction> transactions) {

  }

  @Override
  public int checkValue(List<SingleTransaction> transactions, Date date) {
    return 0;
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
