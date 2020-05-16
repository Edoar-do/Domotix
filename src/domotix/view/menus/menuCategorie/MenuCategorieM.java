package domotix.view.menus.menuCategorie;

import domotix.controller.Importatore;
import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.view.MyMenu;
import domotix.view.menus.menuCategorie.attuatori.MenuCategorieAttuatoriM;
import domotix.view.menus.menuCategorie.sensori.MenuCategorieSensoriM;

/** @author Edoardo Coppola*/
public class MenuCategorieM {
    private static final String TITOLO = "Menu Categorie Manutentore ";
    private static final String[] VOCI = {"Menu Categorie Sensori Manutentore", "Menu Categorie Attuatori Manutentore"};
    private static final String INDIETRO = "Indietro";

   private MyMenu menu;
   private Interpretatore i;
   private Rappresentatore r;
   private Importatore imp;
   private MenuCategorieSensoriM menuCategorieSensoriM;
   private MenuCategorieAttuatoriM menuCategorieAttuatoriM;

   public MenuCategorieM(Interpretatore i, Rappresentatore r, Importatore imp, MyMenu m){
       this.menu = m;
       menu.setTitolo(TITOLO);
       menu.setVoci(VOCI);
       this.i = i;
       this.imp = imp;
       this.r = r;
       menuCategorieAttuatoriM = new MenuCategorieAttuatoriM(menu, i, r, imp);
       menuCategorieSensoriM = new MenuCategorieSensoriM(menu, i, r, imp);
   }

    /**
     * Prensenta all'utente manutentore un menu che consente di aprire un menu per la gestione delle categorie di sensori
     * oppure un menu per la gestione delle categorie di attuatori oppure consente di tornare indietro e chiudere questo menu
     */
    public void avvia(){

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //Menu categorie sensori manutentore
                    menuCategorieSensoriM.avvia();
                    break;
                case 2: //Menu categorie attuatori manutentori
                    menuCategorieAttuatoriM.avvia();
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
