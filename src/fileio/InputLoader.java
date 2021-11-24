package fileio;

import entities.EnergyType;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import strategies.EnergyChoiceStrategyType;
import utils.Constants;

public final class InputLoader {

  /**
   * The path to the input file
   */
  private final String inputPath;

  public InputLoader(final String inputPath) {
    this.inputPath = inputPath;
  }


  /**
   * The method reads the database
   *
   * @return an Input object
   */
  public Input readData() {
    JSONParser jsonParser = new JSONParser();
    long numberOfTurns = 0;
    List<ConsumerInputData> consumers = new ArrayList<>();
    List<DistributorInputData> distributors = new ArrayList<>();
    List<ProducerInputData> producers = new ArrayList<>();
    List<MonthlyUpdateInputData> monthlyUpdates = new ArrayList<>();

    try {
      // Parsing the contents of the JSON file
      JSONObject jsonObject = (JSONObject) jsonParser
          .parse(new FileReader(inputPath));
      numberOfTurns = (Long) jsonObject.get(Constants.NUMBER_OF_TURNS);
      JSONObject jsonInitialData = (JSONObject) jsonObject.get(Constants.INITIAL_DATA);
      JSONArray jsonConsumers = (JSONArray) jsonInitialData.get(Constants.CONSUMERS);
      JSONArray jsonDistributors = (JSONArray) jsonInitialData.get(Constants.DISTRIBUTORS);
      JSONArray jsonProducers = (JSONArray) jsonInitialData.get(Constants.PRODUCERS);
      JSONArray jsonMonthlyUpdates = (JSONArray) jsonObject.get(Constants.MONTHLY_UPDATES);

      if (jsonConsumers != null) {
        for (Object jsonConsumer : jsonConsumers) {
          consumers.add(new ConsumerInputData(
              (int) (long) ((JSONObject) jsonConsumer).get(Constants.ID),
              (int) (long) ((JSONObject) jsonConsumer).get(Constants.INITIAL_BUDGET),
              (int) (long) ((JSONObject) jsonConsumer).get(Constants.MONTHLY_INCOME)
          ));
        }
      } else {
        System.out.println("NU EXISTA CONSUMATORI");
      }

      if (jsonDistributors != null) {
        for (Object jsonDistributor : jsonDistributors) {
          distributors.add(new DistributorInputData(
              (int) (long) ((JSONObject) jsonDistributor).get(Constants.ID),
              (int) (long) ((JSONObject) jsonDistributor).get(Constants.CONTRACT_LENGTH),
              (int) (long) ((JSONObject) jsonDistributor).get(Constants.INITIAL_BUDGET),
              (int) (long) ((JSONObject) jsonDistributor)
                  .get(Constants.INITIAL_INFRASTRUCTURE_COST),
              (int) (long) ((JSONObject) jsonDistributor)
                  .get(Constants.ENERGY_NEEDED_KW),
              EnergyChoiceStrategyType.valueOf((String) ((JSONObject) jsonDistributor)
                  .get(Constants.PRODUCER_STRATEGY))
          ));
        }
      } else {
        System.out.println("NU EXISTA DISTRIBUITORI");
      }

      if (jsonProducers != null) {
        for (Object jsonProducer : jsonProducers) {
          producers.add(new ProducerInputData(
              (int) (long) ((JSONObject) jsonProducer).get(Constants.ID),
              EnergyType.valueOf((String) ((JSONObject) jsonProducer).get(Constants.ENERGY_TYPE)),
              (int) (long) ((JSONObject) jsonProducer).get(Constants.MAX_DISTRIBUTORS),
              (float) (double) ((JSONObject) jsonProducer)
                  .get(Constants.PRICE_KW),
              (int) (long) ((JSONObject) jsonProducer)
                  .get(Constants.ENERGY_PER_DISTRIBUTOR)
          ));
        }
      } else {
        System.out.println("NU EXISTA PRODUCATORI");
      }

      if (jsonMonthlyUpdates != null) {

        for (Object update : jsonMonthlyUpdates) {
          JSONObject updt = (JSONObject) update;

          ArrayList<ConsumerInputData> consumerArrayList = new ArrayList<>();

          if (updt.get(Constants.NEW_CONSUMERS) != null) {
            for (Object iterator : (JSONArray) updt
                .get(Constants.NEW_CONSUMERS)) {
              consumerArrayList.add(new ConsumerInputData(
                  ((Long) ((JSONObject) iterator).get(Constants.ID))
                      .intValue(),
                  ((Long) ((JSONObject) iterator).get(Constants.INITIAL_BUDGET))
                      .intValue(),
                  ((Long) ((JSONObject) iterator).get(Constants.MONTHLY_INCOME))
                      .intValue()
              ));
            }
          } else {
            consumerArrayList = null;
          }

          ArrayList<DistributorChangesInputData> distributorChanges = new ArrayList<>();

          if (updt.get(Constants.DISTRIBUTOR_CHANGES) != null) {
            for (Object iterator : (JSONArray) updt
                .get(Constants.DISTRIBUTOR_CHANGES)) {
              distributorChanges.add(new DistributorChangesInputData(
                  ((Long) ((JSONObject) iterator).get(Constants.ID))
                      .intValue(),
                  ((Long) ((JSONObject) iterator).get(Constants.INFRASTRUCTURE_COST))
              ));
            }
          } else {
            distributorChanges = null;
          }

          ArrayList<ProducerChangesInputData> producerChanges = new ArrayList<>();

          if (updt.get(Constants.PRODUCER_CHANGES) != null) {
            for (Object iterator : (JSONArray) updt
                .get(Constants.PRODUCER_CHANGES)) {
              producerChanges.add(new ProducerChangesInputData(
                  ((Long) ((JSONObject) iterator).get(Constants.ID))
                      .intValue(),
                  ((Long) ((JSONObject) iterator).get(Constants.ENERGY_PER_DISTRIBUTOR))
                      .intValue()
              ));
            }
          } else {
            producerChanges = null;
          }
          monthlyUpdates.add(new MonthlyUpdateInputData(
              consumerArrayList, distributorChanges, producerChanges));

        }
      } else {
        System.out.println("NU EXISTA ACTUALIZARI");
      }

      if (jsonConsumers == null) {
        consumers = null;
      }

      if (jsonDistributors == null) {
        distributors = null;
      }
      if (jsonProducers == null) {
        producers = null;
      }

      if (jsonMonthlyUpdates == null) {
        monthlyUpdates = null;
      }

    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }

    return new Input((int) numberOfTurns, consumers, distributors, producers, monthlyUpdates);
  }
}
