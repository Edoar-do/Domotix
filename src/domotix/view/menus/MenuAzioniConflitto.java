package domotix.view.menus;


import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.view.InputDati;
import domotix.view.MyMenu;

import java.util.ArrayList;

public class MenuAzioniConflitto {
    private static final String NESSUNA = "Nessuna delle presenti";
    private static final String TITOLO_ELIMINA = "Seleziona quale azione ignorare ed eliminare: ";
    private static final String TITOLO_ESEGUI = "Seleziona quale azione eseguire immediatamente: ";

    private static final String AVVISA_PROSEGUIMENTO_DOPO_ELIMINA = "Verranno ora presentate le azioni rimenenti per decidere quale eseguire subito. Premere invio per procedere...";
    private static final String AVVISA_PROSEGUIMENTO_DOPO_ESEGUI = "Tutte le azioni rimanenti saranno riprogrammate per l'esecuzione. Premere invio per procedere...";

    private static MyMenu menu = null;

    /**
     * Avvia l'interazione con l'utente per risolvere i conflitti con le azioni programmate in conflitto
     */
    public static void avvia(ArrayList<String> idAzioni){
        ArrayList<String> descrizioniAzione = new ArrayList<>();

        //recupero le descrizioni delle azioni
        for (int i = 0; i < idAzioni.size(); i++) {
            descrizioniAzione.add(i, Recuperatore.getDescrizioneAzioneProgrammata(idAzioni.get(i)));
        }

        if (idAzioni.size() > 0)
            premenuElimina(idAzioni, descrizioniAzione);

        if (idAzioni.size() > 0) {
            InputDati.leggiStringa(AVVISA_PROSEGUIMENTO_DOPO_ELIMINA);

            premenuEsegui(idAzioni, descrizioniAzione);
        }

        if (idAzioni.size() > 0)
            InputDati.leggiStringa(AVVISA_PROSEGUIMENTO_DOPO_ESEGUI);
    }

    private static void premenuElimina(ArrayList<String> idAzioni, ArrayList<String> descrizioniAzioni) {
        int sceltaMenu = 0;

        do {
            menu = new MyMenu(TITOLO_ELIMINA, descrizioniAzioni.toArray(new String[0]));
            sceltaMenu = menu.scegli(NESSUNA);

            if (sceltaMenu > 0) {
                String id = idAzioni.get(sceltaMenu-1); //recupero l'id
                //elimino l'azione da rimuovere dagli elenchi
                idAzioni.remove(sceltaMenu-1);
                descrizioniAzioni.remove(sceltaMenu-1);
                Modificatore.rimuoviAzioneProgrammata(id, false); //rimuovo l'azione dai dati
            }
        }while(sceltaMenu != 0 && idAzioni.size() > 0);
    }

    private static void premenuEsegui(ArrayList<String> idAzioni, ArrayList<String> descrizioniAzioni) {
        int sceltaMenu = 0;

        do {
            menu = new MyMenu(TITOLO_ESEGUI, descrizioniAzioni.toArray(new String[0]));
            sceltaMenu = menu.scegli(NESSUNA);

            if (sceltaMenu > 0) {
                String id = idAzioni.get(sceltaMenu-1); //recupero l'id
                //elimino l'azione da rimuovere dagli elenchi
                idAzioni.remove(sceltaMenu-1);
                descrizioniAzioni.remove(sceltaMenu-1);
                Modificatore.rimuoviAzioneProgrammata(id, true); //rimuovo l'azione dai dati eseguendola
            }
        }while(sceltaMenu != 0 && idAzioni.size() > 0);
    }
}
