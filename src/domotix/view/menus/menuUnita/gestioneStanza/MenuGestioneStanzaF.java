package domotix.view.menus.menuUnita.gestioneStanza;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.model.bean.device.Modalita;
import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import domotix.view.menus.menuUnita.gestioneStanza.gestioneArtefatto.MenuGestioneArtefattoF;

/** @author Edoardo Coppola*/
public class MenuGestioneStanzaF {

    private static final String TITOLO = "Menu Gestione Stanza Fruitore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Visualizza Descrizione Stanza", "Menu Gestione Artefatto Fruitore", "Imposta modalità operativa di un attuatore associato alla stanza" };
    private static final String INDIETRO = "Indietro";


    private static final String ELENCO_STANZE = "Elenco delle stanze presenti nell'unità: ";
    private static final String ELENCO_ATTUATORI_STANZA = "Elenco degli attuatori della stanza: ";
    private static final String MODALITA_OPERATIVE = "Modalità Operative impostabili all'attuatore: ";
    private static final String NONE = "Non ci sono modalità operative impostabili all'attuatore all'infuori di quella corrente";
    private static final String SUCCESSO_SETTAGGIO_MODALITA = "Modalità operativa %s impostata con successo";
    private static final String FALLIMENTO_SETTAGGIO_MODALITA = "Impostazione modalità operativa %s fallita. Consultare la guida in linea per maggiori informazioni";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    /**
     * Presenta all'utente fruitore un menu che offre la possibilità di visualizzare la descrizione di una stanza interna all'unità (passata come parametro),
     * aprire un menu per la gestione degli artefatti all'interno di una stanza, impostare la modalità operativa di un attuatore associato alla stanza
     * oppure di tornare indietro e chiudere questo menu
     * Tali operazioni sono effettuabili solo dopo che l'utente fruitore ha scelto su quale stanza operare da un menu le cui voci sono le stanze presenti
     * nell'unità
     * @param nomeUnitaSuCuiLavorare è il nome dell'unità dalla quale scegliere la stanza su cui lavorare
     */
    public static void avvia(String nomeUnitaSuCuiLavorare) {
        String nomeStanza = premenuStanze(nomeUnitaSuCuiLavorare);

        if (nomeStanza == null) return;

        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare, nomeStanza));

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch (sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza descrizione stanza
                    System.out.println(Recuperatore.getDescrizioneStanza(nomeStanza, nomeUnitaSuCuiLavorare));
                    break;
                case 2://menu gestione artefatto fruitore
                    MenuGestioneArtefattoF.avvia(nomeUnitaSuCuiLavorare, nomeStanza);
                    break;
                case 3: //imposta modalità operativa
                    String nomeAttuatore = premenuAttuatori(nomeStanza, nomeUnitaSuCuiLavorare);
                    if(nomeAttuatore != null){
                            String mode = premenuModalitaOperative(nomeAttuatore);
                            if(mode == null) break; //scelto di tornare indietro
                            if(mode.equals((NONE))){ //non ci sono modalità impostabili all'infuori di quella corrente
                                System.out.println(NONE);
                                break;
                            }
                            if(Modificatore.setModalitaOperativa(nomeAttuatore, mode))
                                System.out.println(String.format(SUCCESSO_SETTAGGIO_MODALITA, mode));
                            else
                                System.out.println(String.format(FALLIMENTO_SETTAGGIO_MODALITA, mode));
                    }
                    break;
            }
        } while (sceltaMenu != 0);
    }

    private static String premenuStanze(String unita){
        String[] nomiStanze = Recuperatore.getNomiStanze(unita);

        //se solo una scelta allora seleziono quella e procedo automaticamente
        if (nomiStanze.length == 1)
            return nomiStanze[0];

        MyMenu m = new MyMenu(ELENCO_STANZE, nomiStanze);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiStanze[scelta-1];
    }

    private static String premenuAttuatori(String stanza, String unita) {
        String[] nomiAttuatori = Recuperatore.getNomiAttuatori(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ATTUATORI_STANZA, nomiAttuatori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiAttuatori[scelta - 1];
    }

    private static String premenuModalitaOperative(String attuatore){
        String[] modes = Recuperatore.getModalitaOperativeImpostabili(attuatore); //ritorna le modalità operative non correnti
        if(modes.length == 0) //se non ce n'è sono allora l'attuatore ha una sola modalità operativa e non la si cambia
            return NONE;
        if(modes.length == 1) //se ce n'è solo una impostabile allora la scelta è automatica
            return modes[0];
        MyMenu m = new MyMenu(MODALITA_OPERATIVE, modes);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : modes[scelta-1];
    }
}
