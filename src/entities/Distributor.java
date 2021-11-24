package entities;

import java.util.ArrayList;
import java.util.List;
import strategies.EnergyChoiceStrategyType;
import utils.Contract;
import utils.Observer;

public final class Distributor implements Player, Observer {

  private final int id;
  private final int contractLength;
  private long budget;
  private long infrastructureCost;
  private boolean isBankrupt;
  private List<Contract> contracts;
  private long costs;
  private long profit;
  private int energyNeededKW;
  private int energyProvided;
  private long contractCost;
  private EnergyChoiceStrategyType producerStrategy;
  private long productionCost;
  private List<Integer> producersId;

  public Distributor(int id, int contractLength, long budget, int infrastructureCost,
      int energyNeededKW, EnergyChoiceStrategyType producerStrategy) {
    this.id = id;
    this.contractLength = contractLength;
    this.budget = budget;
    this.infrastructureCost = infrastructureCost;
    this.isBankrupt = false;
    this.contracts = new ArrayList<>();
    this.producersId = new ArrayList<>();
    this.energyNeededKW = energyNeededKW;
    this.energyProvided = 0;
    this.producerStrategy = producerStrategy;
  }

  public int getId() {
    return id;
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

  public long getInfrastructureCost() {
    return infrastructureCost;
  }

  public void setInfrastructureCost(final long infrastructureCost) {
    this.infrastructureCost = infrastructureCost;
  }

  public long getContractCost() {
    return contractCost;
  }

  public void setContractCost(long contractCost) {
    this.contractCost = contractCost;
  }

  public int getContractLength() {
    return contractLength;
  }

  public long getCosts() {
    return costs;
  }

  public void setCosts(final long costs) {
    this.costs = costs;
  }

  public long getProfit() {
    return profit;
  }

  public void setProfit(final long profit) {
    this.profit = profit;
  }

  @Override
  public boolean getIsBankrupt() {
    return isBankrupt;
  }

  public void setIsBankrupt(final boolean bankrupt) {
    isBankrupt = bankrupt;
  }

  public EnergyChoiceStrategyType getProducerStrategy() {
    return producerStrategy;
  }

  public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
    this.producerStrategy = producerStrategy;
  }

  public int getEnergyNeededKW() {
    return energyNeededKW;
  }

  public void setEnergyNeededKW(int energyNeededKW) {
    this.energyNeededKW = energyNeededKW;
  }

  public long getProductionCost() {
    return productionCost;
  }

  public void setProductionCost(long productionCost) {
    this.productionCost = productionCost;
  }

  public List<Integer> getProducersId() {
    return producersId;
  }

  public void setProducersId(List<Integer> producersId) {
    this.producersId = producersId;
  }

  public int getEnergyProvided() {
    return energyProvided;
  }

  public void setEnergyProvided(int energyProvided) {
    this.energyProvided = energyProvided;
  }

  @Override
  public void update() {
    energyProvided = 0;
    producersId.clear();
  }
}
