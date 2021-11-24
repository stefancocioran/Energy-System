package utils;

public interface Observer {

  /**
   * Metoda notifica observatorul/distribuitorul atunci cand sunt facute modificari
   */

  void update();

  /**
   * Returneaza id-ul observatorului/distribuitorului
   */
  int getId();

  /**
   * Metoda ne spune daca observatorul/distribuitorul este in failment sau nu
   */
  boolean getIsBankrupt();

}
