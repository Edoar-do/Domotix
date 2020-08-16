package view.listeners.edit_listeners;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.controller.Verificatore;
import view.ModifySignal;
import view.PannelloNord;
import view.Presenter;
import view.listeners.utils.AutoCompletion;
import view.listeners.utils.GraphicInput;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class EditCreazioneRegole implements ActionListener, ModifySignal {

    private Verificatore ver;
    private Rappresentatore rapp;
    private Interpretatore inter;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    public EditCreazioneRegole(Interpretatore inter, Rappresentatore rapp, Verificatore ver, PannelloNord pannelloNord, Presenter presenter){
        this.inter = inter;
        this.ver = ver;
        this.pannelloNord = pannelloNord;
        this.rapp = rapp;
        this.presenter = presenter;
    }

    private boolean checkSensori(String unita) {
        String[] sensori = rapp.getNomiSensori(unita);
        return sensori.length != 0;
    }

    private boolean checkAttuatori(String unita) {
        String[] attuatori = rapp.getNomiAttuatori(unita);
        return attuatori.length != 0;

    }

    private boolean costruisciAzione(String nomeUnitaSuCuiLavorare, String IDregola){
            String[] nomiAttuatori = rapp.getNomiAttuatori(nomeUnitaSuCuiLavorare);
            JComboBox comboAttuatori = new JComboBox(nomiAttuatori);
            comboAttuatori.setToolTipText("Scegli un attuatore per la costruzione dell'azione");
            AutoCompletion.enable(comboAttuatori);

            //SCELTA ATTUATORE AZIONE
            JOptionPane.showOptionDialog(null, comboAttuatori, "Conseguente: scelta attuatore azione", -1, 3, null, null, null);
            String attuatore = nomiAttuatori[comboAttuatori.getSelectedIndex()];

            String[] nomiModalita = rapp.getModalitaTutte(attuatore);
            JComboBox comboModalita = new JComboBox(nomiModalita);
            comboModalita.setToolTipText("Scegli la modalità in cui entrerà " + attuatore);
            AutoCompletion.enable(comboModalita);

            //SCELTA MODALITA DA IMPOSTARE
            JOptionPane.showOptionDialog(null, comboModalita, "Conseguente: scelta attuatore azione", -1, 3, null, null, null);
            String modalita = nomiModalita[comboModalita.getSelectedIndex()];

            //da fare solo se la modalità è parametrica
            if (ver.isModalitaParametrica(attuatore, modalita)) {
                String[] params = rapp.getNomiParametriModalita(attuatore, modalita);
                Map<String, Double> listaParams = new HashMap<>();
                double nuovoValore;

                //ciclo inserimenti nuovi valori per ogni parametro della modalità operativa parametrica
                for (String param : params) {
                    nuovoValore = GraphicInput.leggiDoubleConMinimo("Inserire nuovo valore parametro", "Nuovo valore del parametro, minimo 0.0", 0.0);
                    listaParams.put(param, nuovoValore);
                }

                //condizione di start con modalita parametrica
                if (GraphicInput.yesOrNo("Inserire una condizione di start nell'azione?", "Azione: condizione di start")) { //aggiunta azione con parametri CON start
                    double orario;
                    do
                        orario = GraphicInput.leggiDoubleConMinimo("Inserimento orario di start", "Orario di start, minimo 0.0", 0.0);
                    while(!ver.checkValiditaOrario(orario));

                    return inter.aggiungiAzioneConseguente(attuatore, modalita, listaParams, orario, nomeUnitaSuCuiLavorare, IDregola);

                } else { //aggiunta azione con parametri SENZA start
                    return inter.aggiungiAzioneConseguente(attuatore, modalita, listaParams, nomeUnitaSuCuiLavorare, IDregola);
                }
            } else {//finisco qui se la modalità NON è parametrica
                if (GraphicInput.yesOrNo("Inserire una condizione di start nell'azione?", "Azione: condizione di start")) { //aggiunta azioni senza parametri con start
                    double orario;
                    do
                        orario = GraphicInput.leggiDoubleConMinimo("Inserimento orario di start", "Orario di start, minimo 0.0", 0.0);
                    while(!ver.checkValiditaOrario(orario));

                    return inter.aggiungiAzioneConseguente(attuatore, modalita, orario, nomeUnitaSuCuiLavorare, IDregola);
                } else { //aggiunta azione senza parametri senza start
                    return inter.aggiungiAzioneConseguente(attuatore, modalita, nomeUnitaSuCuiLavorare, IDregola);
                }
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String unita = pannelloNord.getUnitaCorrente();
        if(!checkSensori(unita)) { //non ci sono sensori nell'unità
            JOptionPane.showConfirmDialog(null, "Non sono presenti sensori nell'unità per comporre una regola", "Assenza sensori", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!checkAttuatori(unita)){
            JOptionPane.showConfirmDialog(null, "Non sono presenti attuatori nell'unità per comporre una regola", "Assenza attuatori", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        String IDregolaNuova = inter.aggiungiRegola(pannelloNord.getUnitaCorrente());
        if (IDregolaNuova != null)
            JOptionPane.showConfirmDialog(null, "Regola " + IDregolaNuova + " creata con successo", "Successo creazione regola", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        else {
            JOptionPane.showConfirmDialog(null, "Regola non creata correttamente", "Fallimento creazione regola", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(GraphicInput.yesOrNo("Desideri che la regola abbia un'antecedente? ", "Richiesta antecedente")){ //antecedente da comporre
            String[] logicOps = new String[]{"&&", "||"};
            String[] relOps = new String[]{"<", ">", "<=", ">=", "="};
            do{
                String[] nomiSensori = rapp.getNomiSensori(unita, true);
                JComboBox comboSensori = new JComboBox(nomiSensori);
                comboSensori.setToolTipText("Scegli un sensore come lhs");
                AutoCompletion.enable(comboSensori);

                //RICHIESTA SENSORE LHS
                JOptionPane.showOptionDialog(null, comboSensori, "LHS: scelta sensore", -1, 3, null, null, null);
                String sensore = nomiSensori[comboSensori.getSelectedIndex()];

                String[] nomiInfo = rapp.getInformazioniRilevabili(sensore);
                JComboBox comboInfo = new JComboBox(nomiInfo);
                comboInfo.setToolTipText("Scegli un'informazione rilevabile del " + sensore);

                //RICHIESTA INFO RILEVABILE DEL SENSORE RICHIESTO PRIMA
                JOptionPane.showOptionDialog(null, comboInfo, "LHS: scelta informazione rilevabile", -1, 3, null, null, null);
                String infoRilevabile = nomiInfo[comboInfo.getSelectedIndex()];

                String lhs = sensore + "." + infoRilevabile;
                String relOp;

                //INSERIMENTO OPERATORE RELAZIONALE
                if(lhs.equalsIgnoreCase("orologio_sistema.tempo") || ver.isInfoNumerica(sensore, infoRilevabile)){
                    JComboBox comboRelOp = new JComboBox(relOps);
                    comboRelOp.setToolTipText("Inserisci un operatore relazionale per il confronto");
                    JOptionPane.showOptionDialog(null, comboRelOp, "REL.OP: scelta operatore relazionale", -1, 3, null, null, null);
                    relOp =  relOps[comboRelOp.getSelectedIndex()];
                }else relOp = "=";

                //INSERIMENTO RHS: ORARIO SE SENSORE OROLOGIO OR RHS NUMERICO OR RHS NON NUMERICO
                if(ver.checkIsSensoreOrologio(lhs)){ //se sensore orologio allora lo obbligo a mettere un orario come rhs
                    double orario;

                    do
                        orario = GraphicInput.leggiDoubleConMinimo("RHS: inserimento orario", "Inserisci un orario. Se il formato non sarà corretto verrà richiesto", 0.0);
                    while(!ver.checkValiditaOrario(orario));

                    if (inter.aggiungiComponenteAntecedente(lhs, relOp, orario, unita, IDregolaNuova)) {
                        JOptionPane.showOptionDialog(null, "Componente antecedente inserita correttamente", "Successo Operazione", -1, 1, null, null, null);
                        //da qui balzo alla richiesta di voler continuare o meno l'antecedente
                    }else {
                        JOptionPane.showOptionDialog(null, "Componente antecedente non inserita correttamente", "Fallimento Operazione", -1, 1, null, null, null);
                        continue; //rifai tutto da capo
                    }

                }else if(GraphicInput.yesOrNo("RHS numerico?", "RHS: numerico o non-numerico")){ //RHS numerico

                    double valoreRif = GraphicInput.leggiDoubleConMinimo("RHS: inserimento valore", "Inserisci un valore come RHS", 0.0);

                    if (inter.aggiungiComponenteAntecedente(lhs, relOp, valoreRif, unita, IDregolaNuova))//inserimento componente
                        JOptionPane.showOptionDialog(null, "Componente antecedente inserita correttamente", "Successo Operazione", -1, 1, null, null, null);
                    else {
                        JOptionPane.showOptionDialog(null, "Componente antecedente non inserita correttamente", "Fallimento Operazione", -1, 1, null, null, null);
                        continue;
                    }

                }else{ //RHS NON NUMERICO
                    boolean scalare;
                    String rhs, sensore2, infoRilevabile2;
                    if(GraphicInput.yesOrNo("RHS è una stringa costante?", "RHS non numerico: stringa costante")) {
                        scalare = true;
                        rhs = GraphicInput.leggiStringaNonVuota("RHS: inserimento stringa costante", "Inserisci una stringa");
                    }else{
                        JOptionPane.showOptionDialog(null, comboSensori, "RHS: scelta sensore", -1, 3, null, null, null);
                        sensore2 = nomiSensori[comboSensori.getSelectedIndex()];
                        JOptionPane.showOptionDialog(null, comboInfo, "RHS: scelta informazione rilevabile", -1, 3, null, null, null);
                        infoRilevabile2 = nomiInfo[comboInfo.getSelectedIndex()];
                        rhs = sensore2 + "." + infoRilevabile2;
                        scalare = false;
                    }

                    if (inter.aggiungiComponenteAntecedente(lhs, relOp, rhs, scalare, unita, IDregolaNuova)) //inserimento componente
                        JOptionPane.showOptionDialog(null, "Componente antecedente inserita correttamente", "Successo Operazione", -1, 1, null, null, null);
                    else {
                        JOptionPane.showOptionDialog(null, "Componente antecedente non inserita correttamente", "Fallimento Operazione", -1, 0, null, null, null);
                        continue;
                    }
                } //fine inserimento componente antecedente

                //CONTINUARE CON COSTRUZIONE ANTECEDENTE?
                if(GraphicInput.yesOrNo("Continuare con l'antecedente?", "Continuazione antecedente")){

                    do {
                        JComboBox comboOperatoriLogici = new JComboBox(logicOps);
                        comboOperatoriLogici.setToolTipText("Inserisci un operatore logico per continuare nella costruzione dell'antecedente");
                        JOptionPane.showOptionDialog(null, comboOperatoriLogici, "Continuazione antecedente: operatore logico", -1, 3, null, null, null);
                        String operatoreLogico = logicOps[comboOperatoriLogici.getSelectedIndex()];

                        if (inter.aggiungiOperatoreLogico(unita, IDregolaNuova, operatoreLogico)) {
                            break; //esce dal while interno di inserimento dell'operatore logico - riprende con inserimento componente ant.
                        }
                        JOptionPane.showOptionDialog(null, "Errore inserimento operatore logico", "Errore Logic Op.", -1, 0, null, null, null);
                        //ripete l'inserimento dell'operatore logico

                    }while(true);

                }else{
                    break; //esci dal while più esterno - passa al conseguente
                }
            }while(true);
        }

        //COSTRUZIONE CONSEGUENTE
        boolean almenoUnaAzione;
            while (true) {
                if (costruisciAzione(unita, IDregolaNuova)) {
                    JOptionPane.showOptionDialog(null, "Azione del conseguente inserita con successo", "Successo operazione", -1, 1, null, null, null);
                    almenoUnaAzione = true;
                } else {
                    JOptionPane.showOptionDialog(null, "Inserimento azione del conseguente fallito", "Fallimento operazione", -1, 0, null, null, null);
                    continue;
                }

                if (!GraphicInput.yesOrNo("Continui a costruire il conseguente?", "Continuazione conseguente")) {
                    if (almenoUnaAzione) {
                        inter.cambioStatoRegola(IDregolaNuova, unita); //CAMBIA LO STATO DELLA REGOLA DA DISATTIVA AD ATTIVA PERCHE' VIENE CREATA DISATTIVA
                        segnalaModifica(rapp.getDescrizioneUnita(unita));
                        break;
                    }
                    JOptionPane.showOptionDialog(null, "Necessario inserire almeno un'azione", "Conseguente: almeno un'azione", -1, 1, null, null, null);
                }
            }
    }

    @Override
    public void segnalaModifica(String descrizione) {
        presenter.show(descrizione);
    }
}
