package domotix.view.menus.menuUnita;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.view.InputDati;
import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import domotix.view.menus.menuUnita.gestioneStanza.MenuGestioneStanzaF;
import domotix.view.menus.menuUnita.gestioneUnita.MenuGestioneUnitaF;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/** @author Edoardo Coppola*/
public class MenuUnitaF {
    private static final String TITOLO = "Menu Unita Fruitore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Menu Gestione Unita Fruitore", "Menu Gestione Stanza Fruitore", "Crea una nuova regola", "Rimuovi una regola", "Visualizza Regole"};
    private static final String INDIETRO = "Indietro";
    private static final String UNITA_IMMOBILIARI_ESISTENTI = "Unità Immobiliari: ";
    private static final String NONE = "Nessuna unità immobiliare esistente. L'utente manutentore deve prima crearne una";
    private static final String GUIDA_IN_LINEA = " Consultare la guida in linea per maggiori informazioni";
    private static final String SUCCESSO_INSERIMENTO_REGOLA = "Regola id: %s inserita con successo";
    private static final String SUCCESSO_INSERIMENTO_COMPONENTE = "Condizione componente l'antecedente inserita con successo";
    private static final String SUCCESSO_RIMOZIONE_REGOLA = "Regola rimossa correttamente";
    private static final String ERRORE_INSERIMENTO_REGOLA = "Inserimento regola fallito." + GUIDA_IN_LINEA;
    private static final String ERRORE_INSERIMENTO_COMPONENTE_ANTECEDENTE = "Inserimento della condizione componente l'antecedente fallita." + GUIDA_IN_LINEA;
    private static final String ERRORE_INSERIMENTO_OP_LOGICO = "Errore nell'inserimento dell'operatore logico." + GUIDA_IN_LINEA;
    private static final String ERRORE_RIMOZIONE_REGOLA = "Rimozione della regola fallita." + GUIDA_IN_LINEA;
    private static final String SCELTA_LHS = "Scegli la variabile sensoriale da valutare: ";
    private static final String SCELTA_RELOP = "Scegli un operatore relazionale (se l'informazione rilevata dal sensore è scalare la scelta sara' automatica)";
    private static final String SCELTA_RHS = "Scegli la seconda variabile sensoriale da confrontare oppure inserisci una costante";
    private static final String ANTECEDENTE_SI_NO = "Desideri inserire un'antecedente? (Se scegli no verrà assegnato il valore TRUE di default)";
    private static final String RELOP_NECESSARIO = "Operatore relazionale necessario";
    private static final String LHS_NECESSARIO = "Variabile sensoriale necessaria!";
    private static final String RHS_NECESSARIO = "Variabile sensoriale o costante necessaria";
    private static final String RHS_IS_COSTANTE = "Vuoi inserire un valore costante numerico per completare la condizione? ";
    private static final String INSERIMENTO_COSTANTE_RHS = "Inserisci un valore numerico: ";
    private static final String CONTINUARE_CON_ANTECEDENTE = "Desideri continuare con la costruzione dell'antecedente? ";
    private static final String LOGIC_OP_NECESSARIO = "Operatore logico necessario!";
    private static final String ZERO_SENSORI = "Impossibile creare una nuova regola perche' non sono presenti sensori nell'unita'. Il manutentore deve prima crearne";
    private static final String INFO_DEL_SENSORE = "Informazione rilevabili dal sensore %s: ";
    private static final String SENSORI_UNITA = "Sensori presenti nell'unita' %s: ";
    private static final String[] OPERATORI_LOGICI = {"AND", "OR"};
    private static final String[] OPERATORI_RELAZIONALI = {"<", ">", "<=", ">=", "="};
    private static final String REGOLE_UNITA = "Elenco delle regole nell'unita %s";
    private static final String SUCCESSO_INSERIMENTO_AZIONE = "Azione del conseguente inserita con successo";
    private static final String ERRORE_INSERIMENTO_AZIONE = "Inserimento azione del conseguente fallito. " + GUIDA_IN_LINEA;
    private static final String ZERO_ATTUATORI = "Impossibile creare una nuova regola perché non sono presenti attuatori nell'unita'. Il manutentore deve prima crearne";
    private static final String CONTINUARE_CON_CONSEGUENTE = "Desideri continuare con la costruzione del conseguente? ";
    private static final String ALMENO_UNA_AZIONE = "E' necessario inserire almeno un'azione per il conseguente!";
    private static final String LHS_ATT_NECESSARIO = "E' necessario specificare l'attuatore per un'azione!";
    private static final String RHS_ATT_NECESSARIO = "E' necessario specificare una modalita' da assegnare all'attuatore!";
    private static final String ELENCO_MODALITA_ATTUATORE = "Elenco di tutte le modalità dell'attuatore: ";
    private static final String STRINGA_COST = "Vuoi inserire una stringa costante come secondo termine della condizione? (se no allora sceglierai una variabile sensoriale): ";
    private static final String INSERIMENTO_STRINGA_COSTANTE_RHS = "Inserisci una stringa costante come secondo termine della condizione: ";
    private static final String ATTUATORI_UNITA = "Attuatori presenti nell'unita'%s: ";
    private static final String SUCCESSO_INSERIMENTO_LHS = "Lhs della condizione inserito con successo";
    private static final String SUCCESSO_INSERIMENTO_REL_OP= "Operatore relazionale della condizione inserito con successo";
    private static final String COSTRUZIONE_CONSEGUENTE = "Costruisci il conseguente inserendone le azioni: ";


    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    /**
     * Presenta all'utente fruitore un menu che offre la possibilità di aprire un menu per fruitori per la gestione dell'unità immobiliare,
     * di aprire un menu per fruitori per la gestione di una stanza all'interno dell'unità immobiliare, di creare una nuova regola, di rimuoverne una esistente o di visuallizzare tutte
     * le regole dell'unità. Entrambe le operazioni avvengono dopo che l'utente ha scelto
     * su quale unità immobiliare lavorare. Se esiste solo un'unità immobiliare allora la scelta viene effettuata automaticamente. Se non ne esistono allora si torna al menu precedente
     * perché bisogna crearne una.
     * Il menu consente anche di tornare indietro e chiudere questo menu
     */
    public static void avvia(){
        String nomeUnitaSuCuiLavorare = premenuUnita();
        
        if (nomeUnitaSuCuiLavorare == null) return;

        if(nomeUnitaSuCuiLavorare.equals(NONE)){
            System.out.println(NONE);
            return;
        }

        

        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare));

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: // menu gestione unita
                    MenuGestioneUnitaF.avvia(nomeUnitaSuCuiLavorare);
                    break;
                case 2: //menu gestione stanza
                    MenuGestioneStanzaF.avvia(nomeUnitaSuCuiLavorare);
                    break;
                case 3: //crea nuova regola
                    if(!checkSensori(nomeUnitaSuCuiLavorare)){ System.out.println(ZERO_SENSORI); break;}
                    if(!checkAttuatori(nomeUnitaSuCuiLavorare)){ System.out.println(ZERO_ATTUATORI); break;}
                    String IDregolaNuova = Modificatore.aggiungiRegola(nomeUnitaSuCuiLavorare);
                    if(IDregolaNuova != null)    System.out.println(String.format(SUCCESSO_INSERIMENTO_REGOLA, IDregolaNuova));
                    else{
                        System.out.println(ERRORE_INSERIMENTO_REGOLA);
                        break;
                    }
                    boolean antecedenteSiNo = InputDati.yesOrNo(ANTECEDENTE_SI_NO);
                    if(antecedenteSiNo){ //inserimento dell'antecedente
                        do {
                            System.out.println(SCELTA_LHS);
                            String lhs = sceltaLhs(nomeUnitaSuCuiLavorare);
                            if(lhs == null){ System.out.println(LHS_NECESSARIO); continue; } else { System.out.println(SUCCESSO_INSERIMENTO_LHS); }
                            System.out.println(SCELTA_RELOP);
                            String relOp = sceltaRelOp(lhs);
                            if(relOp == null){ System.out.println(RELOP_NECESSARIO); continue; } else { System.out.println(SUCCESSO_INSERIMENTO_REL_OP); }
                            System.out.println(SCELTA_RHS);
                            if (InputDati.yesOrNo(RHS_IS_COSTANTE)) { //RHS NUMERICO
                                double rhsNum = InputDati.leggiDouble(INSERIMENTO_COSTANTE_RHS);
                                if (Modificatore.aggiungiComponenteAntecedente(lhs, relOp, rhsNum, nomeUnitaSuCuiLavorare, IDregolaNuova))//inserimento componente
                                    System.out.println(SUCCESSO_INSERIMENTO_COMPONENTE);
                                else {
                                    System.out.println(ERRORE_INSERIMENTO_COMPONENTE_ANTECEDENTE);
                                    continue;
                                }
                            } else { // RHS NON NUMERICO
                                String rhs; boolean scalare;
                                if(InputDati.yesOrNo(STRINGA_COST)){ //Stringa costante come "presenza di persone"
                                    rhs = InputDati.leggiStringaNonVuota(INSERIMENTO_STRINGA_COSTANTE_RHS);
                                    scalare = true;
                                }else { //variabile sensoriale
                                    rhs = sceltaLhs(nomeUnitaSuCuiLavorare);
                                    scalare = false;
                                    if (rhs == null) {
                                        System.out.println(RHS_NECESSARIO);
                                        continue; // fa reinserire tutta la condizione
                                    }
                                }
                                if(Modificatore.aggiungiComponenteAntecedente(lhs,relOp,rhs, scalare, nomeUnitaSuCuiLavorare, IDregolaNuova)) //inserimento componente
                                    System.out.println(SUCCESSO_INSERIMENTO_COMPONENTE);
                                else {
                                    System.out.println(ERRORE_INSERIMENTO_COMPONENTE_ANTECEDENTE);
                                    continue;
                                }
                            } //fine inserimento componente

                            //qui ci arrivo solo se l'inserimento del componente ha avuto successo
                            if(InputDati.yesOrNo(CONTINUARE_CON_ANTECEDENTE)){
                                while(true) {
                                    String logicOp = premenuLogicOp(); // && o ||
                                    if(logicOp != null) {
                                        if (Modificatore.aggiungiOperatoreLogico(nomeUnitaSuCuiLavorare, IDregolaNuova, logicOp))
                                            break; //si può proseguire con nuovi componenti
                                        System.out.println(ERRORE_INSERIMENTO_OP_LOGICO);
                                    }else
                                        System.out.println(LOGIC_OP_NECESSARIO);
                                }
                            }else
                                break; //passa al conseguente
                        }while(true);
                    }
                    //inizio inserimento del conseguente
                    boolean almenoUnaAzione = false;
                    System.out.println(COSTRUZIONE_CONSEGUENTE);
                    while(true) {
                        if (costruisciAzione(nomeUnitaSuCuiLavorare, IDregolaNuova)) {
                            System.out.println(SUCCESSO_INSERIMENTO_AZIONE);
                            almenoUnaAzione = true;
                        } else{  System.out.println(ERRORE_INSERIMENTO_AZIONE); continue; }
                        if(!InputDati.yesOrNo(CONTINUARE_CON_CONSEGUENTE)){
                            if(almenoUnaAzione)
                                break;
                            System.out.println(ALMENO_UNA_AZIONE);
                        }
                    }

                    break;
                case 4: //rimuovi una regola
                    String regolaDaCancellare = premenuAntecedentiRegole(nomeUnitaSuCuiLavorare); //viene presentato all'utente un elenco delle antecedenti da cui riconoscerle e sceglierle
                    if(regolaDaCancellare != null){
                        if(Modificatore.rimuoviRegola(nomeUnitaSuCuiLavorare, regolaDaCancellare))
                            System.out.println(SUCCESSO_RIMOZIONE_REGOLA);
                        else
                            System.out.println(ERRORE_RIMOZIONE_REGOLA);
                    }
                    break;
                case 5: //visualizzaRegole
                    for (String descrizioneRegola : Recuperatore.getRegoleUnita(nomeUnitaSuCuiLavorare)) {
                        System.out.println(descrizioneRegola);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private static String premenuUnita(){
        String[] nomiUnitaImmobiliari = Recuperatore.getNomiUnita();

        if(nomiUnitaImmobiliari.length == 0) //non esistono unità immobiliari
            return NONE;

        //se solo una scelta allora seleziono quella e procedo automaticamente
        if (nomiUnitaImmobiliari.length == 1)
            return nomiUnitaImmobiliari[0];

        MyMenu m = new MyMenu(UNITA_IMMOBILIARI_ESISTENTI, nomiUnitaImmobiliari);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiUnitaImmobiliari[scelta-1];
    }

    private static String sceltaLhs(String unita){
        String nomeSensoreScelto = premenuSensori(unita);
        if(nomeSensoreScelto == null) return null;
        String infoRilevabile = premenuInfo(nomeSensoreScelto);
        if(infoRilevabile == null) return null;
        return (nomeSensoreScelto + "." + infoRilevabile);
    }

    private static String sceltaRelOp(String lhs){
        String[] campi = lhs.split(Pattern.quote("."));
        if(Recuperatore.isInfoNumerica(campi[0], campi[1])) //[0] t1_termometro   [1] infoRilevabile
            return premenuRelOp();
        else return "="; //se l'info è scalare l'unico operatore applicabile è '='
    }

    private static String premenuSensori(String unita){
        String[] sensori = Recuperatore.getNomiSensori(unita);
        MyMenu m = new MyMenu(String.format(SENSORI_UNITA, unita), sensori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : sensori[scelta-1];
    }

    private static boolean checkSensori(String unita){
        String[] sensori = Recuperatore.getNomiSensori(unita);
        if(sensori.length == 0) return false;
        return true;
    }

    private static boolean checkAttuatori(String unita){
        String[] attuatori = Recuperatore.getNomiAttuatori(unita);
        if(attuatori.length == 0) return false;
        return true;

    }

    private static String premenuInfo(String sensore){
        String[] info = Recuperatore.getInformazioniRilevabili(sensore);
        //if(info.length == 1) return info[0];
        MyMenu m = new MyMenu(String.format(INFO_DEL_SENSORE, sensore), info);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : info[scelta-1];
    }

    private static String premenuLogicOp(){
        String[] logicOps = new String[] {"&&",  "||"};
        MyMenu m = new MyMenu("Operatori Logici: ", logicOps);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : logicOps[scelta-1];
    }

    private static String premenuRelOp(){
        String[] relOps = new String[]{"<", ">", "<=", ">=", "="};
        MyMenu m = new MyMenu("Operatori Relazionali: ", relOps);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : relOps[scelta-1];
    }

    private static final String premenuAntecedentiRegole(String unita){ //presenta un elenco delle antecedenti da cui sceglierne una da rimuovere
        HashMap<String, String> coppieAntID= (HashMap<String, String>) Recuperatore.getAntecedentiRegoleUnita(unita);
        String[] antecedenti =  coppieAntID.keySet().toArray(new String[0]);
        MyMenu m = new MyMenu(String.format(REGOLE_UNITA, unita), antecedenti);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : coppieAntID.get(antecedenti[scelta-1]);
    }

    private static boolean costruisciAzione(String nomeUnitaSuCuiLavorare, String IDregola){
        while(true) {
            String attuatore = premenuAttuatori(nomeUnitaSuCuiLavorare);
            if(attuatore == null){ System.out.println(LHS_ATT_NECESSARIO); continue; }
            String modalita = premenuModalita(attuatore);
            if(modalita == null){ System.out.println(RHS_ATT_NECESSARIO); continue; }
            //da fare solo se la modalità è parametrica
            if (Recuperatore.isModalitaParametrica(attuatore, modalita)) {
                String[] params = Recuperatore.getNomiParametriModalita(attuatore, modalita);
                Map<String, Double> listaParams = new HashMap<>();
                for (int i = 0; i < params.length; i++) {
                    double nuovoValore = InputDati.leggiDouble(String.format("Imposta un nuovo valore per il parametro %s della modalita %s di %s", params[i], modalita, attuatore));
                    listaParams.put(params[i], nuovoValore);
                }
                if(Modificatore.aggiungiAzioneConseguente(attuatore, modalita, listaParams, nomeUnitaSuCuiLavorare, IDregola)){
                    return true;
                }else
                    return false;
            }else {//finisco qui se la modalità non è parametrica
                if (Modificatore.aggiungiAzioneConseguente(attuatore, modalita, nomeUnitaSuCuiLavorare, IDregola)) {
                    return true;
                } else
                    return false;
            }
        }
    }

    private static String premenuAttuatori(String unita){
        String[] attuatori = Recuperatore.getNomiAttuatori(unita);
        MyMenu m = new MyMenu(String.format(ATTUATORI_UNITA, unita), attuatori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : attuatori[scelta-1];
    }

    private static String premenuModalita(String attuatore){
        String[] modes = Recuperatore.getModalitaTutte(attuatore);
        MyMenu m = new MyMenu(ELENCO_MODALITA_ATTUATORE, modes);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : modes[scelta-1];
    }




}
