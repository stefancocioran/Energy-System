package fileio;

import java.util.List;

/**
 * The class contains information about input
 * <p>
 * DO NOT MODIFY
 */
public final class Input {

  /**
   * Numarul de ture
   */
  private final int numberOfTurns;
  /**
   * Lista de consumatori
   */
  private final List<ConsumerInputData> consumers;
  /**
   * Lista de distribuitori
   */
  private final List<DistributorInputData> distributors;

  /**
   * Lista de producatori
   */
  private final List<ProducerInputData> producers;

  /**
   * Lista de actualizari
   */
  private final List<MonthlyUpdateInputData> monthlyUpdates;


  public Input() {
    this.numberOfTurns = 0;
    this.consumers = null;
    this.distributors = null;
    this.producers = null;
    this.monthlyUpdates = null;
  }

  public Input(final int numberOfTurns, final List<ConsumerInputData> consumers,
      final List<DistributorInputData> distributors,
      final List<ProducerInputData> producers,
      final List<MonthlyUpdateInputData> monthlyUpdates) {
    this.numberOfTurns = numberOfTurns;
    this.consumers = consumers;
    this.distributors = distributors;
    this.producers = producers;
    this.monthlyUpdates = monthlyUpdates;
  }

  public int getNumberOfTurns() {
    return numberOfTurns;
  }

  public List<ConsumerInputData> getConsumers() {
    return consumers;
  }

  public List<DistributorInputData> getDistributors() {
    return distributors;
  }

  public List<ProducerInputData> getProducers() {
    return producers;
  }

  public List<MonthlyUpdateInputData> getMonthlyUpdates() {
    return monthlyUpdates;
  }
}
