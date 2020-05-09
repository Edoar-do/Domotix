package domotix.view.menus;

import domotix.controller.Modificatore;
import domotix.controller.Rappresentatore;
import domotix.controller.Recuperatore;
import domotix.view.InputDati;
import domotix.view.MyMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe rappresentante il menu per le azioni programmate in conflitto.
 * Una azione programmata viene considerata in conflitto se, al momento dell'avvio del programma,
 * vi sono azioni salvate tra i dati. In questo caso e' demandato all'utente come processare tali azioni.
 * 
 * @author paolopasqua
 */
public class MenuAzioniConflitto {
    private static final String NESSUNA = "Nessuna delle presenti";
    private static final String TITOLO_ELIMINA = "Seleziona quale azione ignorare ed eliminare: ";
    private static final String TITOLO_ESEGUI = "Seleziona quale azione eseguire immediatamente: ";

    private static final String AVVISA_PROSEGUIMENTO_DOPO_ELIMINA = "Verranno ora presentate le azioni rimenenti per decidere quale eseguire subito. Premere invio per procedere...";
    private static final String AVVISA_PROSEGUIMENTO_DOPO_ESEGUI = "Tutte le azioni rimanenti saranno riprogrammate per l'esecuzione. Premere invio per procedere...";

    private MyMenu menu = null;
    private Rappresentatore rappresentatore = null;
    private Recuperatore recuperatore = null;
    private Modificatore modificatore = null;

    /**
     * Costruttore della classe.
     * 
     * @param rappresentatore  istanza del controller Recuperatore
     * @param modificatore  istanza del controller Modificatore
     */
    public MenuAzioniConflitto(Rappresentatore rappresentatore, Modificatore modificatore, Recuperatore recuperatore) {
        this.rappresentatore = rappresentatore;
        this.modificatore = modificatore;
        this.recuperatore = recuperatore;
    }

    /**
     * Avvia l'interazione con l'utente per risolvere i conflitti con le azioni programmate in conflitto
     */
    public void avvia(){
        List<String> idAzioni = this.recuperatore.getIdAzioniProgrammate();
        List<String> descrizioniAzione = new ArrayList<>();

        if (!idAzioni.isEmpty()) {
            this.menu = new MyMenu("Azioni in conflitto", null);

            //recupero le descrizioni delle azioni
            for (int i = 0; i < idAzioni.size(); i++) {
                descrizioniAzione.add(i, this.rappresentatore.getDescrizioneAzioneProgrammata(idAzioni.get(i)));
            }

            premenuElimina(idAzioni, descrizioniAzione);

            if (idAzioni.size() > 0) {
                InputDati.leggiStringa(AVVISA_PROSEGUIMENTO_DOPO_ELIMINA);

                premenuEsegui(idAzioni, descrizioniAzione);
            }

            if (idAzioni.size() > 0)
                InputDati.leggiStringa(AVVISA_PROSEGUIMENTO_DOPO_ESEGUI);
        }
    }

    private void premenuElimina(List<String> idAzioni, List<String> descrizioniAzioni) {
        int sceltaMenu = 0;

        do {
            this.menu.setSottotitolo(TITOLO_ELIMINA);
            this.menu.setVoci(descrizioniAzioni.toArray(new String[0]));
            sceltaMenu = menu.scegli(NESSUNA);

            if (sceltaMenu > 0) {
                String id = idAzioni.get(sceltaMenu-1); //recupero l'id
                //elimino l'azione da rimuovere dagli elenchi
                idAzioni.remove(sceltaMenu-1);
                descrizioniAzioni.remove(sceltaMenu-1);
                this.modificatore.rimuoviAzioneProgrammata(id, false); //rimuovo l'azione dai dati
            }
        }while(sceltaMenu != 0 && idAzioni.size() > 0);
    }

    private void premenuEsegui(List<String> idAzioni, List<String> descrizioniAzioni) {
        int sceltaMenu = 0;

        do {
            this.menu.setSottotitolo(TITOLO_ESEGUI);
            this.menu.setVoci(descrizioniAzioni.toArray(new String[0]));
            sceltaMenu = menu.scegli(NESSUNA);

            if (sceltaMenu > 0) {
                String id = idAzioni.get(sceltaMenu-1); //recupero l'id
                //elimino l'azione da rimuovere dagli elenchi
                idAzioni.remove(sceltaMenu-1);
                descrizioniAzioni.remove(sceltaMenu-1);
                this.modificatore.rimuoviAzioneProgrammata(id, true); //rimuovo l'azione dai dati eseguendola
            }
        }while(sceltaMenu != 0 && idAzioni.size() > 0);
    }
}
