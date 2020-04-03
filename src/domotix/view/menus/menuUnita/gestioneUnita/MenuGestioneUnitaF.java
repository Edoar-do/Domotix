package domotix.view.menus.menuUnita.gestioneUnita;


import domotix.controller.Recuperatore;
import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import domotix.controller.Modificatore;


/** @author Edoardo Coppola*/
public class MenuGestioneUnitaF {
    private static final String TITOLO = "Menu Gestione Unità Fruitore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Visualizza Descrizione Unita", "Cambia Stato Sensore", "Cambia Stato Attuatore"};
    private static final String INDIETRO = "Indietro";
    private static final String GUIDA_IN_LINEA = "Consultare la guida in linea per maggiori informazioni";
    private static final String SUCCESSO_CAMBIO_STATO = "Stato cambiato con successo ";
    private static final String ERRORE_CAMBIO_STATO = "Cambiamento dello stato fallito per ";
    private static final String SUCCESSO_CAMBIO_STATO_S = SUCCESSO_CAMBIO_STATO + "al sensore selezionato";
    private static final String SUCCESSO_CAMBIO_STATO_A = SUCCESSO_CAMBIO_STATO + "all'attuatore selezionato";
    private static final String ERRORE_CAMBIO_STATO_S = ERRORE_CAMBIO_STATO + "il sensore selezionato. " + GUIDA_IN_LINEA;
    private static final String ERRORE_CAMBIO_STATO_A = ERRORE_CAMBIO_STATO + "l'attuatore selezionato. " + GUIDA_IN_LINEA;
    private static final String SENSORI_UNITA = "Sensori presenti nell'unita' immobiliare: ";
    private static final String ATTUATORI_UNITA = "Attuatori presenti nell'unita' immobiliare: ";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    /**
     * Presenta all'utente fruitore un menu che offre la possibilità di visualizzare una descrizione dell'unità immobiliare e cambiare lo stato di un sensore e  di un attuatore
     * Consente anche di tornare indietro e chiudere questo menu
     * @param nomeUnitaSuCuiLavorare è il nome dell'unità immobiliare scelta nel menu precedente e su cui operare
     */
    public static void avvia(String nomeUnitaSuCuiLavorare){
        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare));

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);
            String daCambiare;
            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza descrizione unita
                    System.out.println(Recuperatore.getDescrizioneUnita(nomeUnitaSuCuiLavorare));
                    break;
                case 2: //cambia stato sensore
                    daCambiare = premenuSensoriUnita(nomeUnitaSuCuiLavorare);
                    if(daCambiare != null) {
                        if (Modificatore.cambiaStatoSensore(daCambiare, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_CAMBIO_STATO_S);
                        else System.out.println(ERRORE_CAMBIO_STATO_S);
                    }
                    break;
                case 3: //cambia stato attuatore
                    daCambiare = premenuAttuatoriUnita(nomeUnitaSuCuiLavorare);
                    if(daCambiare != null) {
                        if (Modificatore.cambiaStatoAttuatore(daCambiare, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_CAMBIO_STATO_A);
                        else System.out.println(ERRORE_CAMBIO_STATO_A);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private static String premenuSensoriUnita(String unita) {
        String[] sensoriUnita = Recuperatore.getNomiSensori(unita);
        MyMenu m = new MyMenu(SENSORI_UNITA, sensoriUnita);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : sensoriUnita[scelta-1];
    }

    private static String premenuAttuatoriUnita(String unita){
        String[] attuatoriUnita = Recuperatore.getNomiAttuatori(unita);
        MyMenu m = new MyMenu(ATTUATORI_UNITA, attuatoriUnita);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : attuatoriUnita[scelta-1];
    }

}
