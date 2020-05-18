package domotix.view.menus.menuCategorie.attuatori;


import domotix.controller.Rappresentatore;
import domotix.controller.Recuperatore;

import domotix.controller.Verificatore;
import domotix.view.MyMenu;

/** @author Edoardo Coppola*/
public class MenuCategorieAttuatoriF {
    private static final String TITOLO = "Menu Categorie Attuatori Fruitore ";
    private static final String[] VOCI = { "Visualizza Categorie Attuatori"};
    private static final String INDIETRO = "Indietro";

    private MyMenu menu;
    private Rappresentatore r;

    public MenuCategorieAttuatoriF(MyMenu menu, Rappresentatore r) {
        this.menu = menu;
        this.menu.setTitolo(TITOLO);
        this.menu.setVoci(VOCI);
        this.r = r;
    }

    /**
     * Prensenta all'utente manutentore un menu che consente di visualizzare tutte le descrizioni delle categorie
     * di attuatori presenti oppure consente di tornare indietro e chiudere questo menu
     */
    public void avvia(){
ripristinaMenuOriginale(menu, TITOLO, VOCI);

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza categorie attuatori
                    for (String descrizione: r.getDescrizioniCategorieAttuatori()) {
                        System.out.println(descrizione);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
