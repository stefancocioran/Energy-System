# Proiect Energy System 

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

Student: Cocioran Stefan, 321 CA
##Implementare
### database
* ConsumersDatabase - baza de date pentru consumatori, contine metode pentru:
    * actualizarea distribuitorilor unui consumator
    * actualizarea bugetului consumatorilor (incasare salariu)
    * plata facturilor lunare
    * returnarea unui consumator dupa Id
  
* DistributorsDatabase - baza de date pentru distribuitori, contine metode pentru:
    * actualizarea pretului contractelor
    * recalcularea taxelor/costurilor lunare ale distribuitorilor
    * plata taxelor
    * semnare/terminare de contracte intre distribuitori si consumatori
    * returnarea contractului unui consumator in functie de distribuitor
    * returnarea unui distribuitor in functie de Id
 
* ProducersDatabase - baza de date pentru producatori, contine metode pentru:
    * notificarea distribuitorilor atunci cand apar modificari
    * retinerea distribuitorilor pe care i-a avut un producator la sfarsit de luna
    
* UpdatesDatabase - baza de date pentru actualizarile lunare, contine o metode de aplicare a actualizarilor
 			pentru o luna primita ca parametru
 
* Game - clasa implementeaza desfasurarea jocului
    
### entities
* Consumer - clasa folosita pentru prelucrarea datelor unui consumator
* Contract - clasa folosita pentru prelucrarea datelor dintr-un contract
* Distributor - clasa folosita pentru prelucrarea datelor unui distribuitor
* EnergyProducer - clasa folosita pentru prelucrarea datelor unui producator
* Player - interfata folosita pentru implementarea consumatorilor, distribuitorilor si producatorilor
* PlayerFactory - factory pentru crearea jucatorilor/entitatilor
* Observer (*Observer Pattern*) - fiecare distribuitor trebuie sa afle daca au aparut modificari la producătorii sai,
de aceea acestia vor implementa interfata Observer
* Observable (*Observer Pattern*) - clasa folosita pentru ca un distribuitor (observer) sa mentina sub observatie producatorii (Observables) ce ii
furnizeaza energia necesara
* MonthlyStat - clasa folosita pentru retinerea distribuitorilor pe care i-a avut un producator la sfarsit de luna (statistici lunare)
* EnergyType - tipul de energie furnizata de catre producatori

 
### fileio
* ConsumerInputData - contine informatii despre un consumator rezultate din parsarea fisierelor de input
* ConsumerOutputData - clasa de formatare output pentru consumator 
* CostChangesInputData- baza de date pentru seriale
* DistributorInputData - contine informatii despre un distribuitor rezultate din parsarea fisierelor de input
* DistributorOutputData -  clasa de formatare output pentru distribuitor
* Input - contine informatiile despre input
* InputLoader - citeste si parseaza datele din fisierele de input
* MonthlyUpdateInputData - contine informatii despre un update lunar rezultate din parsarea fisierelor de input
* Output - compune output-ul in formatul coresepunzator
* ProducerChangesInputData - contine informatii despre un update lunar rezultate din parsarea fisierelor de input
* DistributorChangesInputData -  contine informatii despre un modificarile ce ii revin unui distribuitor
* ProducerInputData - contine informatii despre un modificarile ce ii revin unui producator
* ProducerOutputData - clasa de formatare output pentru producator

### strategies - Strategy Design Pattern
* EnergyChoiceStrategyType - tipuri de strategii pentru alegerea producatorilor unui distribuitor
* GreenStrategy - un distribuitor isi alege producatorii prioritizandu-i pe cei cu renewable energy intai, apoi după preț, apoi după cantitate
* PriceStrategy - un distribuitor isi alege producatorii prioritizand doar dupa pret, apoi dupa cantitate
* QuantityStrategy - un distribuitor isi alege producatorii prioritizand dupa cantitatea de energie oferita per distribuitor
### common
* Constants - clasa care contine toate constantele folosite

 ## Desfasurarea jocului:
   * se aplica update-urile lunare pentru consumatori si distribuitori
   * distribuitorii isi actualizeaza costul de productie si preturile pentru contracte
   * sunt taiate contractele expirate
   * consumatorii primesc salariile, isi aleg un distribuitor si platesc factura lunara
   * distribuitorii platesc taxele/costurile lunare
   * se aplica update-urile lunare pentru producatori
   * distribuitorii isi aleg producatori
   * se retin ce distribuitori a avut fiecare producator la sfarsit de luna
   * sunt eliminati jucatorii care au intrat in faliment
  
