package fileio;

public final class ProducerChangesInputData {

  private final int id;
  private final int energyPerDistributor;

  public ProducerChangesInputData(int id, int energyPerDistributor) {
    this.id = id;
    this.energyPerDistributor = energyPerDistributor;
  }

  public int getId() {
    return id;
  }

  public int getEnergyPerDistributor() {
    return energyPerDistributor;
  }
}
