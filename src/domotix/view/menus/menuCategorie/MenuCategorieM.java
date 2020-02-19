package domotix.view.menus.menuCategorie;

import domotix.logicUtil.MyMenu;
import domotix.view.menus.menuCategorie.attuatori.MenuCategorieAttuatoriM;
import domotix.view.menus.menuCategorie.sensori.MenuCategorieSensoriM;

/** @author Edoardo Coppola*/
public class MenuCategorieM {
    private static final String TITOLO = "Menu Categorie Manutentore ";
    private static final String[] VOCI = {"Menu Categorie Sensori Manutentore", "Menu Categorie Attuatori Manutentori"};
    private static final boolean INDIETRO = true;

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(){

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //Menu categorie sensori manutentore
                    MenuCategorieSensoriM.avvia();
                    break;
                case 2: //Menu categorie attuatori manutentori
                    MenuCategorieAttuatoriM.avvia();
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
