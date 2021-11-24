package entities;


public final class Consumer implements Player {

  private final int id;
  private final int monthlyIncome;
  private long budget;
  private boolean isBankrupt;
  private int distributorId;
  private int remainedContractMonths;
  private boolean penaltyGiven;
  private long debt;
  private int previousDistributorId;

  public Consumer(final int id, final int budget, final int monthlyIncome) {
    this.id = id;
    this.monthlyIncome = monthlyIncome;
    this.budget = budget;
    this.isBankrupt = false;
    this.penaltyGiven = false;
    this.debt = 0;
  }

  public int getId() {
    return id;
  }

  public int getMonthlyIncome() {
    return monthlyIncome;
  }

  public int getRemainedContractMonths() {
    return remainedContractMonths;
  }

  public void setRemainedContractMonths(final int remainedContractMonths) {
    this.remainedContractMonths = remainedContractMonths;
  }

  public int getProviderId() {
    return distributorId;
  }

  public void setProviderId(final int providerId) {
    this.distributorId = providerId;
  }

  public boolean getPenaltyGiven() {
    return penaltyGiven;
  }

  public void setPenaltyGiven(final boolean penaltyGiven) {
    this.penaltyGiven = penaltyGiven;
  }

  public boolean getIsBankrupt() {
    return isBankrupt;
  }

  public void setIsBankrupt(final boolean bankrupt) {
    isBankrupt = bankrupt;
  }

  public long getBudget() {
    return budget;
  }

  public void setBudget(final long budget) {
    this.budget = budget;
  }

  public long getDebt() {
    return debt;
  }

  public void setDebt(final long debt) {
    this.debt = debt;
  }

  public int getPreviousDistributorId() {
    return previousDistributorId;
  }

  public void setPreviousDistributorId(final int previousDistributorId) {
    this.previousDistributorId = previousDistributorId;
  }
}
