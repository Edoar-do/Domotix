package domotix.view.menus.menuUnita;

import domotix.controller.*;

import domotix.view.InputDati;
import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import domotix.view.menus.menuUnita.gestioneStanza.MenuGestioneStanzaF;
import domotix.view.menus.menuUnita.gestioneUnita.MenuGestioneUnitaF;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import static domotix.view.ViewConstants.*;

/** @author Edoardo Coppola*/
public class MenuUnitaF {
    private static final String TITOLO = "Menu Unita Fruitore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Menu Gestione Unita Fruitore", "Menu Gestione Stanza Fruitore", "Crea una nuova regola", "Rimuovi una regola", "Visualizza Regole", "Attiva/Disattiva Regola"};
    private static final String NONE = "Nessuna unità immobiliare esistente. L'utente manutentore deve prima crearne una";

    private MyMenu menu;
    private Interpretatore i;
    private Verificatore v;
    private Rappresentatore r;
    private MenuGestioneUnitaF menuGestioneUnitaF;
    private MenuGestioneStanzaF menuGestioneStanzaF;
    

    public MenuUnitaF(Interpretatore i, Verificatore v, Rappresentatore r, MyMenu m){
        this.menu = m;
        menu.setTitolo(TITOLO);
        menu.setVoci(VOCI);
        this.i = i;
        this.v = v;
        this.r = r;
        menuGestioneStanzaF = new MenuGestioneStanzaF(menu, r, i);
        menuGestioneUnitaF = new MenuGestioneUnitaF(menu, i, r);
    }

    /**
     * Presenta all'utente fruitore un menu che offre la possibilità di aprire un menu per fruitori per la gestione dell'unità immobiliare,
     * di aprire un menu per fruitori per la gestione di una stanza all'interno dell'unità, di creare una nuova regola, di rimuoverne una esistente, di visuallizzare tutte
     * le regole dell'unità, di attivarne/disattivarne una. Tutte le operazioni avvengono dopo che l'utente ha scelto
     * su quale unità immobiliare lavorare. Se esiste solo un'unità immobiliare allora la scelta viene effettuata automaticamente. Se non ne esistono allora si torna al menu precedente
     * perché bisogna crearne una.
     * Il menu consente anche di tornare indietro e chiudere questo menu
     */
    public void avvia() {
        String nomeUnitaSuCuiLavorare = premenuUnita();

        if(nomeUnitaSuCuiLavorare == null) return;

        if (nomeUnitaSuCuiLavorare.equals(NONE)) {
            System.out.println(NONE);
            return;
        }
        
        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare));

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch (sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: // menu gestione unita
                    menuGestioneUnitaF.avvia(nomeUnitaSuCuiLavorare);
                    break;
                case 2: //menu gestione stanza
                    menuGestioneStanzaF.avvia(nomeUnitaSuCuiLavorare);
                    break;
                case 3: //crea nuova regola
                    if (!checkSensori(nomeUnitaSuCuiLavorare)) {
                        System.out.println(ZERO_SENSORI);
                        break;
                    }
                    if (!checkAttuatori(nomeUnitaSuCuiLavorare)) {
                        System.out.println(ZERO_ATTUATORI);
                        break;
                    }
                    String IDregolaNuova = i.aggiungiRegola(nomeUnitaSuCuiLavorare);
                    if (IDregolaNuova != null)
                        System.out.println(String.format(SUCCESSO_INSERIMENTO_REGOLA, IDregolaNuova));
                    else {
                        System.out.println(ERRORE_INSERIMENTO_REGOLA);
                        break;
                    }
                    boolean antecedenteSiNo = InputDati.yesOrNo(ANTECEDENTE_SI_NO);
                    if (antecedenteSiNo) { //inserimento dell'antecedente
                        do {
                            System.out.println(SCELTA_LHS);
                            String lhs = sceltaLhs(nomeUnitaSuCuiLavorare);
                            if (lhs == null) {
                                System.out.println(LHS_NECESSARIO);
                                continue;
                            } else {
                                System.out.println(SUCCESSO_INSERIMENTO_LHS);
                            }
                            System.out.println(SCELTA_RELOP);
                            String relOp = sceltaRelOp(lhs);
                            if (relOp == null) {
                                System.out.println(RELOP_NECESSARIO);
                                continue;
                            } else {
                                System.out.println(SUCCESSO_INSERIMENTO_REL_OP);
                            }
                            System.out.println(SCELTA_RHS);
                            if (v.checkIsSensoreOrologio(lhs)) { //allora lo obbligo a scegliere un orario double
                                double rhsOrario = InputDati.leggiDoubleConMinimo(INSERIMENTO_RHS_ORA, 0.0);
                                if (v.checkValiditaOrario(rhsOrario)) {
                                    if (i.aggiungiComponenteAntecedente(lhs, relOp, rhsOrario, nomeUnitaSuCuiLavorare, IDregolaNuova))
                                        System.out.println(SUCCESSO_INSERIMENTO_COMPONENTE); //da qui balzo alla richiesta di voler continuare o meno l'antecedente
                                    else {
                                        System.out.println(ERRORE_INSERIMENTO_COMPONENTE_ANTECEDENTE);
                                        continue; //rifai tutto da capo
                                    }
                                } else {
                                    System.out.println(ERRORE_VALIDITA_ORARIO);
                                    continue;
                                }
                            } else if (InputDati.yesOrNo(RHS_IS_COSTANTE)) { //RHS NUMERICO
                                double rhsNum = InputDati.leggiDouble(INSERIMENTO_COSTANTE_RHS);
                                if (i.aggiungiComponenteAntecedente(lhs, relOp, rhsNum, nomeUnitaSuCuiLavorare, IDregolaNuova))//inserimento componente
                                    System.out.println(SUCCESSO_INSERIMENTO_COMPONENTE);
                                else {
                                    System.out.println(ERRORE_INSERIMENTO_COMPONENTE_ANTECEDENTE);
                                    continue;
                                }
                            } else { // RHS NON NUMERICO
                                String rhs;
                                boolean scalare;
                                if (InputDati.yesOrNo(STRINGA_COST)) { //Stringa costante come "presenza di persone"
                                    rhs = InputDati.leggiStringaNonVuota(INSERIMENTO_STRINGA_COSTANTE_RHS);
                                    scalare = true;
                                } else { //variabile sensoriale
                                    rhs = sceltaLhs(nomeUnitaSuCuiLavorare);
                                    scalare = false;
                                    if (rhs == null) {
                                        System.out.println(RHS_NECESSARIO);
                                        continue; // fa reinserire tutta la condizione
                                    }
                                }
                                if (i.aggiungiComponenteAntecedente(lhs, relOp, rhs, scalare, nomeUnitaSuCuiLavorare, IDregolaNuova)) //inserimento componente
                                    System.out.println(SUCCESSO_INSERIMENTO_COMPONENTE);
                                else {
                                    System.out.println(ERRORE_INSERIMENTO_COMPONENTE_ANTECEDENTE);
                                    continue;
                                }
                            } //fine inserimento componente

                            //qui ci arrivo solo se l'inserimento del componente ha avuto successo
                            if (InputDati.yesOrNo(CONTINUARE_CON_ANTECEDENTE)) {
                                while (true) {
                                    String logicOp = premenuLogicOp(); // && o ||
                                    if (logicOp != null) {
                                        if (i.aggiungiOperatoreLogico(nomeUnitaSuCuiLavorare, IDregolaNuova, logicOp))
                                            break; //si può proseguire con nuovi componenti
                                        System.out.println(ERRORE_INSERIMENTO_OP_LOGICO);
                                    } else
                                        System.out.println(LOGIC_OP_NECESSARIO);
                                }
                            } else
                                break; //passa al conseguente
                        } while (true);
                    }
                    //inizio inserimento del conseguente
                    boolean almenoUnaAzione = false;
                    System.out.println(COSTRUZIONE_CONSEGUENTE);
                    while (true) {
                        if (costruisciAzione(nomeUnitaSuCuiLavorare, IDregolaNuova)) {
                            System.out.println(SUCCESSO_INSERIMENTO_AZIONE);
                            almenoUnaAzione = true;
                        } else {
                            System.out.println(ERRORE_INSERIMENTO_AZIONE);
                            continue;
                        }
                        if (!InputDati.yesOrNo(CONTINUARE_CON_CONSEGUENTE)) {
                            if (almenoUnaAzione) {
                                i.cambioStatoRegola(IDregolaNuova, nomeUnitaSuCuiLavorare); //CAMBIA LO STATO DELLA REGOLA DA DISATTIVA AD ATTIVA PERCHE' VIENE CREATA DISATTIVA
                                break;
                            }
                            System.out.println(ALMENO_UNA_AZIONE);
                        }
                    }
                    break;
                case 4: //rimuovi una regola
                    String regolaDaCancellare = premenuAntecedentiRegole(nomeUnitaSuCuiLavorare); //viene presentato all'utente un elenco delle antecedenti da cui riconoscerle e sceglierle
                    if (regolaDaCancellare != null) {
                        if (i.rimuoviRegola(nomeUnitaSuCuiLavorare, regolaDaCancellare))
                            System.out.println(SUCCESSO_RIMOZIONE_REGOLA);
                        else
                            System.out.println(ERRORE_RIMOZIONE_REGOLA);
                    }
                    break;
                case 5: //visualizzaRegole
                    for (String descrizioneRegola : r.getRegoleUnita(nomeUnitaSuCuiLavorare))
                        System.out.println(descrizioneRegola);
                    break;
                case 6: //attiva/disattiva regola
                    String IDregolaDaCambiare = premenuRegoleAttive_Disattive(nomeUnitaSuCuiLavorare);
                    if (IDregolaDaCambiare != null) {
                        switch(i.cambioStatoRegola(IDregolaDaCambiare, nomeUnitaSuCuiLavorare)){
                            case -1:
                                System.out.println(ERRORE_CAMBIO_STATO_REGOLA);
                                break;
                            case 1:
                                System.out.println(SUCCESSO_DISATTIVAZIONE);
                                break;
                            case 2:
                                System.out.println(SUCCESSO_ATTIVAZIONE);
                                break;
                            case 3:
                                System.out.println(SOSPENSIONE);
                                break;
                        }
                    }
                    break;
            }
        } while (sceltaMenu != 0);
    }

    private String premenuUnita() {
        String[] nomiUnitaImmobiliari = r.getNomiUnita();

        if (nomiUnitaImmobiliari.length == 0) //non esistono unità immobiliari
            return NONE;

        //se solo una scelta allora seleziono quella e procedo automaticamente
        if (nomiUnitaImmobiliari.length == 1)
            return nomiUnitaImmobiliari[0];

        MyMenu m = new MyMenu(UNITA_IMMOBILIARI_ESISTENTI, nomiUnitaImmobiliari);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiUnitaImmobiliari[scelta - 1];
    }

    private String sceltaLhs(String unita) {
        //if (InputDati.yesOrNo(CONDIZIONE_TEMPORALE)) return "time";
        String nomeSensoreScelto = premenuSensori(unita);
        if (nomeSensoreScelto == null) return null;
        String infoRilevabile = premenuInfo(nomeSensoreScelto);
        if (infoRilevabile == null) return null;
        return (nomeSensoreScelto + "." + infoRilevabile);
    }

    private String sceltaRelOp(String lhs) {
        if (lhs.equals("time"))
            return premenuRelOp();
        String[] campi = lhs.split(Pattern.quote("."));
        if (v.isInfoNumerica(campi[0], campi[1])) //[0] t1_termometro   [1] infoRilevabile
            return premenuRelOp();
        else return "="; //se l'info è scalare l'unico operatore applicabile è '='
    }

    private String premenuSensori(String unita) {
        String[] sensori = r.getNomiSensori(unita, true); // con true include anche il sensore orologio
        MyMenu m = new MyMenu(String.format(SENSORI_UNITA, unita), sensori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : sensori[scelta - 1];
    }

    private boolean checkSensori(String unita) {
        String[] sensori = r.getNomiSensori(unita);
        if (sensori.length == 0) return false;
        return true;
    }

    private boolean checkAttuatori(String unita) {
        String[] attuatori = r.getNomiAttuatori(unita);
        if (attuatori.length == 0) return false;
        return true;

    }

    private String premenuInfo(String sensore) {
        String[] info = r.getInformazioniRilevabili(sensore);
        //if(info.length == 1) return info[0];
        MyMenu m = new MyMenu(String.format(INFO_DEL_SENSORE, sensore), info);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : info[scelta - 1];
    }

    private String premenuLogicOp() {
        String[] logicOps = new String[]{"&&", "||"};
        MyMenu m = new MyMenu("Operatori Logici: ", logicOps);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : logicOps[scelta - 1];
    }

    private String premenuRelOp() {
        String[] relOps = new String[]{"<", ">", "<=", ">=", "="};
        MyMenu m = new MyMenu("Operatori Relazionali: ", relOps);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : relOps[scelta - 1];
    }

    private final String premenuAntecedentiRegole(String unita) { //presenta un elenco delle antecedenti da cui sceglierne una da rimuovere
        HashMap<String, String> coppieAntID = (HashMap<String, String>) r.getAntecedentiRegoleUnita(unita);
        String[] antecedenti = coppieAntID.keySet().toArray(new String[0]);
        MyMenu m = new MyMenu(String.format(REGOLE_UNITA, unita), antecedenti);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : coppieAntID.get(antecedenti[scelta - 1]);
    }

    private boolean costruisciAzione(String nomeUnitaSuCuiLavorare, String IDregola) {
        while (true) {
            String attuatore = premenuAttuatori(nomeUnitaSuCuiLavorare);
            if (attuatore == null) {
                System.out.println(LHS_ATT_NECESSARIO);
                continue;
            }
            String modalita = premenuModalita(attuatore);
            if (modalita == null) {
                System.out.println(RHS_ATT_NECESSARIO);
                continue;
            }
            //da fare solo se la modalità è parametrica
            if (v.isModalitaParametrica(attuatore, modalita)) {
                String[] params = r.getNomiParametriModalita(attuatore, modalita);
                Map<String, Double> listaParams = new HashMap<>();
                for (int i = 0; i < params.length; i++) {
                    double nuovoValore = InputDati.leggiDouble(String.format("Imposta un nuovo valore per il parametro %s della modalita %s di %s", params[i], modalita, attuatore));
                    listaParams.put(params[i], nuovoValore);
                }
                if (InputDati.yesOrNo(CONDIZIONE_START)) { //aggiunta azione con parametri CON start
                    double orarioStart = InputDati.leggiDoubleConMinimo(INSERIMENTO_RHS_ORA, 0.0);
                    if (v.checkValiditaOrario(orarioStart)) {
                        if (i.aggiungiAzioneConseguente(attuatore, modalita, listaParams, orarioStart, nomeUnitaSuCuiLavorare, IDregola))
                            return true;
                        else return false;
                    } else {
                        System.out.println(ERRORE_VALIDITA_ORARIO);
                        continue;
                    } //se sbagli orario rifai tutta la costruzione dell'azione
                } else { //aggiunta azione con parametri SENZA start
                    if (i.aggiungiAzioneConseguente(attuatore, modalita, listaParams, nomeUnitaSuCuiLavorare, IDregola)) {
                        return true;
                    } else
                        return false;
                }
            } else {//finisco qui se la modalità non è parametrica
                if (InputDati.yesOrNo(CONDIZIONE_START)) { //aggiunta azioni senza parametri con start
                    double orarioStart = InputDati.leggiDoubleConMinimo(INSERIMENTO_RHS_ORA, 0.0);
                    if (v.checkValiditaOrario(orarioStart)) {
                        if (i.aggiungiAzioneConseguente(attuatore, modalita, orarioStart, nomeUnitaSuCuiLavorare, IDregola))
                            return true;
                        else return false;
                    } else {
                        System.out.println(ERRORE_VALIDITA_ORARIO);
                        continue;
                    }
                } else { //aggiunta azione senza parametri senza start
                    if (i.aggiungiAzioneConseguente(attuatore, modalita, nomeUnitaSuCuiLavorare, IDregola)) {
                        return true;
                    } else
                        return false;
                }
            }
        }
    }

    private String premenuAttuatori(String unita) {
        String[] attuatori = r.getNomiAttuatori(unita);
        MyMenu m = new MyMenu(String.format(ATTUATORI_UNITA, unita), attuatori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : attuatori[scelta - 1];
    }

    private String premenuModalita(String attuatore) {
        String[] modes = r.getModalitaTutte(attuatore);
        MyMenu m = new MyMenu(ELENCO_MODALITA_ATTUATORE, modes);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : modes[scelta - 1];
    }

    private String premenuRegoleAttive_Disattive(String unita){
        HashMap<String, String> coppieAntIDregoleAttive_Disattive = (HashMap<String, String>) r.getAntecentiRegoleAttiveDisattive(unita);
        String[] antecedentiPiuStato = coppieAntIDregoleAttive_Disattive.keySet().toArray(new String[0]);
        MyMenu m = new MyMenu(String.format(REGOLE_UNITA_ATTIVE_O_DISATTIVE, unita), antecedentiPiuStato);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : coppieAntIDregoleAttive_Disattive.get(antecedentiPiuStato[scelta - 1]);
    }
}
