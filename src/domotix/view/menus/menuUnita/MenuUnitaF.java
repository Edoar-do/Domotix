package domotix.view.menus.menuUnita;

import domotix.logicUtil.MyMenu;
import domotix.view.menus.menuUnita.gestioneStanza.MenuGestioneStanzaF;
import domotix.view.menus.menuUnita.gestioneUnita.MenuGestioneUnitaF;

/** @author Edoardo Coppola*/
public class MenuUnitaF {
    private static final String TITOLO = "Menu Unita Fruitore ";
    private static final String[] VOCI = {"Menu Gestione Unita Fruitore", "Menu Gestione Stanza Fruitore"};
    private static final boolean INDIETRO = true;

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(){
        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: // menu gestione unita
                    MenuGestioneUnitaF.avvia();
                    break;
                case 2: //menu gestione stanza
                    MenuGestioneStanzaF.avvia();
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
