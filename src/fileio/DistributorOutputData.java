package fileio;

import java.util.List;
import strategies.EnergyChoiceStrategyType;
import utils.Contract;

public final class DistributorOutputData {

  private int id;
  private int energyNeededKW;
  private long contractCost;
  private long budget;
  private EnergyChoiceStrategyType producerStrategy;
  private boolean isBankrupt;
  private List<Contract> contracts;

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public int getEnergyNeededKW() {
    return energyNeededKW;
  }

  public void setEnergyNeededKW(int energyNeededKW) {
    this.energyNeededKW = energyNeededKW;
  }

  public long getContractCost() {
    return contractCost;
  }

  public void setContractCost(long contractCost) {
    this.contractCost = contractCost;
  }

  public EnergyChoiceStrategyType getProducerStrategy() {
    return producerStrategy;
  }

  public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
    this.producerStrategy = producerStrategy;
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

  public List<Contract> getContracts() {
    return contracts;
  }

  public void setContracts(final List<Contract> contracts) {
    this.contracts = contracts;
  }
}
