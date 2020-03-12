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
    private static final String IS_PARAMATRICA = "La modalità operativa %s è parametrica? ";
    private static final String INSERIMENTO_NOME_PARAMETRO = "Inserisci un nome per il parametro della modalità operativa parametrica (inserire " + TERMINATORE + " per terminare)";
    private static final String SUCCESSO_INSERIMENTO_PARAMETRO = "Parametro inserito con successo";
    private static final String ERRORE_ALMENO_UN_PARAMETRO = "Almeno un parametro deve essere inserito nella modalita' parametrica";
    private static final String ERRORE_INSERIMENTO_PARAMETRO = "L'inserimento del parametro è fallito. " + GUIDA_IN_LINEA;
    private static final String INSERIMENTO_VALORE_PARAMETRO = "Inserisci il valore desiderato per il parametro %s : ";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    /**
     * Prensenta all'utente manutentore un menu che consente di aggiungere un nuova categoria di attuatori, rimuoverne una (se presente) oppure di
     * visualizzare tutte le descrizioni delle categorie di attuatori presenti oppure consente di tornare indietro e chiudere questo menu.
     * L'aggiunta di una categoria comporta l'inserimento di almeno una modalità operativa la quale può essere parametrica o meno. Se sì
     * è richiesto almeno un parametro per essa
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
                    //creo la categoria vuota e la popolo poi con le modalità
                    if(Modificatore.aggiungiCategoriaAttuatore(nome, InputDati.leggiStringaNonVuota(INSERIMENTO_TESTO_LIBERO)))
                        System.out.println(INSERIMENTO_SUCCESSO);
                    else {
                        System.out.println(ERRORE_INSERIMENTO);
                        break;
                    }

                    boolean parametrica, almenoUnParametro = false, modalitaDefault = false;
                    String nomeModalita, parametro;

                    //ciclo di popolamento della categoria vuota con le sue modalità
                    while (true) {
                        nomeModalita = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_MODALITA_OPERATIVA);
                        if (nomeModalita.equals(TERMINATORE)) {
                            if (modalitaDefault)
                                break;
                            System.out.println(ERRORE_MODALITA_DEFAULT_MANCANTE);
                        }else{
                            //creo la modalità vuota e poi aggiungo eventualmente i parametri
                            if (Modificatore.aggiungiModalitaCategoriaAttuatore(nome, nomeModalita)) {
                                modalitaDefault = true;
                                System.out.println(INSERIMENTO_SUCCESSO_MODALITA);
                            } else {
                                System.out.println(ERRORE_INSERIMENTO_MODALITA);
                                continue;
                            }

                            //aggiungo i parametri eventualmente
                            parametrica = InputDati.yesOrNo(String.format(IS_PARAMATRICA, nomeModalita));
                            if(parametrica){
                                //se modalità parametrica -> inserimento nomi parametri
                                while(true){
                                    parametro = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_PARAMETRO);
                                    if(parametro.equals(TERMINATORE)){
                                        if(almenoUnParametro)
                                            break;
                                        System.out.println(ERRORE_ALMENO_UN_PARAMETRO);
                                    }else{
                                        if(Modificatore.aggiungiParametro(nome, nomeModalita, parametro, InputDati.leggiDouble(String.format(INSERIMENTO_VALORE_PARAMETRO, parametro)))){
                                            System.out.println(SUCCESSO_INSERIMENTO_PARAMETRO);
                                            almenoUnParametro = true;
                                        }else
                                            System.out.println(ERRORE_INSERIMENTO_PARAMETRO);
                                    }
                                }
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
