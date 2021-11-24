package entities;

import java.util.ArrayList;
import java.util.List;
import utils.MonthlyStat;
import utils.Observable;
import utils.Observer;

public final class EnergyProducer extends Observable implements Player {

  private int id;
  private EnergyType energyType;
  private int maxDistributors;
  private List<Distributor> currentDistributors;
  private float priceKW;
  private int energyPerDistributor;
  private List<MonthlyStat> monthlyStats;
  private boolean renewable;

  public EnergyProducer(int id, EnergyType energyType, int maxDistributors, float priceKW,
      int energyPerDistributor) {
    this.id = id;
    this.energyType = energyType;
    this.maxDistributors = maxDistributors;
    this.priceKW = priceKW;
    this.energyPerDistributor = energyPerDistributor;
    this.currentDistributors = new ArrayList<>();
    this.monthlyStats = new ArrayList<>();
    this.renewable = energyType.isRenewable();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public EnergyType getEnergyType() {
    return energyType;
  }

  public void setEnergyType(EnergyType energyType) {
    this.energyType = energyType;
  }

  public int getEnergyPerDistributor() {
    return energyPerDistributor;
  }

  public void setEnergyPerDistributor(int energyPerDistributor) {
    this.energyPerDistributor = energyPerDistributor;
  }

  public int getMaxDistributors() {
    return maxDistributors;
  }

  public void setMaxDistributors(int maxDistributors) {
    this.maxDistributors = maxDistributors;
  }

  public float getPriceKW() {
    return priceKW;
  }

  public void setPriceKW(float priceKW) {
    this.priceKW = priceKW;
  }

  public List<Distributor> getCurrentDistributors() {
    return currentDistributors;
  }

  public void setCurrentDistributors(List<Distributor> currentDistributors) {
    this.currentDistributors = currentDistributors;
  }

  public List<MonthlyStat> getMonthlyStats() {
    return monthlyStats;
  }

  public void setMonthlyStats(List<MonthlyStat> monthlyStats) {
    this.monthlyStats = monthlyStats;
  }

  public boolean getRenewable() {
    return renewable;
  }

  public void setRenewable(boolean renewable) {
    this.renewable = renewable;
  }

  @Override
  public List<Observer> getObservers() {
    return super.getObservers();
  }

}
