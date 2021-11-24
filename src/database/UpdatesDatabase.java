package database;

import entities.Consumer;
import entities.EnergyProducer;
import entities.Player;
import entities.PlayerFactory;
import fileio.MonthlyUpdateInputData;
import java.util.List;
import utils.Constants;

public class UpdatesDatabase {

  private final List<MonthlyUpdateInputData> monthlyUpdates;

  public UpdatesDatabase(final List<MonthlyUpdateInputData> monthlyUpdates) {
    this.monthlyUpdates = monthlyUpdates;
  }

  /**
   * Metoda aplica actualizarile lunare pentru consumatori si distribuitori
   *
   * @param month                - luna pentru care se aplica actualizari
   * @param consumersDatabase    - baza de date pentru consumatori
   * @param distributorsDatabase - baza de date pentru distribuitori
   */

  public void applyMonthlyUpdateConsumersAndDistributors(final int month,
      final DistributorsDatabase distributorsDatabase, final ConsumersDatabase consumersDatabase) {
    PlayerFactory entityFactory = PlayerFactory.getInstance();
    for (int i = 0; i < monthlyUpdates.size(); i++) {
      if (month == i) {
        for (int j = 0; j < monthlyUpdates.get(i).getNewConsumers().size(); j++) {
          int id = monthlyUpdates.get(i).getNewConsumers().get(j).getId();
          int budget = monthlyUpdates.get(i).getNewConsumers().get(j).getInitialBudget();
          int monthlyIncome = monthlyUpdates.get(i).getNewConsumers().get(j).getMonthlyIncome();
          Player entity = entityFactory.createEntity(Constants.CONSUMER, 0, null,
              null, id, budget, monthlyIncome);
          consumersDatabase.getConsumers().add((Consumer) entity);
        }

        for (int j = 0; j < monthlyUpdates.get(i).getDistributorChanges().size(); j++) {
          int id = monthlyUpdates.get(i).getDistributorChanges().get(j).getId();
          long newInfrastuctureCost = monthlyUpdates.get(i).getDistributorChanges().get(j)
              .getInfrastructureCost();
          for (int k = 0; k < distributorsDatabase.getDistributors().size(); k++) {
            if (distributorsDatabase.getDistributors().get(k).getId() == id) {
              distributorsDatabase.getDistributors().get(k)
                  .setInfrastructureCost(newInfrastuctureCost);
            }
          }
        }
      }
    }
  }

  /**
   * Metoda aplica actualizarile lunare pentru producatori
   *
   * @param month             - luna pentru care se aplica actualizari
   * @param producersDatabase - baza de date pentru producatori
   */
  public void applyMonthlyUpdateProducers(final int month,
      final ProducersDatabase producersDatabase) {
    for (int i = 0; i < monthlyUpdates.size(); i++) {
      if (month == i) {
        for (int j = 0; j < monthlyUpdates.get(i).getProducerChanges().size(); j++) {
          int id = monthlyUpdates.get(i).getProducerChanges().get(j).getId();
          int energyPerDistributor = monthlyUpdates.get(i).getProducerChanges().get(j)
              .getEnergyPerDistributor();
          for (int k = 0; k < producersDatabase.getProducers().size(); k++) {
            if (producersDatabase.getProducers().get(k).getId() == id) {
              EnergyProducer producer = producersDatabase.getProducers().get(k);
              producer.setEnergyPerDistributor(energyPerDistributor);
              producersDatabase.notifyAllObservers(producer);
            }
          }
        }
      }
    }
  }
}
