import domotix.model.ElencoCategorieAttuatori;
import domotix.model.ElencoCategorieSensori;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.AccessoDatiSalvati;
import domotix.model.io.datilocali.DatiLocali;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import domotix.logicUtil.*;

public class Domotix {

    static UnitaImmobiliare unita;
    static boolean unitaCreata;

    private static final String SELEZIONE_UTENTE = "Selezione Utente";
    private static final String[] UTENTI = {"Manutentore", "Fruitore"};

    private static final String TITOLO_MANUTENTORE = "Operazioni Manutentore";
    private static final String[] OPERAZIONI_MANUTENTORE = {"Gestione Categoria Dispotivi", "Gestione Unità Immobiliare"};

    private static final String SCELTA_SESNS_ATT = "Scelta tra sensori ed attuatori";
    private static final String[] SENS_ATT = {"Sensori", "Attuatori"};

    private static final String TITOLO_FRUITORE = "Operazioni Fruitore";
    private static final String[] OPERAZIONI_FRUITORE = {"Visualizzazione Categorie Dispositivi", "Visualizzazione Descrizione Unità Immobiliare"};

    private static final String TITOLO_UNITA_IMMOBILIARE = "Unità Immobiliare";
    private static final String[] OPERAZIONI_UNITA_IMMOBILIARE = {"Gestione Unità Immobiliare", "Gestione Stanze"};

    private static final String PRESENTAZIONE_STANZE = "\n Elenco delle stanze presenti nell'unità immobiliare: ";

    private static final String INSERIMENTO_INDICE_STANZA = "Inserisci L'indice della stanza su cui vuoi andare ad operare";
    private static final String INDICE_STANZA_NON_CORRETTO = "L'indice della stanza specificato non è presente";

    private static final String TITOLO_OPERAZIONI_SU_STANZA = "Operazioni effettuabili su una stanza: ";
    private static final String[] OPERAZIONI_SU_STANZA = {"Visualizza descrizione stanza", "Aggiungi un sensore", "Aggiungi un attuatore",
                                                            "Aggiungi un artefatto", "Rimuovi un sensore", "Rimuovi un attuatore",
                                                            "Rimuovi un artefatto", "Gestione artefatto"};

    private static final String OPERATION_NOT_POSSIBLE_DUE_TO_UNIT_INEXISTENCE = "Operazione non effettuabile al momento. Devi prima creare un'unità immobiliare";
    private  static final String UNITA_GIA_CREATA = "Unità Immobiliare già creata. Impossibile crearne un'altra."

    private static void showMenuUtente(){

        MyMenu selezioneUtente = new MyMenu(SELEZIONE_UTENTE, UTENTI);
        int sceltaUtente = 0;

        do{
            sceltaUtente = selezioneUtente.scegli();
            switch (sceltaUtente){
                case 0:
                    break;
                case 1: //manutentore menu
                    showMenuManutentore();
                    break;
                case 2: //fruitore menu
                    showMenuFruitore();
                    break;
            }
        }while(sceltaUtente != 0);
    } //manutentore o fruitore

    private static void showMenuManutentore(){ //gestione categorie dispositivi o gestione unità immobiliare

        MyMenu menuManutentore = new MyMenu(TITOLO_MANUTENTORE, OPERAZIONI_MANUTENTORE);
        int sceltasceltaGestione = 0;

        do{
            sceltasceltaGestione = menuManutentore.scegli();
            switch(sceltasceltaGestione){
                case 0:
                    break;
                case 1: //gestione categoria dispositivi
                    showMenuCategorieDispositivi();
                    break;
                case 2: //gestione unità immobiliare
                    showMenuUnitaImmobiliare();
                    break;
            }
        }while(sceltasceltaGestione != 0);
    } //gestione categoria o gestione unità
    
    private static void showMenuUnitaImmobiliare(){
        MyMenu menuUnitaImmobiliare = new MyMenu(TITOLO_UNITA_IMMOBILIARE, OPERAZIONI_UNITA_IMMOBILIARE);
        int sceltaGestione = 0;

        do{
            sceltaGestione = menuUnitaImmobiliare.scegli();
            switch(sceltaGestione){
                case 0:
                    break;
                case 1: //gestione unità immobiliare
                    showMenuGestioneUnita();
                    break;
                case 2: //gestione stanze
                    if(unitaCreata == true) {
                        showMenuGestioneStanze();
                    }else{
                        System.out.println(OPERATION_NOT_POSSIBLE_DUE_TO_UNIT_INEXISTENCE);
                    }
                    break;
            }
        }while(sceltaGestione != 0);
    } //operazioni effettuabili su un'unità immobiliare: lavoro sull'unità o sulle stanze al suo interno

    private static void showMenuGestioneStanze() { //TODO finire!!!!!!!!!
        //presentazione delle stanze presenti nell'unità con i loro nomi e scelta di una tra esse per andare a lavorarci su
        //in seguito presentazione del menu delle operazioni che si possono compiere sulla stanza fissata

        System.out.println(PRESENTAZIONE_STANZE + unita.getNome());

        Stanza[] stanze = unita.getStanze();
        for (int i=0; i <= stanze.length; i++) { System.out.println(i + " " + stanze[i].getNome()); }

        int indiceStanza = InputDati.leggiIntero(INSERIMENTO_INDICE_STANZA, 0, stanze.length);

        Stanza stanza = stanze[indiceStanza];
        MyMenu menuOperazioniSuUnaStanza = new MyMenu(TITOLO_OPERAZIONI_SU_STANZA, OPERAZIONI_SU_STANZA);
        int sceltaOperazioneSuStanza = 0;
        do{
            sceltaOperazioneSuStanza = menuOperazioniSuUnaStanza.scegli();
            switch(sceltaOperazioneSuStanza) {
                case 0:
                    break;
                case 1: //Visualizza descrizione stanza
                    System.out.println(stanza.toString());
                    break;
                case 2: //aggiungi sensore alla stanza
                    aggiuntaSensore(InputDati.le);
                    break;
                case 3: //aggiungi attuatore alla stanza
                    aggiuntaAttuatore();
                    break;
                case 4: //aggiungi artefatto alla stanza
                    aggiuntaArtefatto();
                    break;
                case 5: //rimuovi sensore dalla stanza
                    rimooviSensore();
                    break;
                case 6: //rimuovi attuatore dalla stanza
                    rimuoviAttuatore();
                    break;
                case 7: //rimuovi artefatto dalla stanza
                    rimuoviArtefatto();
                    break;
                case 8: //gestione artefatto
                    break;
            }
        }while(sceltaOperazioneSuStanza != 0);
    }

    private static boolean aggiuntaSensore(){

    }

    private static void showMenuGestioneUnita(){ //TODO METODI PER LA VARIE OPERAZIONI EFFETTUABILI SU UN'UNITA'
        MyMenu menuGestioneUnita = new MyMenu(TITOLO_UNITA_IMMOBILIARE, OPERAZIONI_UNITA_IMMOBILIARE);
        int sceltaGestioneUnita = 0;

        do{
            sceltaGestioneUnita = menuGestioneUnita.scegli();
            switch(sceltaGestioneUnita){
                case 0:
                    break;
                case 1: //visualizzazione descrizione unità immobiliare
                    if(unitaCreata == true) {
                        visualizzaDescrizioneUnita(); //todo toString() dell'unita???
                    }else{
                        System.out.println(OPERATION_NOT_POSSIBLE_DUE_TO_UNIT_INEXISTENCE);
                    }
                    break;
                case 2: //inizializzazione unita: costruzione dell'oggetto unita
                    if(unitaCreata == false){
                        inizializzaUnita(); //todo creazione dell'oggetto unità immobiliare + impostare flag
                        unitaCreata = true;
                    }else{
                        System.out.println(UNITA_GIA_CREATA);
                    }
                    break;
                case 3: //aggiunta stanza all'unita se GIA' presente l'unità
                    if(unitaCreata == true) {
                        addStanza();
                    }else{
                        System.out.println(OPERATION_NOT_POSSIBLE_DUE_TO_UNIT_INEXISTENCE);
                    }
                    break;
                case 4: //rimozione stanza all'unita se GIA' presente l'unità
                    if(unitaCreata == true) {
                        removeStanza();
                    }else{
                        System.out.println(OPERATION_NOT_POSSIBLE_DUE_TO_UNIT_INEXISTENCE);
                    }
                    break;
            }
        }while(sceltaGestioneUnita != 0);
    }

    private static void showMenuCategorieDispositivi(){
        MyMenu menusceltaCategoriaDisp = new MyMenu(SCELTA_SESNS_ATT, SENS_ATT);
        int sceltaCategoriaDisp = 0;

        do{
            sceltaCategoriaDisp = menusceltaCategoriaDisp.scegli();
            switch(sceltaCategoriaDisp){
                case 0:
                    break;
                case 1: //categoria sensori
                    showMenuOperazioniCategoriaSensori();
                    break;
                case 2: //categoria attuatori
                    showMenuOperazioniCategoriaAttuatori();
                    break;
            }
        }while(sceltaCategoriaDisp != 0);
    } //SENSORE O ATTUATORE

    private static void showMenuOperazioniCategoriaSensori(){ //TODO
        //mostrare le categorie di sensori presenti nell'elenco delle categoria disponibili
        //in seguito mostrare le operazioni effettuabili per una categoria di sensori
    }

    private static void showMenuOperazioniCategoriaAttuatori(){ //TODO
        //mostrare le categorie di attuatori presenti nell'elenco delle categoria disponibili
        //in seguito mostrare le operazioni effettuabili per una categoria di attuatori
    }

    // FINE DELLA ROBA DEL MANUTENTORE

    private static void showMenuFruitore(){
        MyMenu menuFruitore = new MyMenu(TITOLO_FRUITORE, OPERAZIONI_FRUITORE);
        int sceltaOperazioniFruitore = 0;

        do{
            sceltaOperazioniFruitore = menuFruitore.scegli();
            switch(sceltaOperazioniFruitore){
                case 0:
                    break;
                case 1: //gestione categoria dispositivi
                    //TODO
                    break;
                case 2: //gestione unità immobiliare
                    //TODO
                    break;
            }
        }while(sceltaOperazioniFruitore != 0);
    }



    public static void main(String ...args) {

        unitaCreata = false;
        unita = new UnitaImmobiliare("CasaMia");

       
    }
}
