package domotix.view.menus.menuPrincipale;

import domotix.view.MyMenu;
import domotix.view.menus.menuCategorie.MenuCategorieF;
import domotix.view.menus.menuUnita.MenuUnitaF;

/** @author Edoardo Coppola*/
public class MenuFruitore {
    private static final String TITOLO = "Menu Fruitore ";
    private static final String[] VOCI = {"Menu Categorie Dispositivi Fruitore", "Menu Unita Fruitore"};
    private static final String INDIETRO = "Indietro";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    /**
     * Presenta all'utente fruitore un menu che offre la possibilità di aprire un menu per fruitori per la gestione delle categorie di sensori ed
     * attuatori oppure di aprire un menu per fruitori per la gestione dell'unità immobiliare oppure di tornare indietro e chiudere questo menu
     */
    public static void avvia(){

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //MenuCategorieDispositivi
                    MenuCategorieF.avvia();
                    break;
                case 2: //MenuUnita
                    MenuUnitaF.avvia();
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
