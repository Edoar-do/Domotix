package domotix.view.menus;

import domotix.controller.Importatore;
import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.controller.Verificatore;
import domotix.view.MyMenu;
import domotix.view.menus.menuPrincipale.MenuFruitore;
import domotix.view.menus.menuPrincipale.MenuManutentore;
import static domotix.view.ViewConstants.*;

/** @author Edoardo Coppola*/
public class MenuLogin {

    private static final String TITOLO = "Menu di login: Manutentore o Fruitore";
    private static final String[] VOCI = {"Manutentore", "Fruitore"};


    private MyMenu menu;
    private Interpretatore interpretatore;
    private Verificatore verificatore;
    private Rappresentatore rappresentatore;
    private Importatore importatore;
    private MenuManutentore menuManutentore;
    private MenuFruitore menuFruitore;

    public MenuLogin(Interpretatore interpretatore, Verificatore verificatore, Rappresentatore rappresentatore, Importatore importatore){
        this.menu = new MyMenu(TITOLO, VOCI);
        this.interpretatore = interpretatore;
        this.rappresentatore = rappresentatore;
        this.verificatore = verificatore;
        this.importatore = importatore;
        menuManutentore = new MenuManutentore(interpretatore, verificatore, rappresentatore, menu, importatore);
        menuFruitore = new MenuFruitore(interpretatore, verificatore, rappresentatore, menu);
    }

    /**
     * Presenta il menu di login all'utente di modo che possa accedere come manutentore o come fruitore oppure che possa salvare ed uscire dall'applicazione
     */
    public void avvia(){
ripristinaMenuOriginale(menu, TITOLO, VOCI);

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(SALVA_ED_ESCI);

            switch(sceltaMenu) {
                case 0://Salva ed Esci
                    return;
                case 1: //Manutentore
                    menuManutentore.avvia();
ripristinaMenuOriginale(menu, TITOLO, VOCI);
                    break;
                case 2: //Fruitore
                    menuFruitore.avvia();
ripristinaMenuOriginale(menu, TITOLO, VOCI);
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
