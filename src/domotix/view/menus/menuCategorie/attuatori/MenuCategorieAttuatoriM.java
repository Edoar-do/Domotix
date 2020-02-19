package domotix.view.menus.menuCategorie.attuatori;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.controller.Verificatore;
import domotix.logicUtil.InputDati;
import domotix.logicUtil.MyMenu;
import domotix.model.bean.device.CategoriaAttuatore;

/** @author Edoardo Coppola*/
public class MenuCategorieAttuatoriM {
    private static final String TITOLO = "Menu Categorie Attuatori Manutentore ";
    private static final String[] VOCI = {"Aggiungi Categoria Attuatore", "Rimuovi Categoria Attuatore", "Visualizza Categorie Attuatori"};
    private static final boolean INDIETRO = true;

    private static final String INSERIMENTO_CATEGORIA_ATTUATORE = "Inserisci un nome per la nuova categoria di attuatore";
    private static final String INSERIMENTO_TESTO_LIBERO = "Inserisci un testo libero di descrizione del attuatore";
    private static final String CATEGORIE_ESISTENTI_ATTUATORI = "Elenco delle categorie di sensori esistenti: ";
    private static final String INVALID_NAME = "Il nome della categoria inserita non è valido. Consultare la guida in linea per maggiori informazioni";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(){

        int sceltaMenu = 0;
        String nome;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: // aggiungi categoria attuatori
                    while(true){
                        nome = InputDati.leggiStringaNonVuota(INSERIMENTO_CATEGORIA_ATTUATORE);
                        if(Verificatore.checkValiditaCategoriaAttuatore(nome)){
                            Modificatore.aggiungiCategoriaAttuatore(new CategoriaAttuatore(nome,InputDati.leggiStringaNonVuota(INSERIMENTO_TESTO_LIBERO)));
                            break;
                        }else{
                            System.out.println(INVALID_NAME);
                        }
                    }
                    //TODO GESTIRE INSERIMENTO DELLE MODALITA OPERATIVE

                    break;
                case 2: //rimuovi categoria attuatori
                    String nomeCategoriaDaRimuovere = premenu();
                    Modificatore.rimuoviCategoriaAttuatore(nomeCategoriaDaRimuovere);
                    break;
                case 3: //visualizza categorie attuatori
                    for (String descrizione: Recuperatore.getDescrizioniCategorieAttuatori()) {
                        System.out.println(descrizione);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private static String premenu(){
        String[] nomiCategorie = Recuperatore.getNomiCategorieAttuatori();
        MyMenu m = new MyMenu(CATEGORIE_ESISTENTI_ATTUATORI, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return nomiCategorie[scelta-1];
    }
}
