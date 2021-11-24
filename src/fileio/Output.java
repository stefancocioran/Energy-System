package fileio;

import java.util.List;

public final class Output {

  private List<ConsumerOutputData> consumers;
  private List<DistributorOutputData> distributors;
  private List<ProducerOutputData> energyProducers;

  public List<ConsumerOutputData> getConsumers() {
    return consumers;
  }

  public void setConsumers(final List<ConsumerOutputData> consumers) {
    this.consumers = consumers;
  }

  public List<DistributorOutputData> getDistributors() {
    return distributors;
  }

  public void setDistributors(final List<DistributorOutputData> distributors) {
    this.distributors = distributors;
  }

  public List<ProducerOutputData> getEnergyProducers() {
    return energyProducers;
  }

  public void setEnergyProducers(List<ProducerOutputData> energyProducers) {
    this.energyProducers = energyProducers;
  }
}
