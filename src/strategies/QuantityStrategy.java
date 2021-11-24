package strategies;

import database.ProducersDatabase;
import entities.Distributor;
import entities.EnergyProducer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuantityStrategy {

  private final Distributor distributor;
  private final ProducersDatabase producersDatabase;

  public QuantityStrategy(Distributor distributorsDatabase,
      ProducersDatabase producersDatabase) {
    this.distributor = distributorsDatabase;
    this.producersDatabase = producersDatabase;
  }

  /**
   * Metoda ii atribuie unui distribuitor unul sau mai multi producatori in functie de necesitati
   */
  public void distribute() {
    if (distributor.getProducersId().isEmpty() && !distributor.getIsBankrupt()) {
      List<EnergyProducer> producers = new ArrayList<>(producersDatabase.getProducers());
      producers.sort(
          (o1, o2) -> Comparator.comparing(EnergyProducer::getEnergyPerDistributor).reversed()
              .compare(o1, o2));
      for (EnergyProducer producer : producers) {
        int distributorsNumber = producer.getObservers().size();
        if (distributorsNumber >= producer.getMaxDistributors()) {
          continue;
        }
        if (!producer.getObservers().contains(distributor)
            && distributor.getEnergyProvided() < distributor
            .getEnergyNeededKW()) {
          producer.addObserver(distributor);
          producer.getCurrentDistributors().add(distributor);
          distributor.getProducersId().add(producer.getId());
          distributor.setEnergyProvided(
              distributor.getEnergyProvided() + producer.getEnergyPerDistributor());
        }
      }
    }
  }
}
