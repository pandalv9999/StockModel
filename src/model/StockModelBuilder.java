package model;

/**
 * This interface represents the builder of a stock model.
 */
public interface StockModelBuilder {
  StockModelBuilder commissionFee(double fee);


  StockModel build();
}
