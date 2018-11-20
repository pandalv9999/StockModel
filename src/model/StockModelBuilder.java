package model;

/**
 * This interface represents the builder of a stock model.
 */
public interface StockModelBuilder {

  /**
   * This builder method will assign the commission fee in the stock model.
   *
   * @param fee The commission fee in every transaction.
   * @return The builder for the following construction.
   */
  StockModelBuilder commissionFee(double fee);

  /**
   * This method actually do the construction of the stock model.
   *
   * @return The stock model with the parameters above.
   */
  StockModel build();
}
