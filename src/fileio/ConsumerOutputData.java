package fileio;

public final class ConsumerOutputData {

  private int id;
  private boolean isBankrupt;
  private long budget;

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
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

  public void setBudget(final long buget) {
    this.budget = buget;
  }
}
