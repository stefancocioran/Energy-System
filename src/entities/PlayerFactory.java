package entities;

import strategies.EnergyChoiceStrategyType;
import utils.Constants;

public class PlayerFactory {

  private static PlayerFactory instance;

  /**
   * Returneaza instanta unui jucator
   */
  public static synchronized PlayerFactory getInstance() {
    if (instance == null) {
      instance = new PlayerFactory();
      return instance;
    }
    return instance;
  }

  /**
   * Creeaza un jucator
   */
  public Player createEntity(final String entityType, float price,
      EnergyChoiceStrategyType strategyType,
      EnergyType energyType, final int... args) {
    return switch (entityType) {
      case Constants.CONSUMER -> new Consumer(args[Constants.ARG_NUM_0], args[Constants.ARG_NUM_1],
          args[Constants.ARG_NUM_2]);
      case Constants.DISTRIBUTOR -> new Distributor(args[0],
          args[Constants.ARG_NUM_1], args[Constants.ARG_NUM_2], args[Constants.ARG_NUM_3],
          args[Constants.ARG_NUM_4], strategyType);
      case Constants.PRODUCER -> new EnergyProducer(args[Constants.ARG_NUM_0], energyType,
          args[Constants.ARG_NUM_1], price, args[Constants.ARG_NUM_2]);
      default -> throw new IllegalArgumentException("Object type does not match.");
    };
  }
}
