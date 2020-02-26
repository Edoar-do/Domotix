package domotix.view.menus.menuCategorie;

import domotix.view.MyMenu;
import domotix.view.menus.menuCategorie.attuatori.MenuCategorieAttuatoriF;
import domotix.view.menus.menuCategorie.sensori.MenuCategorieSensoriF;

/** @author Edoardo Coppola*/
public class MenuCategorieF {
    private static final String TITOLO = "Menu Categorie Fruitore ";
    private static final String[] VOCI = {"Menu Categorie Sensori Fruitore", "Menu Categorie Attuatori Fruitore"};
    private static final String INDIETRO = "Indietro";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    /**
     * Prensenta all'utente fruitore un menu che consente di aprire un menu per la gestione delle categorie di sensori
     * oppure un menu per la gestione delle categorie di attuatori
     */
    public static void avvia(){

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //Menu categorie sensori fruitore
                    MenuCategorieSensoriF.avvia();
                    break;
                case 2: //Menu categorie attuatori fruitore
                    MenuCategorieAttuatoriF.avvia();
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
