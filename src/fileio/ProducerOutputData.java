package fileio;

import entities.EnergyType;
import java.util.List;
import utils.MonthlyStat;

public final class ProducerOutputData {

  private int id;
  private int maxDistributors;
  private double priceKW;
  private EnergyType energyType;
  private int energyPerDistributor;
  private List<MonthlyStat> monthlyStats;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getMaxDistributors() {
    return maxDistributors;
  }

  public void setMaxDistributors(int maxDistributors) {
    this.maxDistributors = maxDistributors;
  }

  public int getEnergyPerDistributor() {
    return energyPerDistributor;
  }

  public void setEnergyPerDistributor(int energyPerDistributor) {
    this.energyPerDistributor = energyPerDistributor;
  }

  public EnergyType getEnergyType() {
    return energyType;
  }

  public void setEnergyType(EnergyType energyType) {
    this.energyType = energyType;
  }

  public double getPriceKW() {
    return priceKW;
  }

  public void setPriceKW(double priceKW) {
    this.priceKW = priceKW;
  }

  public List<MonthlyStat> getMonthlyStats() {
    return monthlyStats;
  }

  public void setMonthlyStats(List<MonthlyStat> monthlyStats) {
    this.monthlyStats = monthlyStats;
  }
}
