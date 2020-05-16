package domotix.view.menus.menuPrincipale;

import domotix.controller.Importatore;
import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.controller.Verificatore;
import domotix.view.InputDati;
import domotix.view.MyMenu;
import domotix.view.menus.menuCategorie.MenuCategorieM;
import domotix.view.menus.menuUnita.MenuUnitaM;
import static domotix.view.ViewConstants.*;

import java.util.ArrayList;

/** @author Edoardo Coppola*/
public class MenuManutentore {
    private static final String TITOLO = "Menu Manutentore ";
    private static final String[] VOCI = {"Menu Categorie Dispositivi Manutentore", "Menu Unita Manutentore", "Aggiungi un'unità immobiliare", "Rimuovi un'unità immobiliare",
            "Importa Unita' Immobiliari"};

    private MyMenu menu;
    private Interpretatore interpretatore;
    private Rappresentatore rappresentatore;
    private Verificatore verificatore;
    private Importatore importatore;
    private MenuUnitaM menuUnitaM;
    private MenuCategorieM menuCategorieM;

    public MenuManutentore(Interpretatore i, Verificatore v, Rappresentatore r, MyMenu m, Importatore imp){
        this.menu = m;
        menu.setTitolo(TITOLO);
        menu.setVoci(VOCI);
        this.interpretatore = i;
        this.verificatore = v;
        this.rappresentatore = r;
        this.importatore = imp;
        menuUnitaM = new MenuUnitaM(interpretatore, verificatore, rappresentatore, menu);
        menuCategorieM = new MenuCategorieM(interpretatore, rappresentatore, importatore, menu);
    }

    /**
     * Presenta all'utente manutentore un menu che offre la possibilità di aprire un menu per menutentori per la gestione delle categorie di
     * sensori e attuatori, di aprire un menu per la gestione dell'unità immobiliare, di aggiungere o rimuovere un'unità immobiliare oppure di importarne una dalla libreria esterna
     * E' consentito anche tornare indietro ed uscire da questo menu
     */
    public void avvia(){

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //Menu Categorie Dispositivi Manutentore
                    menuCategorieM.avvia();
                    break;
                case 2: //Menu Unita Manutentore
                    menuUnitaM.avvia();
                    break;
                case 3: //aggiungi unità
                    if(interpretatore.aggiungiUnitaImmobiliare(InputDati.leggiStringaNonVuota(NOME_NUOVA_UNITA)))
                        System.out.println(SUCCESSO_INSERIMENTO_UNITA);
                    else
                        System.out.println(ERRORE_INSERIMENTO_UNITA);
                    break;
                case 4: //rimuovi unità
                    String nomeUnitaDaRimuovere = premenuUnita();
                    if(nomeUnitaDaRimuovere != null) { //scelto di tornare indietro
                        if (interpretatore.rimuoviUnitaImmobiliare(nomeUnitaDaRimuovere))
                            System.out.println(SUCCESSO_RIMOZIONE_UNITA);
                        else
                            System.out.println(ERRORE_RIMOZIONE_UNITA);
                    }
                    break;
                case 5: //importazione unità immobiliari
                    System.out.println(INTRO_IMPORT_UNITA);
                    ArrayList<String> msgs = (ArrayList<String>) importatore.importaUnitaImmobiliari();
                    if(msgs == null) System.out.println(IMPORT_FAILED);
                    if(msgs.size() > 0) {
                        for (String msg : msgs) { //stampa gli eventuali messaggi di errore oppure il solo messaggio di OK
                            System.out.println(String.format(UNITA_NON_IMPORTATA, msg));
                        }
                    }else System.out.println(IMPORT_UNITA_OK);
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private String premenuUnita(){
        String[] nomiUnita = rappresentatore.getNomiUnita();
        MyMenu m = new MyMenu(NOMI_UNITA_IMMOBILIARI_ESISTENTI, nomiUnita);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiUnita[scelta-1];
    }
}
