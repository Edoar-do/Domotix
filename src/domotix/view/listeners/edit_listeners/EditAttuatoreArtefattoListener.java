package domotix.view.listeners.edit_listeners;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.view.ModifySignal;
import domotix.view.passiveView.PannelloNord;
import domotix.view.Presenter;
import domotix.view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener per la gestione di eventi legati all'aggiunta e alla rimozione di attuatori a/da un artefatto
 */
public class EditAttuatoreArtefattoListener implements ActionListener, ModifySignal {
    private Interpretatore inter;
    private Rappresentatore rapp;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    public EditAttuatoreArtefattoListener(Interpretatore inter, Rappresentatore rapp, PannelloNord pannelloNord, Presenter presenter) {
        this.inter = inter;
        this.rapp = rapp;
        this.pannelloNord = pannelloNord;
        this.presenter = presenter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if(actionCommand.equalsIgnoreCase("Aggiungi un attuatore ad un artefatto")){
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboStanze);

            JButton selezionaStanza = new JButton("Scegli stanza");

            JPanel alto = new JPanel(new GridLayout(2,2));
            alto.add(new JLabel("Scegli una stanza da cui scegliere poi un artefatto"));
            alto.add(new JLabel(""));
            alto.add(comboStanze);
            alto.add(selezionaStanza);

            JDialog dialog = new JDialog();
            dialog.setTitle("Aggiunta attuatore ad un artefatto");
            dialog.getContentPane().add(alto, BorderLayout.NORTH);
            dialog.setLocationRelativeTo(null);
            dialog.pack();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            selezionaStanza.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comboStanze.setEnabled(false);
                    selezionaStanza.setEnabled(false);
                    String[] nomiArtefatti = rapp.getNomiArtefatti(nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                    if (nomiArtefatti.length > 0) {
                        JComboBox comboArtefatti = new JComboBox(nomiArtefatti);
                        AutoCompletion.enable(comboArtefatti);

                        JButton selezionaArtefatto = new JButton("Scegli Artefatto");

                        JPanel medio = new JPanel(new GridLayout(2, 2));
                        medio.add(new JLabel("Scegli un artefatto a cui collegare un attuatore:"));
                        medio.add(new JLabel(""));
                        medio.add(comboArtefatti);
                        medio.add(selezionaArtefatto);

                        dialog.getContentPane().add(medio, BorderLayout.CENTER);
                        dialog.pack();
                        selezionaArtefatto.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                comboArtefatti.setEnabled(false);
                                selezionaArtefatto.setEnabled(false);
                                String[] nomiCategorie = rapp.getNomiCategorieAttuatori();
                                if(nomiCategorie.length == 0){
                                    JOptionPane.showOptionDialog(null, "Non sono presenti categorie di attuatori!", "Impossibile aggiungere attuatori", -1, 0, null, null, null);
                                    dialog.dispose();
                                    return;
                                }
                                JComboBox comboCategorie = new JComboBox(nomiCategorie);
                                AutoCompletion.enable(comboCategorie);

                                JTextField campoNomeAttuatore = new JTextField(20);
                                campoNomeAttuatore.setToolTipText("Nome nuovo attuatore");

                                JButton inserisci = new JButton("Attacca attuatore");

                                JPanel basso = new JPanel(new GridLayout(2, 3));
                                basso.add(new JLabel("Scegli la categoria del nuovo attuatore: "));
                                basso.add(comboCategorie);
                                basso.add((new JLabel("")));
                                basso.add(new JLabel("Nome nuovo attuatore:"));
                                basso.add(campoNomeAttuatore);
                                basso.add(inserisci);

                                dialog.add(basso, BorderLayout.SOUTH);
                                dialog.pack();

                                inserisci.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (inter.aggiungiAttuatore(campoNomeAttuatore.getText(), nomiCategorie[comboCategorie.getSelectedIndex()], nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                                            JOptionPane.showConfirmDialog(null, "Attuatore inserito con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                            segnalaModifica(rapp.getDescrizioneArtefatto(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
                                        }else
                                            JOptionPane.showConfirmDialog(null, "Attuatore non inserito", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                        dialog.dispose();
                                    }
                                });
                            }
                        });//qui sotto
                    }else{
                        JOptionPane.showOptionDialog(null, "Non sono presenti artefatti nella stanza scelta", "Impossibile accedere agli artefatti", -1, 1, null, null, null);
                        dialog.dispose();
                        return;
                    }
                }
            });
            dialog.setVisible(true);
        }else{ //rimozione attuatore da un artefatto
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboStanze);

            JButton selezionaStanza = new JButton("Scegli stanza");

            JPanel alto = new JPanel(new GridLayout(2,2));
            alto.add(new JLabel("Scegli una stanza da cui scegliere poi un artefatto"));
            alto.add(new JLabel(""));
            alto.add(comboStanze);
            alto.add(selezionaStanza);

            JDialog dialog = new JDialog();
            dialog.setTitle("Rimozione attuatore da un artefatto");
            dialog.getContentPane().add(alto, BorderLayout.NORTH);
            dialog.setLocationRelativeTo(null);
            dialog.pack();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            selezionaStanza.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comboStanze.setEnabled(false);
                    selezionaStanza.setEnabled(false);
                    String[] nomiArtefatti = rapp.getNomiArtefatti(nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                    if(nomiArtefatti.length > 0) {
                        JComboBox comboArtefatti = new JComboBox(nomiArtefatti);
                        AutoCompletion.enable(comboArtefatti);

                        JButton selezionaArtefatto = new JButton("Scegli Artefatto");

                        JPanel medio = new JPanel(new GridLayout(2, 2));
                        medio.add(new JLabel("Scegli un artefatto da cui rimuovere un attuatore:"));
                        medio.add(new JLabel(""));
                        medio.add(comboArtefatti);
                        medio.add(selezionaArtefatto);

                        dialog.getContentPane().add(medio, BorderLayout.CENTER);
                        dialog.pack();
                        selezionaArtefatto.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                comboArtefatti.setEnabled(false);
                                selezionaArtefatto.setEnabled(false);
                                String[] nomiAttuatori = rapp.getNomiAttuatori(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                                if(nomiAttuatori.length > 0) {
                                    JComboBox comboAttuatori = new JComboBox(nomiAttuatori);
                                    AutoCompletion.enable(comboAttuatori);

                                    JButton rimuovi = new JButton("Rimuovi attuatore");

                                    JPanel basso = new JPanel(new GridLayout(2, 2));
                                    basso.add(new JLabel("Scegli l'attuatore da rimuovere: "));
                                    basso.add(new JLabel(""));
                                    basso.add(comboAttuatori);
                                    basso.add(rimuovi);

                                    dialog.add(basso, BorderLayout.SOUTH);
                                    dialog.pack();

                                    rimuovi.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            comboAttuatori.setEnabled(false);
                                            rimuovi.setEnabled(false);
                                            if (inter.rimuoviAttuatore(nomiAttuatori[comboAttuatori.getSelectedIndex()], nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                                                JOptionPane.showConfirmDialog(null, "Attuatore rimosso con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                                segnalaModifica(rapp.getDescrizioneArtefatto(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
                                            }else
                                                JOptionPane.showConfirmDialog(null, "Attuatore non rimosso", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                            dialog.dispose();
                                        }
                                    });//qui sotto
                                }else{
                                    JOptionPane.showOptionDialog(null, "Non sono presenti attuatori da rimuovere", "Impossibile rimuovere attuatori", -1, 1, null, null, null);
                                    dialog.dispose();
                                    return;
                                }
                            }
                        });
                    }else{
                        JOptionPane.showOptionDialog(null, "Non sono presenti artefatti nella stanza scelta", "Impossibile accedere agli artefatti", -1, 1, null, null, null);
                        dialog.dispose();
                        return;
                    }
                }
            });
            dialog.setVisible(true);
        }
    }

    @Override
    public void segnalaModifica(String descrizione) {
        presenter.show(descrizione);
    }
}