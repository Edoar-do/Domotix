package domotix.view.menus.menuCategorie.sensori;

import domotix.controller.*;
import domotix.view.InputDati;
import domotix.view.MyMenu;
import static domotix.view.ViewUtil.*;
import java.util.List;


/** @author Edoardo Coppola*/
public class MenuCategorieSensoriM {
    private static final String TITOLO = "Menu Categorie Sensori Manutentore ";
    private static final String[] VOCI = {"Aggiungi Categoria Sensore", "Rimuovi Categoria Sensore", "Visualizza Categorie Sensori", "Import Categorie Sensori"};
    private static final String INDIETRO = "Indietro";


    private MyMenu menu;
    private Interpretatore i;
    private Rappresentatore r;
    private Importatore imp;

    public MenuCategorieSensoriM(MyMenu menu, Interpretatore i, Rappresentatore r, Importatore imp) {
        this.menu = menu;
        this.menu.setTitolo(TITOLO);
        this.menu.setVoci(VOCI);
        this.i = i;
        this.r = r;
        this.imp = imp;
    }

    /**
     * Presenta all'utente manutentore un menu che offre la possibilità di scegliere se aggiungere una nuova categoria di sensori, rimuoverne una da quelle
     * esistenti (se ne esiste almeno una), di visualizzare le descrizioni di tutte le categorie di sensori esistenti oppure di importarne da una libreria esterna
     * E' consentito anche tornare indietro e chiudere questo menu.
     * L'aggiunta di una nuova categoria di sensori comporta anche l'aggiunta di almeno un'informazione rilevabile di cui va specificato il nome e se questa è numerica o meno
     */
    public void avvia(){
ripristinaMenuOriginale(menu, TITOLO, VOCI);

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
                    if(i.aggiungiCategoriaSensore(nome, testo)){ //creo la categoria vuota, senza le sue info rilevabili che aggiungo dopo
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
                            if (i.aggiungiInfoRilevabile(nome, info, numerica)) {
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
                        if (i.rimuoviCategoriaSensore(nome))
                            System.out.println(SUCCESSO_RIMOZIONE_CATEGORIA);
                        else
                            System.out.println(ERRORE_RIMOZIONE_CATEGORIA);
                    }
                    break;
                case 3: //visualizza categorie sensori
                    for (String descrizione: r.getDescrizioniCategorieSensori()) {
                        System.out.println(descrizione);
                    }
                    break;
                case 4: //importa categorie sensori
                    System.out.println(INTRO_IMPORT_CAT_SENS);
                   List<String> msgs = imp.importaCategorieSensori();
                   if(msgs == null) System.out.println(IMPORT_FAILED_SENS_CAT);
                   if(msgs.size() > 0) {
                        for (String msg : msgs) { //stampa gli eventuali messaggi di errore oppure il solo messaggio di OK
                            System.out.println(String.format(CATEGORIA_NON_IMPORTATA, msg));
                        }
                   }else System.out.println(IMPORT_CAT_SENS_OK);
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private String premenu(){
        String[] nomiCategorie = r.getNomiCategorieSensori();
        MyMenu m = new MyMenu(CATEGORIE_ESISTENTI_SENSORI, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiCategorie[scelta-1];
    }
}
