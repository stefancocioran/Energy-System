package database;

import entities.Consumer;
import entities.Distributor;
import entities.EnergyProducer;
import java.util.List;
import strategies.GreenStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;
import utils.Constants;
import utils.Contract;

public class DistributorsDatabase {

  private final List<Distributor> distributors;

  public DistributorsDatabase(final List<Distributor> distributors) {
    this.distributors = distributors;
  }

  /**
   * Alege un producatori pentru distribuitori, in functie de strategia preferata
   */
  public void pickProducer(ProducersDatabase producersDatabase) {
    for (Distributor distributor : distributors) {
      switch (distributor.getProducerStrategy()) {
        case GREEN -> {
          GreenStrategy greenStrategy = new GreenStrategy(distributor, producersDatabase);
          greenStrategy.distribute();
        }
        case PRICE -> {
          PriceStrategy priceStrategy = new PriceStrategy(distributor, producersDatabase);
          priceStrategy.distribute();
        }
        case QUANTITY -> {
          QuantityStrategy quantityStrategy = new QuantityStrategy(distributor, producersDatabase);
          quantityStrategy.distribute();
        }
        default -> throw new IllegalStateException(
            "Unexpected value: " + distributor.getProducerStrategy());
      }
    }
  }

  /**
   * Metoda calculeaza costul de productie pentru fiecare distribuitor
   */
  public void computeProductionCost(ProducersDatabase producersDatabase) {
    float cost = 0;
    for (Distributor distributor : distributors) {
      for (EnergyProducer producer : producersDatabase.getProducers()) {
        if (producer.getObservers().contains(distributor)) {
          cost += producer.getPriceKW() * producer.getEnergyPerDistributor();
        }
      }
      distributor.setProductionCost(Math.round(Math.floor(cost / Constants.TEN)));
      cost = 0;
    }
  }

  /**
   * Actualizeaza preturile contractelor distribuitorilor
   */
  public void computeContractCost() {
    for (Distributor distributor : distributors) {
      // Daca nu a intrat in faliment, se recalculeaza costurile
      if (!distributor.getIsBankrupt()) {
        long productionCost = distributor.getProductionCost();
        long infrastructureCost = distributor.getInfrastructureCost();
        int consumersNumber = distributor.getContracts().size();
        distributor.setProfit(Math.round(Math.floor(Constants.PROFIT_PERCENTAGE * productionCost)));
        if (consumersNumber == 0) {
          distributor
              .setContractCost(infrastructureCost + productionCost + distributor.getProfit());
        } else {
          distributor
              .setContractCost(Math.round(Math.floor((double) infrastructureCost / consumersNumber)
                  + productionCost + distributor.getProfit()));
        }
      }
    }
  }


  /**
   * Se actualizeaza costurile/taxele lunare ale distribuitorilor
   */
  public void updateTaxes() {
    for (Distributor distributor : distributors) {
      if (!distributor.getIsBankrupt()) {
        long productionCost = distributor.getProductionCost();
        long infrastructureCost = distributor.getInfrastructureCost();
        int consumersNumber = distributor.getContracts().size();
        distributor.setCosts(infrastructureCost + productionCost * consumersNumber);
      }
    }
  }

  /**
   * Distribuitorii platesc costurile lunare
   */
  public void payTaxes(ProducersDatabase producersDatabase) {
    updateTaxes();
    for (Distributor distributor : distributors) {
      long budget = distributor.getBudget();
      long costs = distributor.getCosts();
      if (budget - costs < 0) {
        if (!distributor.getIsBankrupt()) {
          distributor.setIsBankrupt(true);
          distributor.update(); // goleste lista de observables
          // daca e bankrupt, este scos din lista de observatori
          producersDatabase.removeObserver(distributor);
          distributor.setBudget(budget - costs);
        }
      } else {
        distributor.setBudget(budget - costs);
      }
    }
    updateTaxes();
  }

  /**
   * Se semneaza un contract intre un consumator si distribuitor
   */
  public void signContract(final Consumer consumer, final Distributor distributor) {
    distributor.getContracts().add(
        new Contract(consumer.getId(), distributor.getContractCost(),
            distributor.getContractLength()));
  }


  /**
   * Returneaza contractul unui consumator
   *
   * @param consumer - consumatorul al carui contract este returnat
   */
  public Contract getContractOfConsumer(final Consumer consumer) {
    for (Distributor distributor : distributors) {
      for (int j = 0; j < distributor.getContracts().size(); j++) {
        if (distributor.getContracts().get(j).getConsumerId() == consumer.getId()) {
          return distributor.getContracts().get(j);
        }
      }
    }
    return null;
  }

  /**
   * Se taie/termina un contract intre consumator si distribuitor
   */
  public void cutContract(final Consumer consumer, final Distributor distributor) {
    for (int i = 0; i < distributor.getContracts().size(); i++) {
      if (distributor.getContracts().get(i).getConsumerId() == consumer.getId()) {
        Contract contract = distributor.getContracts().get(i);
        distributor.getContracts().remove(contract);
      }
    }
  }

  /**
   * Sunt taiate toate contractele care au expirat sau care s-au terminat
   *
   * @param consumersDatabase - baza de date a consumatorilor
   */
  public void cutExpiredContracts(final ConsumersDatabase consumersDatabase) {
    for (Distributor distributor : distributors) {
      for (int j = 0; j < distributor.getContracts().size(); j++) {
        if (distributor.getContracts().get(j).getRemainedContractMonths() == 0) {
          int id = distributor.getContracts().get(j).getConsumerId();
          consumersDatabase.getConsumerById(id).setRemainedContractMonths(0);
        }
      }
      distributor.getContracts().removeIf(x -> x.getRemainedContractMonths() == 0);
    }
  }


  /**
   * Elimina toti jucatorii care sunt in faliment
   *
   * @param consumersDatabase - baza de date a consumatorilor
   */
  public void removeBankruptPlayers(final ConsumersDatabase consumersDatabase) {
    for (Distributor distributor : distributors) {
      for (int i = 0; i < distributor.getContracts().size(); i++) {
        if (consumersDatabase.getConsumerById(distributor.getContracts().get(i).getConsumerId())
            .getIsBankrupt()) {
          cutContract(consumersDatabase.getConsumerById(distributor.getContracts().get(i).
              getConsumerId()), distributor);
        }
      }
      if (distributor.getIsBankrupt()) {
        // trec prin toate contractele si resetez lunile de plata, la sfarsit tai contractele
        for (int j = 0; j < distributor.getContracts().size(); j++) {
          consumersDatabase.getConsumerById(distributor.getContracts().get(j).getConsumerId())
              .setRemainedContractMonths(0);
        }
        distributor.getContracts().clear();
      }
    }
  }

  /**
   * Returneaza distribuitorul unui consumator
   *
   * @param consumer - consumatorul al carui distribuitor este returnat
   */
  public Distributor getConsumerProvider(final Consumer consumer) {
    for (Distributor distributor : distributors) {
      if (consumer.getProviderId() == distributor.getId()) {
        return distributor;
      }
    }
    return null;
  }

  /**
   * Cauta si returneaza un distribuitor dupa un Id primit
   *
   * @param id - id-ul distribuitorului cautat
   */
  public Distributor getDistributorByID(final int id) {
    for (Distributor distributor : distributors) {
      if (distributor.getId() == id) {
        return distributor;
      }
    }
    return null;
  }

  /**
   * Returneaza factura curenta a unui consumator
   */
  public int getConsumerBill(final Consumer consumer) {
    for (Distributor distributor : distributors) {
      if (consumer.getProviderId() == distributor.getId()) {
        for (int i = 0; i < distributor.getContracts().size(); i++) {
          if (distributor.getContracts().get(i).getConsumerId() == consumer.getId()) {
            return (int) distributor.getContracts().get(i).getPrice();
          }
        }
      }
    }
    return 0;
  }


  /**
   * Verifica daca toti distribuitorii au intrat in faliment
   */
  public boolean distributorsAreAllBankrupt() {
    for (Distributor distributor : distributors) {
      if (!distributor.getIsBankrupt()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returneaza lista de distribuitori din baza de date
   */
  public List<Distributor> getDistributors() {
    return distributors;
  }
}
