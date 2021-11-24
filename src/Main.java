import com.fasterxml.jackson.databind.ObjectMapper;
import database.Game;
import entities.Consumer;
import entities.Distributor;
import entities.EnergyProducer;
import fileio.ConsumerOutputData;
import fileio.DistributorOutputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Output;
import fileio.ProducerOutputData;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import utils.Constants;
import utils.Contract;

/**
 * Entry point to the simulation
 */
public final class Main {

  private Main() {
  }

  /**
   * Main function which reads the input file and starts simulation
   *
   * @param args input and output files
   * @throws Exception might error when reading/writing/opening files, parsing JSON
   */
  public static void main(final String[] args) throws Exception {
    InputLoader inputLoader = new InputLoader(args[0]);
    Input input = inputLoader.readData();

    Game game = new Game(input);
    game.playGame();

    Output output = new Output();
    getOutputData(output, game.getConsumersOut(),
        game.getDistributorsOut(), game.getProducersOut());

    ObjectMapper objectMapperbj = new ObjectMapper();
    try {
      objectMapperbj.writeValue(new File(args[1]), output);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Metoda compune output-ul in formatul corsepunzator
   *
   * @param output       - output-ul rezultat
   * @param consumers    - lista de consumatori din baza de date
   * @param distributors - lista de distribuitori din baza de date
   * @param producers    - lista de producatori din baza de date
   */
  public static void getOutputData(final Output output, final List<Consumer> consumers,
      final List<Distributor> distributors,
      final List<EnergyProducer> producers) {

    List<ConsumerOutputData> consumerOutputData = new ArrayList<>();
    List<DistributorOutputData> distributorOutputData = new ArrayList<>();
    List<ProducerOutputData> producerOutputData = new ArrayList<>();

    for (Consumer consumer : consumers) {
      ConsumerOutputData consumerData = new ConsumerOutputData();
      consumerData.setId(consumer.getId());
      consumerData.setIsBankrupt(consumer.getIsBankrupt());
      consumerData.setBudget(consumer.getBudget());
      consumerOutputData.add(consumerData);
    }
    for (Distributor distributor : distributors) {
      DistributorOutputData distributorData = new DistributorOutputData();
      distributorData.setId(distributor.getId());
      distributorData.setEnergyNeededKW(distributor.getEnergyNeededKW());
      distributorData.setContractCost(distributor.getContractCost());
      distributorData.setBudget(distributor.getBudget());
      distributorData.setProducerStrategy(distributor.getProducerStrategy());
      distributorData.setIsBankrupt(distributor.getIsBankrupt());
      List<Contract> contracts = new ArrayList<>();
      for (Contract contract : distributor.getContracts()) {
        Contract contractData = new Contract(contract.getConsumerId(), contract.getPrice(),
            contract.getRemainedContractMonths());
        contracts.add(contractData);
      }
      distributorData.setContracts(contracts);
      distributorOutputData.add(distributorData);
    }

    // pentru producatori
    for (EnergyProducer producer : producers) {
      ProducerOutputData producerData = new ProducerOutputData();
      producerData.setId(producer.getId());
      producerData.setMaxDistributors(producer.getMaxDistributors());
      producerData.setPriceKW((double) Math.round(producer.getPriceKW() * Constants.ONE_HUNDRED)
          / Constants.ONE_HUNDRED);
      producerData.setEnergyType(producer.getEnergyType());
      producerData.setEnergyPerDistributor(producer.getEnergyPerDistributor());
      producerData.setMonthlyStats(producer.getMonthlyStats());
      producerOutputData.add(producerData);
    }
    output.setConsumers(consumerOutputData);
    output.setDistributors(distributorOutputData);
    output.setEnergyProducers(producerOutputData);
  }
}
