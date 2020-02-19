package domotix.view.menus.menuUnita;


import domotix.logicUtil.MyMenu;
import domotix.view.menus.menuUnita.gestioneStanza.MenuGestioneStanzaM;
import domotix.view.menus.menuUnita.gestioneUnita.MenuGestioneUnitaM;

/** @author Edoardo Coppola*/
public class MenuUnitaM {

    private static final String TITOLO = "Menu Unita Manutentore ";
    private static final String[] VOCI = {"Menu Gestione Unita", "Menu Gestione Stanza"};
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
                    MenuGestioneUnitaM.avvia();
                    break;
                case 2: //menu gestione stanza
                    MenuGestioneStanzaM.avvia();
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
