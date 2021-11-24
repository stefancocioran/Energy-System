package database;

import entities.Consumer;
import entities.Distributor;
import java.util.List;
import utils.Constants;

public class ConsumersDatabase {

  private final List<Consumer> consumers;

  public ConsumersDatabase(final List<Consumer> consumers) {
    this.consumers = consumers;
  }

  /**
   * Metoda face ca toti consumatorii sa-si aleage un nou distribuitor daca contractul lor s-a
   * incheiat (nu au mai ramas luni de plata)
   *
   * @param distributorsDatabase - baza de date a distribuitorilor
   */

  public void updateDistributor(final DistributorsDatabase distributorsDatabase) {
    // Trec prin toti distributorii, verific care are pretul cel mai mic
    // Se fac contracte cu toti consumatorii
    for (Consumer consumer : consumers) {
      if (!consumer.getIsBankrupt()) {
        // Daca nu are distribuitor / s-a incheiat contractul
        if (consumer.getRemainedContractMonths() == 0) {
          Distributor bestPriceDistributor = null;
          long min = Integer.MAX_VALUE;
          for (int i = 0; i < distributorsDatabase.getDistributors().size(); i++) {
            if (distributorsDatabase.getDistributors().get(i).getContractCost() < min
                && !distributorsDatabase.getDistributors().get(i).getIsBankrupt()) {
              min = distributorsDatabase.getDistributors().get(i).getContractCost();
              bestPriceDistributor = distributorsDatabase.getDistributors().get(i);
            }
          }
          // Fac contract
          if (bestPriceDistributor != null) {
            distributorsDatabase.signContract(consumer, bestPriceDistributor);
            consumer.setProviderId(bestPriceDistributor.getId());
            consumer.setRemainedContractMonths(bestPriceDistributor.getContractLength());
            distributorsDatabase.getContractOfConsumer(consumer)
                .setRemainedContractMonths(bestPriceDistributor.getContractLength());
          }
        }
      }
    }
  }

  /**
   * Consumatorii primesc salariu
   */

  public void receiveSalary() {
    for (Consumer consumer : consumers) {
      if (!consumer.getIsBankrupt()) {
        long previousBudget = consumer.getBudget();
        int monthlyIncome = consumer.getMonthlyIncome();
        consumer.setBudget(previousBudget + monthlyIncome);
      }
    }
  }

  /**
   * Se platesc facturile lunare ale consumatorilor
   *
   * @param distributorsDatabase - baza de date a distribuitorilor
   */
  public void payMonthlyBill(final DistributorsDatabase distributorsDatabase) {
    for (Consumer consumer : consumers) {
      if (!consumer.getIsBankrupt()) {
        long budget = consumer.getBudget();
        long actualBill = distributorsDatabase.getConsumerBill(consumer);
        long debt = consumer.getDebt();

        if ((budget - actualBill - debt) < 0) {
          if (consumer.getPenaltyGiven()) {
            consumer.setIsBankrupt(true);
          } else {
            consumer.setPenaltyGiven(true);
            consumer.setDebt(Math.round(Math.floor(Constants.DEBT_PERCENTAGE * actualBill)));
            int distributorId = distributorsDatabase.getConsumerProvider(consumer).getId();
            consumer.setPreviousDistributorId(distributorId);
            int remainedContractMonths = consumer.getRemainedContractMonths();
            consumer.setRemainedContractMonths(remainedContractMonths - 1);
            distributorsDatabase.getContractOfConsumer(consumer)
                .setRemainedContractMonths(remainedContractMonths - 1);
          }
        } else {
          // Se efectueaza plata facturilor

          // Platesc factura noua
          Distributor actualDistributor = distributorsDatabase.getConsumerProvider(consumer);
          if (!actualDistributor.getIsBankrupt()) {
            long actualDistributorBudget = actualDistributor.getBudget();
            actualDistributor.setBudget(actualDistributorBudget + actualBill);
            consumer.setBudget((int) (budget - actualBill));
          }

          budget = consumer.getBudget();

          // Platesc factura veche la distribuitorul anterior
          Distributor previousDistributor = distributorsDatabase
              .getDistributorByID(consumer.getPreviousDistributorId());
          if (!previousDistributor.getIsBankrupt()) {
            long previousProviderBudget = previousDistributor.getBudget();
            previousDistributor.setBudget(previousProviderBudget + debt);
            consumer.setBudget(budget - debt);
          }

          // Si-a achitat datoria
          consumer.setDebt(0);
          consumer.setPenaltyGiven(false);
          if (actualDistributor.getIsBankrupt()) {
            consumer.setRemainedContractMonths(0);
            distributorsDatabase.getContractOfConsumer(consumer)
                .setRemainedContractMonths(0);
          } else {
            int remainedContractMonths = consumer.getRemainedContractMonths();
            consumer.setRemainedContractMonths(remainedContractMonths - 1);
            distributorsDatabase.getContractOfConsumer(consumer)
                .setRemainedContractMonths(remainedContractMonths - 1);
          }
        }
      }
    }
  }

  /**
   * Cauta si returneaza un consumator dupa un Id dat
   *
   * @param id - id-ul consumatorului cautat
   */
  public Consumer getConsumerById(final int id) {
    for (Consumer consumer : consumers) {
      if (consumer.getId() == id) {
        return consumer;
      }
    }
    return null;
  }

  /**
   * Returneaza lista consumatorilor din baza de date
   */
  public List<Consumer> getConsumers() {
    return consumers;
  }
}
