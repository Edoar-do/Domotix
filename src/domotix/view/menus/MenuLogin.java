package domotix.view.menus;

import domotix.view.MyMenu;
import domotix.view.menus.menuPrincipale.MenuFruitore;
import domotix.view.menus.menuPrincipale.MenuManutentore;

/** @author Edoardo Coppola*/
public class MenuLogin {

    private static final String TITOLO = "Menu di login: Manutentore o Fruitore";
    private static final String[] VOCI = {"Manutentore", "Fruitore"};
    private static final String SALVA_ED_ESCI = "Salva ed Esci";

    private static MyMenu menuLogin = new MyMenu(TITOLO, VOCI);

    /**
     * Presenta il menu di login all'utente di modo che possa accedere come manutentore o come fruitore oppure che possa salvare ed uscire dall'applicazione
     */
    public static void avvia(){

        int sceltaMenu = 0;
        do {
            sceltaMenu = menuLogin.scegli(SALVA_ED_ESCI);

            switch(sceltaMenu) {
                case 0://Salva ed Esci
                    return;
                case 1: //Manutentore
                    MenuManutentore.avvia();
                    break;
                case 2: //Fruitore
                    MenuFruitore.avvia();
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
