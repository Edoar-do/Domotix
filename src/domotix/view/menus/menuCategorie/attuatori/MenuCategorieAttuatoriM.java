package domotix.view.menus.menuCategorie.attuatori;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.view.InputDati;
import domotix.view.MyMenu;

/** @author Edoardo Coppola*/
public class MenuCategorieAttuatoriM {
    private static final String TITOLO = "Menu Categorie Attuatori Manutentore ";
    private static final String[] VOCI = {"Aggiungi Categoria Attuatore", "Rimuovi Categoria Attuatore", "Visualizza Categorie Attuatori"};
    private static final String INDIETRO = "Indietro";

    private static final String INSERIMENTO_CATEGORIA_ATTUATORE = "Inserisci un nome per la nuova categoria di attuatore";
    private static final String INSERIMENTO_TESTO_LIBERO = "Inserisci un testo libero di descrizione del attuatore";
    private static final String CATEGORIE_ESISTENTI_ATTUATORI = "Elenco delle categorie di sensori esistenti: ";
    private static final String GUIDA_IN_LINEA = "Consultare la guida in linea per maggiori informazioni";
    private static final String ERRORE_INSERIMENTO_MODALITA = "Inserimento della modalità fallito. " + GUIDA_IN_LINEA;
    private static final String ERRORE_INSERIMENTO = "Inserimento della categori fallito. " + GUIDA_IN_LINEA;
    private static final String TERMINATORE = "-q";
    private static final String INSERIMENTO_NOME_MODALITA_OPERATIVA = "Inserisci un nome per la nuova modalita' operativa (inserire " + TERMINATORE + " per terminare)";
    private static final String INSERIMENTO_SUCCESSO = "Inserimento della categoria avvenuto con successo";
    private static final String INSERIMENTO_SUCCESSO_MODALITA = "Inserimento della modalità avvenuta con successo";
    private static final String ERRORE_RIMOZIONE_CATEGORIA = "Rimozione della categoria fallita. " + GUIDA_IN_LINEA;
    private static final String SUCCESSO_RIMOZIONE_CATEGORIA = "Rimozione della categoria avvenuta con successo";
    private static final String ERRORE_MODALITA_DEFAULT_MANCANTE = "Inserire almeno la modalita' di default!";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    /**
     * Prensenta all'utente manutentore un menu che consente di aggiungere un nuova categoria di attuatori, rimuoverne una (se presente) oppure di
     * visualizzare tutte le descrizioni delle categorie di attuatori presenti oppure consente di tornare indietro e chiudere questo menu
     */
    public static void avvia(){

        int sceltaMenu = 0;
        String nome;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: // aggiungi categoria attuatori
                    nome = InputDati.leggiStringaNonVuota(INSERIMENTO_CATEGORIA_ATTUATORE);
                    if(Modificatore.aggiungiCategoriaAttuatore(nome, InputDati.leggiStringaNonVuota(INSERIMENTO_TESTO_LIBERO)))
                        System.out.println(INSERIMENTO_SUCCESSO);
                    else {
                        System.out.println(ERRORE_INSERIMENTO);
                        break;
                    }
                    boolean modalitaDefault = false;
                    while (true) {
                        String nomeModalita = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_MODALITA_OPERATIVA);
                        if (nomeModalita.equals(TERMINATORE)) {
                            if (modalitaDefault)
                                break;
                            System.out.println(ERRORE_MODALITA_DEFAULT_MANCANTE);
                        }
                        else {
                            if (Modificatore.aggiungiModalitaCategoriaAttuatore(nome, nomeModalita)) {
                                modalitaDefault = true;
                                System.out.println(INSERIMENTO_SUCCESSO_MODALITA);
                            } else {
                                System.out.println(ERRORE_INSERIMENTO_MODALITA);
                            }
                        }
                    }
                    break;
                case 2: //rimuovi categoria attuatori
                    nome = premenu();
                    if (nome != null) {
                        if (Modificatore.rimuoviCategoriaAttuatore(nome))
                            System.out.println(SUCCESSO_RIMOZIONE_CATEGORIA);
                        else
                            System.out.println(ERRORE_RIMOZIONE_CATEGORIA);
                    }
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
        return scelta == 0 ? null : nomiCategorie[scelta-1];
    }
}
