package domotix.view.menus.menuPrincipale;

import domotix.controller.Rappresentatore;
import domotix.controller.Verificatore;
import domotix.view.MyMenu;
import domotix.view.menus.menuCategorie.MenuCategorieF;
import domotix.view.menus.menuUnita.MenuUnitaF;

import java.util.ArrayList;
import static domotix.view.menus.ViewConstants.*;

/** @author Edoardo Coppola*/
public class MenuFruitore {
    private static final String TITOLO = "Menu Fruitore ";
    private static final String[] VOCI = {"Menu Categorie Dispositivi Fruitore", "Menu Unita Fruitore", "Visualizza Azioni Programmate"};

    private MyMenu menu;
    private Interpretatore interpretatore;
    private Verificatore verificatore;
    private Rappresentatore rappresentatore;
    private MenuCategorieF menuCategorieF;
    private MenuUnitaF menuUnitaF;

    public MenuFruitore(Interpretatore i, Verificatore v, Rappresentatore r, MyMenu m){
        this.menu = m;
        menu.setTitolo(TITOLO);
        menu.setVoci(VOCI);
        this.interpretatore = i;
        this.verificatore = v;
        this.rappresentatore = r;
        this.menuCategorieF = new MenuCategorieF(rappresentatore, menu);
        this.menuUnitaF = new MenuUnitaF(interpretatore, verificatore, rappresentatore, menu);
    }

    /**
     * Presenta all'utente fruitore un menu che offre la possibilità di aprire un menu per fruitori per la gestione delle categorie di sensori ed
     * attuatori oppure di aprire un menu per fruitori per la gestione dell'unità immobiliare oppure di tornare indietro e chiudere questo menu
     */
    public void avvia(){

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //MenuCategorieDispositivi
                    menuCategorieF.avvia();
                    break;
                case 2: //MenuUnita
                    menuUnitaF.avvia();
                    break;
                case 3: //Visualizza azioni programmate
                    String[] azioni = rappresentatore.getDescrizioniAzioniProgrammate();
                    if (azioni.length > 0) {
                        for (String descrizione : azioni)
                            System.out.println(descrizione);
                    }
                    else {
                        System.out.println(NESSUNA_AZIONE_PROGRAMMATA);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
