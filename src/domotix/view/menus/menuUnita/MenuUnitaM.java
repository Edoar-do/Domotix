package domotix.view.menus.menuUnita;


import domotix.controller.Recuperatore;
import domotix.logicUtil.MyMenu;
import domotix.view.menus.menuUnita.gestioneStanza.MenuGestioneStanzaM;
import domotix.view.menus.menuUnita.gestioneUnita.MenuGestioneUnitaM;

/** @author Edoardo Coppola*/
public class MenuUnitaM {

    private static final String TITOLO = "Menu Unita Manutentore ";
    private static final String[] VOCI = {"Menu Gestione Unita Manutentore", "Menu Gestione Stanza Manutentore"};
    private static final String INDIETRO = "Indietro";
    private static final String UNITA_IMMOBILIARI_ESISTENTI = "Unità Immobiliari: ";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(){

        String nomeUnitaSuCuiLavorare = premenuUnita();
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
        MyMenu m = new MyMenu(UNITA_IMMOBILIARI_ESISTENTI, nomiUnitaImmobiliari);
        int scelta = m.scegli(INDIETRO);
        return nomiUnitaImmobiliari[scelta-1];
    }

}
