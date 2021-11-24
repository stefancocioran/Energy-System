package database;

import entities.Consumer;
import entities.Distributor;
import entities.EnergyProducer;
import entities.EnergyType;
import entities.Player;
import entities.PlayerFactory;
import fileio.Input;
import java.util.ArrayList;
import java.util.List;
import strategies.EnergyChoiceStrategyType;
import utils.Constants;


public final class Game {

  private final Input input;
  private List<Consumer> consumersOut;
  private List<Distributor> distributorsOut;
  private List<EnergyProducer> producersOut;

  public Game(final Input input) {
    this.input = input;
  }

  /**
   * Se desfasoara jocul
   */
  public void playGame() {
    int numberOfTurns = input.getNumberOfTurns();

    PlayerFactory entityFactory = PlayerFactory.getInstance();
    // Adaug consumatorii
    List<Consumer> consumers = new ArrayList<>();
    for (int i = 0; i < input.getConsumers().size(); i++) {
      int id = input.getConsumers().get(i).getId();
      int budget = input.getConsumers().get(i).getInitialBudget();
      int monthlyIncome = input.getConsumers().get(i).getMonthlyIncome();
      Player entity = entityFactory.createEntity(Constants.CONSUMER, 0, null,
          null, id, budget, monthlyIncome);
      consumers.add((Consumer) entity);
    }

    // Adaug distribuitorii
    List<Distributor> distributors = new ArrayList<>();
    for (int i = 0; i < input.getDistributors().size(); i++) {
      int id = input.getDistributors().get(i).getId();
      int contractLength = input.getDistributors().get(i).getContractLength();
      int budget = input.getDistributors().get(i).getInitialBudget();
      int infrastructureCost = input.getDistributors().get(i).getInitialInfrastructureCost();
      int energyNeededKW = input.getDistributors().get(i).getEnergyNeededKW();
      EnergyChoiceStrategyType producerStrategy = input.getDistributors().get(i)
          .getProducerStrategy();

      Player entity = entityFactory
          .createEntity(Constants.DISTRIBUTOR, 0, producerStrategy, null, id,
              contractLength, budget, infrastructureCost, energyNeededKW);
      distributors.add((Distributor) entity);
    }

    // Adaug producatorii
    List<EnergyProducer> energyProducers = new ArrayList<>();
    for (int i = 0; i < input.getProducers().size(); i++) {
      int id = input.getProducers().get(i).getId();
      EnergyType energyType = input.getProducers().get(i).getEnergyType();
      int maxDistributors = input.getProducers().get(i).getMaxDistributors();
      float priceKW = input.getProducers().get(i).getPriceKW();
      int energyPerDistributor = input.getProducers().get(i).getEnergyPerDistributor();
      Player entity = entityFactory
          .createEntity(Constants.PRODUCER, priceKW, null, energyType, id,
              maxDistributors, energyPerDistributor);
      energyProducers.add((EnergyProducer) entity);
    }

    UpdatesDatabase updatesDatabase = new UpdatesDatabase(input.getMonthlyUpdates());

    ConsumersDatabase consumersDatabase = new ConsumersDatabase(consumers);
    DistributorsDatabase distributorsDatabase = new DistributorsDatabase(distributors);
    ProducersDatabase producersDatabase = new ProducersDatabase(energyProducers);

    // Distribuitorii isi aleg producatori
    distributorsDatabase.pickProducer(producersDatabase);

    // Calculare cost de productie
    distributorsDatabase.computeProductionCost(producersDatabase);

    // Recalculare pret contracte
    distributorsDatabase.computeContractCost();

    // Se primesc salarii
    consumersDatabase.receiveSalary();

    // Se aleg contracte
    consumersDatabase.updateDistributor(distributorsDatabase);

    // Se platesc ratele
    consumersDatabase.payMonthlyBill(distributorsDatabase);

    // Distribuitorii platesc taxe
    distributorsDatabase.payTaxes(producersDatabase);

    // De aici incepe prelucrarea datelor in functie de update-uri
    // Incepe jocul propriu-zis

    for (int i = 0; i < numberOfTurns; i++) {
      // In cazul în care toți distribuitorii dau faliment, jocul se va încheia
      // Trebuie sa verific mereu daca exista distribuitori care n-au dat faliment

      if (distributorsDatabase.distributorsAreAllBankrupt()) {
        break;
      }

      updatesDatabase
          .applyMonthlyUpdateConsumersAndDistributors(i, distributorsDatabase, consumersDatabase);

      distributorsDatabase.computeProductionCost(producersDatabase);

      distributorsDatabase.computeContractCost();

      distributorsDatabase.cutExpiredContracts(consumersDatabase);

      consumersDatabase.receiveSalary();

      consumersDatabase.updateDistributor(distributorsDatabase);

      consumersDatabase.payMonthlyBill(distributorsDatabase);

      distributorsDatabase.payTaxes(producersDatabase);

      updatesDatabase.applyMonthlyUpdateProducers(i, producersDatabase);

      distributorsDatabase.pickProducer(producersDatabase);

      producersDatabase.updateMonthlyStats(i);

      distributorsDatabase.removeBankruptPlayers(consumersDatabase);

      distributorsDatabase.computeProductionCost(producersDatabase);

    }

    setConsumersOut(consumersDatabase.getConsumers());
    setDistributorsOut(distributorsDatabase.getDistributors());
    setProducersOut(producersDatabase.getProducers());
  }

  public List<Consumer> getConsumersOut() {
    return consumersOut;
  }

  public void setConsumersOut(final List<Consumer> consumersOut) {
    this.consumersOut = consumersOut;
  }

  public List<Distributor> getDistributorsOut() {
    return distributorsOut;
  }

  public void setDistributorsOut(final List<Distributor> distributorsOut) {
    this.distributorsOut = distributorsOut;
  }

  public List<EnergyProducer> getProducersOut() {
    return producersOut;
  }

  public void setProducersOut(List<EnergyProducer> producersOut) {
    this.producersOut = producersOut;
  }
}
