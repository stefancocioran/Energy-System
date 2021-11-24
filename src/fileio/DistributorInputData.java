package fileio;

import strategies.EnergyChoiceStrategyType;

public final class DistributorInputData {

  private final int id;
  private final int contractLength;
  private final int initialBudget;
  private final int initialInfrastructureCost;
  private final int energyNeededKW;
  private final EnergyChoiceStrategyType producerStrategy;


  public DistributorInputData(final int id, final int contractLength, final int initialBudget,
      final int initialInfrastructureCost, final int energyNeededKW,
      final EnergyChoiceStrategyType producerStrategy) {
    this.id = id;
    this.contractLength = contractLength;
    this.initialBudget = initialBudget;
    this.initialInfrastructureCost = initialInfrastructureCost;
    this.energyNeededKW = energyNeededKW;
    this.producerStrategy = producerStrategy;
  }

  public int getId() {
    return id;
  }

  public int getContractLength() {
    return contractLength;
  }

  public int getInitialBudget() {
    return initialBudget;
  }

  public int getInitialInfrastructureCost() {
    return initialInfrastructureCost;
  }

  public int getEnergyNeededKW() {
    return energyNeededKW;
  }

  public EnergyChoiceStrategyType getProducerStrategy() {
    return producerStrategy;
  }
}
