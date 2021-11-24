package utils;

import java.util.ArrayList;
import java.util.List;

public class Observable {

  private final List<Observer> observers = new ArrayList<>();

  /**
   * Adauga un observator in lista
   */

  public void addObserver(Observer observer) {
    this.observers.add(observer);
  }

  /**
   * Elimina un observator din lista
   */

  public void removeObserver(Observer observer) {
    this.observers.remove(observer);
  }

  /**
   * Returneaza o lista cu toti observatorii
   */
  public List<Observer> getObservers() {
    return observers;
  }
}
