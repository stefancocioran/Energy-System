package fileio;

import entities.EnergyType;

public final class ProducerInputData {

  private final int id;
  private final EnergyType energyType;
  private final int maxDistributors;
  private final float priceKW;
  private final int energyPerDistributor;

  public ProducerInputData(int id, EnergyType energyType, int maxDistributors, float priceKW,
      int energyPerDistributor) {
    this.id = id;
    this.energyType = energyType;
    this.maxDistributors = maxDistributors;
    this.priceKW = priceKW;
    this.energyPerDistributor = energyPerDistributor;
  }

  public int getId() {
    return id;
  }

  public EnergyType getEnergyType() {
    return energyType;
  }

  public int getMaxDistributors() {
    return maxDistributors;
  }

  public float getPriceKW() {
    return priceKW;
  }

  public int getEnergyPerDistributor() {
    return energyPerDistributor;
  }
}
