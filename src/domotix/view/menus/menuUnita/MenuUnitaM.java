package domotix.view.menus.menuUnita;


import domotix.controller.Recuperatore;
import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import domotix.view.menus.menuUnita.gestioneStanza.MenuGestioneStanzaM;
import domotix.view.menus.menuUnita.gestioneUnita.MenuGestioneUnitaM;

/** @author Edoardo Coppola*/
public class MenuUnitaM {

    private static final String TITOLO = "Menu Unita Manutentore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Menu Gestione Unita Manutentore", "Menu Gestione Stanza Manutentore"};
    private static final String INDIETRO = "Indietro";
    private static final String UNITA_IMMOBILIARI_ESISTENTI = "Unità Immobiliari: ";
    private static final String NONE = "Nessuna unità immobiliare esistente. L'utente manutentore deve prima crearne una";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    /**
     * Presenta all'utente manutentore un menu che offre la possibilità di aprire un menu per manutentori per la gestione dell'unità immobiliare
     * o di aprire un menu per manutentori per la gestione di una stanza all'interno dell'unità immobiliare. Entrambe le operazioni avvengono dopo che l'utente ha scelto
     * su quale unità immobiliare lavorare. Se esiste solo un'unità immobiliare allora la scelta viene effettuata automaticamente. Se non ne esistono allora si torna al
     * menu precedente perché bisogna crearne una.
     * Il menu consete anche di tornare indietro e chiudere questo menu
     */
    public static void avvia(){

        String nomeUnitaSuCuiLavorare = premenuUnita();

        if(nomeUnitaSuCuiLavorare == null) return;

        if(nomeUnitaSuCuiLavorare.equals(NONE)){
            System.out.println(NONE);
            return;
        }


        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare));

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: // menu gestione unita
                    MenuGestioneUnitaM.avvia(nomeUnitaSuCuiLavorare);
                    break;
                case 2: //menu gestione stanza
                    MenuGestioneStanzaM.avvia(nomeUnitaSuCuiLavorare);
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private static String premenuUnita(){
        String[] nomiUnitaImmobiliari = Recuperatore.getNomiUnita();

        if(nomiUnitaImmobiliari.length == 0) //non esistono unità immobiliari
            return NONE;

        //se solo una scelta allora seleziono quella e procedo automaticamente
        if (nomiUnitaImmobiliari.length == 1)
            return nomiUnitaImmobiliari[0];

        MyMenu m = new MyMenu(UNITA_IMMOBILIARI_ESISTENTI, nomiUnitaImmobiliari);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiUnitaImmobiliari[scelta-1];
    }

}
