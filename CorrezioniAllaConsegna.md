Correzioni alla consegna
========================

La versione v5.1 di Domotix rivoluziona il codice per realizzare le modifiche richieste durante la fase di correzione.

Le correzioni da apportare al codice date dal professore alla consegna sono le seguenti:
1. Evitare i Singleton
2. Evitare classi statiche per intere porzioni di progetto
3. Evitare i toString() per costruire la descrizione da mostrare a video per un'entità
4. Suddividere le classi troppo grosse in più classi (ed evitare "troppe" righe di codice)

Le modifiche pensate per soddisfare le correzioni sono:
* Trasformare i singleton in classi istanziabili, in particolar modo:
  * Classi timer: non è necessario l'accesso globale, è sufficiente istanziarli nel main in quanto unico punto di uso.
  * Classi 'ElencoXXX': diventeranno classi da istanziare e di conseguenza essere passate ovunque siano necessari. 
  * Classi "operazioni iniziali e finali": da rivedere in modo da renderli istanziabili o renderle funzioni di utilità.
  * Classi di lettura, scrittura e altre operazioni sui dati locali: rendere le implementazioni delle interfaccie già presenti instanziabili. Passare queste istanze alle relative parti del controller che le utilizzano.
* Trasformare le classi statiche in classi istanziabili, dove le dipendenze sono istanze indicate nel costruttore e richiamate nei diversi metodi:
  * Classi controller: diventano singole classi istanziabili le quali richiedono il riferimento alle istanze del model nel costruttore.
  * Classi view: diventano singole classi istanziabili, le quali chiedono quasi tutte il riferimento all'istanza del controller nel costruttore. Inoltre, i diversi menu ramificati vengono istanziati all'interno della classe che lo utilizzano: ad esempio la classe 'MenuLogin' genera l'istanza della classe 'MenuFruitore' e 'MenuManutentore'.
* Senza toccare i metodi toString() così costruiti, si rende indipendente la classe Recuperatore da essi costruendo la stringa dai singoli attributi (questo se necessario effettivamente). Eventualmente si può dividere questa responsabilità in un'altra classe.
* Onde evitare di dover passare molte istanze per rappresentare il model o il controller, si può pensare di implementare tale struttura:
  * Implementare un'interfaccia Model contenente i metodi per recuperare le istanze delle cinque entità principali (categorie, dispositivi e unità). In questo modo un solo riferimento ad un'istanza di questa interfaccia è da passare ai costruttori che utilizzano il model.
  * Sviluppare una classe AccessoModel, la quale implementa l'interfaccia sopra, per effettuare l'accesso al model con i metodi appositi.
  * Implementare un'interfaccia Controller analoga a quella per il model. Da analizzare se suddividere i molteplici metodi del controller in interfacce distinte o raggrupparle tutte in questa.
  * Sviluppare una classe AccessoController, la quale implementa l'interfaccia sopra, per effettuare l'accesso al controller in un'unica istanza. (Anche qui si ha poi una sola istanza da passare)
  * Nel main generare tutte le istanze necessarie e collegarle tra loro tramite i riferimenti indicati nei costruttori.
* Sul suddividere le classi "grandi", si dovranno valutare tutte le classi una per una.

I vantaggi ottenuti alla fine di tutte le migliorie saranno due:
* Nessun componente statico (o quanto meno il minimo necessario).
* Modello MVC puro (o quanto più puro possibile)
