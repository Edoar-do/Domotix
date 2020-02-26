package domotix.view.menus.menuUnita;

import domotix.controller.Recuperatore;
import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import domotix.view.menus.menuUnita.gestioneStanza.MenuGestioneStanzaF;
import domotix.view.menus.menuUnita.gestioneUnita.MenuGestioneUnitaF;

/** @author Edoardo Coppola*/
public class MenuUnitaF {
    private static final String TITOLO = "Menu Unita Fruitore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Menu Gestione Unita Fruitore", "Menu Gestione Stanza Fruitore"};
    private static final String INDIETRO = "Indietro";
    private static final String UNITA_IMMOBILIARI_ESISTENTI = "Unità Immobiliari: ";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(){
        String nomeUnitaSuCuiLavorare = premenuUnita();

        if (nomeUnitaSuCuiLavorare == null) return;

        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare));

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: // menu gestione unita
                    MenuGestioneUnitaF.avvia(nomeUnitaSuCuiLavorare);
                    break;
                case 2: //menu gestione stanza
                    MenuGestioneStanzaF.avvia(nomeUnitaSuCuiLavorare);
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private static String premenuUnita(){
        String[] nomiUnitaImmobiliari = Recuperatore.getNomiUnita();

        //se solo una scelta allora seleziono quella e procedo automaticamente
        if (nomiUnitaImmobiliari.length == 1)
            return nomiUnitaImmobiliari[0];

        MyMenu m = new MyMenu(UNITA_IMMOBILIARI_ESISTENTI, nomiUnitaImmobiliari);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiUnitaImmobiliari[scelta-1];
    }
}