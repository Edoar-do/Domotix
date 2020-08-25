package domotix.view.listeners;

import domotix.controller.*;
import domotix.view.passiveView.PannelloNord;
import domotix.view.Presenter;
import domotix.view.listeners.edit_listeners.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioButtonListener implements ActionListener {

    private Rappresentatore rapp;
    private Verificatore ver;
    private Interpretatore inter;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    private JMenu m_suCategorieDispositivi, m_suUnitaImmobiliari, m_suStanze, m_suArtefatti,
                f_suDispositivi, f_suRegole;

    //menu item di manutentore
    private JMenuItem m_rimozioneCatSens, m_rimozioneCatAtt, m_addSensStanza, m_rimuoviSensStanza, m_addAttStanza, m_rimuoviAttStanza,
                m_addArtefattoStanza, m_rimuoviArtefattoStanza, m_addStanzaUnita, m_rimuoviStanzaUnita, m_rimuoviUnitaCorrente,
                m_addSensArtefatto, m_rimuoviSensArtefatto, m_addAttArtefatto, m_rimuoviAttArtefatto,
                m_collegaSensStanza, m_collegaAttStanza, m_collegaSensArtefatto, m_collegaAttArtefatto;

    //menu item di fruitore
    private JMenuItem f_cambioStatoSens, f_cambioStatoAtt, f_attDisRegole,f_setModeAttStanza, f_setModeAttArtefatto, f_addRegolaUnita, f_rimuoviRegolaUnita;

    //listener manutentore
    private ActionListener edit_categorieDispositiviListener, edit_unitaListener, edit_sensStanzaListener, edit_attStanzaListener, edit_artefattoStanzaListener,
            edit_sensArtefattoListener, edit_attArtefattoListener, edit_collegaDispositiviStanzaListener, edit_collegaDispositiviArtefattoListener, edit_creazioneRegoleListener;

    //listener fruitore
    private ActionListener edit_cambioStatoDispListener, edit_setModeAttListener, edit_regoleListener;

    public RadioButtonListener(Rappresentatore rapp, Verificatore ver, Interpretatore inter, PannelloNord pannelloNord, Presenter presenter) {
        this.rapp = rapp;
        this.ver = ver;
        this.inter = inter;
        this.pannelloNord = pannelloNord;
        this.presenter = presenter;
        creazioneMenu();
        creaListaManutentore();
        creaListaFruitore();
        attaccaListenerManutentore(inter, rapp);
        attaccaListenerFruitore(inter, rapp, ver);
    }

    private void creazioneMenu(){
        m_suCategorieDispositivi = new JMenu("su categorie di dispositivi");
        m_suUnitaImmobiliari = new JMenu("su unità immobiliari");
        m_suStanze = new JMenu("su stanze");
        m_suArtefatti = new JMenu("su artefatti");
        f_suDispositivi = new JMenu("su dispositivi");
        f_suRegole = new JMenu("su regole");
    }

    private void creaListaManutentore(){
        m_rimozioneCatSens = new JMenuItem("Rimuovi Categoria Sensori");
        m_rimozioneCatAtt = new JMenuItem("Rimuovi Categoria Attuatori");
        m_suCategorieDispositivi.add(m_rimozioneCatSens);
        m_suCategorieDispositivi.add(m_rimozioneCatAtt);

        m_addSensStanza = new JMenuItem("Aggiungi un sensore ad una stanza");
        m_rimuoviSensStanza = new JMenuItem("Rimuovi un sensore da una stanza");
        m_addAttStanza = new JMenuItem("Aggiungi un attuatore ad una stanza");
        m_rimuoviAttStanza = new JMenuItem("Rimuovi un attuatore da una stanza");
        m_addArtefattoStanza = new JMenuItem("Aggiungi un artefatto ad una stanza");
        m_rimuoviArtefattoStanza = new JMenuItem("Rimuovi un artefatto da una stanza");
        m_collegaSensStanza = new JMenuItem("Collega sensore esistente ad un'altra stanza");
        m_collegaAttStanza = new JMenuItem("Collega attuatore esistente ad un'altra stanza");
        m_suStanze.add(m_addSensStanza);
        m_suStanze.add(m_rimuoviSensStanza);
        m_suStanze.add(m_addAttStanza);
        m_suStanze.add(m_rimuoviAttStanza);
        m_suStanze.add(m_addArtefattoStanza);
        m_suStanze.add(m_rimuoviArtefattoStanza);
        m_suStanze.add(m_collegaSensStanza);
        m_suStanze.add(m_collegaAttStanza);

        m_rimuoviUnitaCorrente = new JMenuItem("Rimuovi unità immobiliare corrente");
        m_addStanzaUnita = new JMenuItem("Aggiungi una stanza all'unità");
        m_rimuoviStanzaUnita = new JMenuItem("rimuovi una stanza dall'unità");
        m_suUnitaImmobiliari.add(m_rimuoviUnitaCorrente);
        m_suUnitaImmobiliari.add(m_addStanzaUnita);
        m_suUnitaImmobiliari.add(m_rimuoviStanzaUnita);

        m_addSensArtefatto = new JMenuItem("Aggiungi un sensore ad un artefatto");
        m_rimuoviSensArtefatto = new JMenuItem("Rimuovi un sensore da un artefatto");
        m_addAttArtefatto = new JMenuItem("Aggiungi un attuatore ad un artefatto");
        m_rimuoviAttArtefatto = new JMenuItem("Rimuovi un attuatore da un artefatto");
        m_collegaSensArtefatto = new JMenuItem("Collega sensore ad un altro artefatto");
        m_collegaAttArtefatto = new JMenuItem("Collega attuatore ad un altro artefatto");
        m_suArtefatti.add(m_addSensArtefatto);
        m_suArtefatti.add(m_rimuoviSensArtefatto);
        m_suArtefatti.add(m_addAttArtefatto);
        m_suArtefatti.add(m_rimuoviAttArtefatto);
        m_suArtefatti.add(m_collegaSensArtefatto);
        m_suArtefatti.add(m_collegaAttArtefatto);
    }

    private void creaListaFruitore(){
        f_setModeAttStanza = new JMenuItem("Imposta modalità operativa attuatore collegato ad una stanza");
        f_setModeAttArtefatto = new JMenuItem("Imposta modalità operativa attuatore collegato ad un artefatto");
        f_addRegolaUnita = new JMenuItem("Aggiungi una regola all'unità corrente");
        f_rimuoviRegolaUnita = new JMenuItem("Rimuovi una regola dell'unità corrente");
        f_cambioStatoSens = new JMenuItem("Cambio stato sensore");
        f_cambioStatoAtt = new JMenuItem("Cambio stato attuatore");
        f_attDisRegole = new JMenuItem("Attiva/disattiva regole dell'unità corrente");

        f_suDispositivi.add(f_setModeAttStanza);
        f_suDispositivi.add(f_setModeAttArtefatto);
        f_suDispositivi.add(f_cambioStatoAtt);
        f_suDispositivi.add(f_cambioStatoSens);
        f_suRegole.add(f_addRegolaUnita);
        f_suRegole.add(f_rimuoviRegolaUnita);
        f_suRegole.add(f_attDisRegole);
    }

    private void attaccaListenerManutentore(Interpretatore inter, Rappresentatore rapp){
        //listener edit categorie dispositivi - rimuovi categoria sensori e attuatori
        edit_categorieDispositiviListener = new EditCategorieDispositivi(inter, rapp, pannelloNord);
        m_rimozioneCatSens.addActionListener(edit_categorieDispositiviListener);
        m_rimozioneCatAtt.addActionListener(edit_categorieDispositiviListener);
        //listener edit unità - aggiungi, rimuovi stanza a unità e rimuovi unità corrente
        edit_unitaListener = new EditUnitaListener(inter, rapp, pannelloNord, presenter);
        m_addStanzaUnita.addActionListener(edit_unitaListener);
        m_rimuoviStanzaUnita.addActionListener(edit_unitaListener);
        m_rimuoviUnitaCorrente.addActionListener(edit_unitaListener);
        // 3 listener di stanza: add/remove sensori + add/remove attuatori + add/remove artefatti
        edit_sensStanzaListener = new EditSensoreStanzaListener(inter, rapp, pannelloNord, presenter);
        m_addSensStanza.addActionListener(edit_sensStanzaListener);
        m_rimuoviSensStanza.addActionListener(edit_sensStanzaListener);
        edit_attStanzaListener = new EditAttuatoreStanzaListener(inter, rapp, pannelloNord, presenter);
        m_addAttStanza.addActionListener(edit_attStanzaListener);
        m_rimuoviAttStanza.addActionListener(edit_attStanzaListener);
        edit_artefattoStanzaListener = new EditArtefattoStanzaListener(inter, rapp, pannelloNord, presenter);
        m_addArtefattoStanza.addActionListener(edit_artefattoStanzaListener);
        m_rimuoviArtefattoStanza.addActionListener(edit_artefattoStanzaListener);
        // 2 listener di artefatto: add/remove sensori + add/remove attuatori
        edit_sensArtefattoListener = new EditSensoreArtefattoListener(inter, rapp, pannelloNord,presenter );
        m_addSensArtefatto.addActionListener(edit_sensArtefattoListener);
        m_rimuoviSensArtefatto.addActionListener(edit_sensArtefattoListener);
        edit_attArtefattoListener = new EditAttuatoreArtefattoListener(inter, rapp, pannelloNord,presenter );
        m_addAttArtefatto.addActionListener(edit_attArtefattoListener);
        m_rimuoviAttArtefatto.addActionListener(edit_attArtefattoListener);
        // 2 listener per il collegamento di sensori e attuatori a stanze e artefatti
        edit_collegaDispositiviStanzaListener = new EditCollegaDispStanzaListener(inter, rapp, pannelloNord, presenter);
        m_collegaSensStanza.addActionListener(edit_collegaDispositiviStanzaListener);
        m_collegaAttStanza.addActionListener(edit_collegaDispositiviStanzaListener);
        edit_collegaDispositiviArtefattoListener = new EditCollegaDispArtefattoListener(inter, rapp, pannelloNord,presenter );
        m_collegaSensArtefatto.addActionListener(edit_collegaDispositiviArtefattoListener);
        m_collegaAttArtefatto.addActionListener(edit_collegaDispositiviArtefattoListener);
    }

    private void attaccaListenerFruitore(Interpretatore inter, Rappresentatore rapp, Verificatore ver) {
        //listener per il cambio stato di sensori e attuatori
        edit_cambioStatoDispListener = new EditCambioStatoDispositiviListener(inter, rapp, pannelloNord,presenter);
        f_cambioStatoSens.addActionListener(edit_cambioStatoDispListener);
        f_cambioStatoAtt.addActionListener(edit_cambioStatoDispListener);
        //listener per il settaggio della modalita operativa di attuatori di stanze e artefatti
        edit_setModeAttListener = new EditSetModalitaAttListener(inter, rapp, pannelloNord, presenter);
        f_setModeAttStanza.addActionListener(edit_setModeAttListener);
        f_setModeAttArtefatto.addActionListener(edit_setModeAttListener);
        //listener per la rimozione, creazione e cambio stato di regole
        edit_regoleListener = new EditRegoleListener(inter, rapp, ver, pannelloNord,presenter );
        f_attDisRegole.addActionListener(edit_regoleListener);
        f_rimuoviRegolaUnita.addActionListener(edit_regoleListener);
        //listener creazione regole
        edit_creazioneRegoleListener = new EditCreazioneRegole(inter, rapp, ver, pannelloNord,presenter );
        f_addRegolaUnita.addActionListener(edit_creazioneRegoleListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("manutentore")){
            pannelloNord.getFile_nuovo().setEnabled(true); //manutentore può creare roba nuova
            pannelloNord.getFile_importa().setEnabled(true); //manutentore può importare
            pannelloNord.getEdit().removeAll();
            pannelloNord.getEdit().add(m_suCategorieDispositivi);
            pannelloNord.getEdit().add(m_suUnitaImmobiliari);
            pannelloNord.getEdit().add(m_suStanze);
            pannelloNord.getEdit().add(m_suArtefatti);
        }else{ //fruitore
            pannelloNord.getFile_nuovo().setEnabled(false); //fruitore non può creare nulla di nuovo
            pannelloNord.getFile_importa().setEnabled(false); //fruitore non può importare
            pannelloNord.getEdit().removeAll();
            pannelloNord.getEdit().add(f_suDispositivi);
            pannelloNord.getEdit().add(f_suRegole);
        }
    }
}