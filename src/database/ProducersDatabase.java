package database;

import entities.Distributor;
import entities.EnergyProducer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import utils.MonthlyStat;
import utils.Observer;

public final class ProducersDatabase {

  private final List<EnergyProducer> producers;

  public ProducersDatabase(final List<EnergyProducer> producers) {
    this.producers = producers;
  }

  public List<EnergyProducer> getProducers() {
    return producers;
  }

  /**
   * Metoda retine cati distribuitori a avut fiecare producator la sfarsit de luna
   */
  public void updateMonthlyStats(int month) {
    for (EnergyProducer producer : producers) {
      List<Observer> distributors = new ArrayList<>(producer.getObservers());
      distributors.sort((o1, o2) -> Comparator.comparing(Observer::getId).compare(o1, o2));
      List<Integer> distributorsId = new ArrayList<>();
      for (Observer observer : distributors) {
        distributorsId.add(observer.getId());
      }
      List<MonthlyStat> monthlyStats = producer.getMonthlyStats();
      MonthlyStat newMonthlyStat = new MonthlyStat(month + 1, distributorsId);
      monthlyStats.add(newMonthlyStat);
      producer.setMonthlyStats(monthlyStats);
    }
  }

  /**
   * Atunci cand un producator primeste update, atunci toti observatorii acestuia sunt notificati
   */
  public void notifyAllObservers(EnergyProducer producer) {
    for (Distributor distributor : producer.getCurrentDistributors()) {
      for (EnergyProducer energyProducer : producers) {
        if (energyProducer.getObservers().contains(distributor)) {
          energyProducer.getObservers().clear();
        }
      }
      distributor.update();
    }
    producer.getCurrentDistributors().clear();
  }

  /**
   * Metoda elimina un observer(distribuitor) pentru ai toti ai sai observables(producatori)
   */
  public void removeObserver(Distributor distributor) {
    for (EnergyProducer producer : producers) {
      if (producer.getObservers().contains(distributor)) {
        producer.removeObserver(distributor);
        producer.getCurrentDistributors().remove(distributor);
      }
    }
  }
}
