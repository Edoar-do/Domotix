package domotix.view.menus.menuCategorie.attuatori;

import domotix.controller.*;
import domotix.view.InputDati;
import domotix.view.MyMenu;

import java.util.ArrayList;
import static domotix.view.ViewUtil.*;

/** @author Edoardo Coppola*/
public class MenuCategorieAttuatoriM {
    private static final String TITOLO = "Menu Categorie Attuatori Manutentore ";
    private static final String[] VOCI = {"Aggiungi Categoria Attuatore", "Rimuovi Categoria Attuatore", "Visualizza Categorie Attuatori", "Importa Categorie Attuatori"};
    private static final String INDIETRO = "Indietro";

    private MyMenu menu;
    private Interpretatore i;
    private Rappresentatore r;
    private Importatore imp;

    public MenuCategorieAttuatoriM(MyMenu menu, Interpretatore i, Rappresentatore r, Importatore imp) {
        this.menu = menu;
        this.menu.setTitolo(TITOLO);
        this.menu.setVoci(VOCI);
        this.i = i;
        this.r = r;
        this.imp = imp;
    }

    /**
     * Prensenta all'utente manutentore un menu che consente di aggiungere un nuova categoria di attuatori, rimuoverne una (se presente) oppure di
     * visualizzare tutte le descrizioni delle categorie di attuatori presenti oppure importarne da una libreria esterna
     * E' consentito anche tornare indietro e chiudere questo menu.
     * L'aggiunta di una categoria comporta l'inserimento di almeno una modalità operativa la quale può essere parametrica o meno. Se sì
     * è richiesto almeno un parametro per essa
     */
    public void avvia(){
ripristinaMenuOriginale(menu, TITOLO, VOCI);

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
                    if(i.aggiungiCategoriaAttuatore(nome, InputDati.leggiStringaNonVuota(INSERIMENTO_TESTO_LIBERO_ATT)))
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
                            if (i.aggiungiModalitaCategoriaAttuatore(nome, nomeModalita)) {
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
                                        if(i.aggiungiParametro(nome, nomeModalita, parametro, InputDati.leggiDouble(String.format(INSERIMENTO_VALORE_PARAMETRO, parametro)))){
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
                        if (i.rimuoviCategoriaAttuatore(nome))
                            System.out.println(SUCCESSO_RIMOZIONE_CATEGORIA);
                        else
                            System.out.println(ERRORE_RIMOZIONE_CATEGORIA);
                    }
                    break;
                case 3: //visualizza categorie attuatori
                    for (String descrizione: r.getDescrizioniCategorieAttuatori()) {
                        System.out.println(descrizione);
                    }
                    break;
                case 4: //importa categorie attuatori
                    System.out.println(INTRO_IMPORT_CAT_ATT);
                    ArrayList<String> msgs = (ArrayList<String>) imp.importaCategorieAttuatori();
                    if(msgs == null) System.out.println(IMPORT_FAILED_ATT_CAT);
                    if(msgs.size() > 0) {
                        for (String msg : msgs) { //stampa gli eventuali messaggi di errore oppure il solo messaggio di OK
                            System.out.println(String.format(CATEGORIA_NON_IMPORTATA, msg));
                        }
                    }else System.out.println(IMPORT_CAT_ATT_OK);
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private String premenu(){
        String[] nomiCategorie = r.getNomiCategorieAttuatori();
        MyMenu m = new MyMenu(CATEGORIE_ESISTENTI_ATTUATORI, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiCategorie[scelta-1];
    }
}
