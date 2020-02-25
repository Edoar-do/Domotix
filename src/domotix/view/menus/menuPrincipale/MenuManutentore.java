package domotix.view.menus.menuPrincipale;

import domotix.view.MyMenu;
import domotix.view.menus.menuCategorie.MenuCategorieM;
import domotix.view.menus.menuUnita.MenuUnitaM;

/** @author Edoardo Coppola*/
public class MenuManutentore {
    private static final String TITOLO = "Menu Manutentore ";
    private static final String[] VOCI = {"Menu Categorie Dispositivi Manutentore", "Menu Unita Manutentore"};
    private static final String INDIETRO = "Indietro";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(){

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //Menu Categorie Dispositivi Manutentore
                    MenuCategorieM.avvia();
                    break;
                case 2: //Menu Unita Manutentore
                    MenuUnitaM.avvia();
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
