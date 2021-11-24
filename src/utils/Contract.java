package utils;

public final class Contract {

  private int consumerId;
  private long price;
  private int remainedContractMonths;


  public Contract(final int consumerId, final long price, final int remainedContractMonths) {
    this.consumerId = consumerId;
    this.price = price;
    this.remainedContractMonths = remainedContractMonths;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(final long price) {
    this.price = price;
  }

  public int getConsumerId() {
    return consumerId;
  }

  public void setConsumerId(final int consumerId) {
    this.consumerId = consumerId;
  }

  public int getRemainedContractMonths() {
    return remainedContractMonths;
  }

  public void setRemainedContractMonths(final int remainedContractMonths) {
    this.remainedContractMonths = remainedContractMonths;
  }
}
