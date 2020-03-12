package domotix.view.menus.menuCategorie.sensori;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.view.InputDati;
import domotix.view.MyMenu;


/** @author Edoardo Coppola*/
public class MenuCategorieSensoriM {
    private static final String TITOLO = "Menu Categorie Sensori Manutentore ";
    private static final String[] VOCI = {"Aggiungi Categoria Sensore", "Rimuovi Categoria Sensore", "Visualizza Categorie Sensori"};
    private static final String INDIETRO = "Indietro";

    private static final String INSERIMENTO_CATEGORIA_SENSORE = "Inserisci un nome per la nuova categoria di sensore";
    private static final String INSERIMENTO_TESTO_LIBERO = "Inserisci un testo libero di descrizione del sensore";
    private static final String CATEGORIE_ESISTENTI_SENSORI = "Elenco delle categorie di sensori esistenti: ";
    private static final String ERRORE_INSERIMENTO = "Inserimento della categoria fallito. Consultare la guida in linea per maggiori informazioni " ;
    private static final String SUCCESSO_INSERIMENTO = "Inserimento della categoria avvenuto con successo";
    private static final String SUCCESSO_RIMOZIONE_CATEGORIA = "Rimozione categoria avvenuta con successo";
    private static final String ERRORE_RIMOZIONE_CATEGORIA = "Rimozione delle categoria fallita. Consultare la guida in linea per maggiori informazioni";
    private static final String TERMINATORE = "-q";
    private static final String SUCCESSO_INSERIMENTO_INFO = "L'informazione rilevabile è stata inserita con successo";
    private static final String ERRORE_INSERIMENTO_INFO = "L'inserimento dell'informazione rilevabile è fallito. Consultare la guida in linea per maggiori informazioni" ;
    private static final String INSERIMENTO_NOME_INFO = "Inserisci il nome dell'informazione rilevabile (inserire " + TERMINATORE + " per terminare) ";
    private static final String ALMENO_UNA_INFO = "Deve essere presente almeno un'informazione rilevabile per la categoria di sensori creata!";
    private static final String IS_NUMERICA = "L'informazione %s è numerica? ";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    /**
     * Presenta all'utente manutentore un menu che offre la possibilità di scegliere se aggiungere una nuova categoria di sensori, rimuoverne una da quelle
     * esistenti (se ne esiste almeno una), di visualizzare le descrizioni di tutte le categorie di sensori esistenti oppure di tornare indietro e chiudere
     * questo menu.
     * L'aggiunta di una nuova categoria di sensori comporta anche l'aggiunta di almeno un'informazione rilevabile di cui va specificato il nome e se questa è numerica o meno
     */
    public static void avvia(){

        int sceltaMenu = 0;
        String nome, info, testo;
        boolean numerica, almenoUna = false;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: // aggiungi categoria sensori
                    nome = InputDati.leggiStringaNonVuota(INSERIMENTO_CATEGORIA_SENSORE);
                    testo = InputDati.leggiStringaNonVuota(INSERIMENTO_TESTO_LIBERO);
                    if(Modificatore.aggiungiCategoriaSensore(nome, testo)){ //creo la categoria vuota, senza le sue info rilevabili che aggiungo dopo
                        System.out.println(SUCCESSO_INSERIMENTO);
                    }else {
                        System.out.println(ERRORE_INSERIMENTO);
                        break;
                    }
                    //ciclo di inserimento delle info rilevabili. Mano mano si popola la categoria sensore della sue info rilevabili
                    while(true){
                        info = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_INFO);
                        if(info.equals(TERMINATORE)){
                            if(almenoUna)
                                break;
                            System.out.println(ALMENO_UNA_INFO);
                        }else {
                            numerica = InputDati.yesOrNo(String.format(IS_NUMERICA, info));
                            if (Modificatore.aggiungiInfoRilevabile(nome, info, numerica)) {
                                System.out.println(SUCCESSO_INSERIMENTO_INFO);
                                almenoUna = true;
                            } else
                                System.out.println(ERRORE_INSERIMENTO_INFO);
                        }
                    }
                     break;
                case 2: //rimuovi categoria sensori
                    nome = premenu();
                    if (nome != null) {
                        if (Modificatore.rimuoviCategoriaSensore(nome))
                            System.out.println(SUCCESSO_RIMOZIONE_CATEGORIA);
                        else
                            System.out.println(ERRORE_RIMOZIONE_CATEGORIA);
                    }
                    break;
                case 3: //visualizza categorie sensori
                    for (String descrizione: Recuperatore.getDescrizioniCategorieSensori()) {
                        System.out.println(descrizione);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private static String premenu(){
        String[] nomiCategorie = Recuperatore.getNomiCategorieSensori();
        MyMenu m = new MyMenu(CATEGORIE_ESISTENTI_SENSORI, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiCategorie[scelta-1];
    }
}
