package domotix.view.menus.menuCategorie.attuatori;


import domotix.controller.Recuperatore;

import domotix.logicUtil.MyMenu;

/** @author Edoardo Coppola*/
public class MenuCategorieAttuatoriF {
    private static final String TITOLO = "Menu Categorie Attuatori Fruitore ";
    private static final String[] VOCI = { "Visualizza Categorie Attuatori"};
    private static final boolean INDIETRO = true;


    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(){

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza categorie attuatori
                    for (String descrizione: Recuperatore.getDescrizioniCategorieAttuatori()) {
                        System.out.println(descrizione);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
