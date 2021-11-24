package fileio;

public final class DistributorChangesInputData {

  private final int id;
  private final Long infrastructureCost;

  public DistributorChangesInputData(int id, Long infrastructureCost) {
    this.id = id;
    this.infrastructureCost = infrastructureCost;
  }

  public int getId() {
    return id;
  }

  public Long getInfrastructureCost() {
    return infrastructureCost;
  }
}
