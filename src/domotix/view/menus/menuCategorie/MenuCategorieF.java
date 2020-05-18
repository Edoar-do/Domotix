package domotix.view.menus.menuCategorie;

import domotix.controller.Rappresentatore;
import static domotix.view.ViewUtil.*;
import domotix.view.MyMenu;
import domotix.view.menus.menuCategorie.attuatori.MenuCategorieAttuatoriF;
import domotix.view.menus.menuCategorie.sensori.MenuCategorieSensoriF;

/** @author Edoardo Coppola*/
public class MenuCategorieF {
    private static final String TITOLO = "Menu Categorie Fruitore ";
    private static final String[] VOCI = {"Menu Categorie Sensori Fruitore", "Menu Categorie Attuatori Fruitore"};
    private static final String INDIETRO = "Indietro";

    private MyMenu menu;
    private Rappresentatore r;
    private MenuCategorieSensoriF menuCategorieSensoriF;
    private MenuCategorieAttuatoriF menuCategorieAttuatoriF;

    public MenuCategorieF(Rappresentatore r, MyMenu m){
        this.menu = m;
        menu.setTitolo(TITOLO);
        menu.setVoci(VOCI);
        this.r = r;
        menuCategorieSensoriF = new MenuCategorieSensoriF(menu, r);
        menuCategorieAttuatoriF = new MenuCategorieAttuatoriF(menu, r);
    }

    /**
     * Prensenta all'utente fruitore un menu che consente di aprire un menu per la gestione delle categorie di sensori
     * oppure un menu per la gestione delle categorie di attuatori
     */
    public void avvia(){
ripristinaMenuOriginale(menu, TITOLO, VOCI);

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //Menu categorie sensori fruitore
                    menuCategorieSensoriF.avvia();
ripristinaMenuOriginale(menu, TITOLO, VOCI);
                    break;
                case 2: //Menu categorie attuatori fruitore
                    menuCategorieAttuatoriF.avvia();
ripristinaMenuOriginale(menu, TITOLO, VOCI);
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
