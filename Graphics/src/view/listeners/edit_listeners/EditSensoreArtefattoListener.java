package view.listeners.edit_listeners;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import view.ModifySignal;
import view.PannelloNord;
import view.Presenter;
import view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener per la gestione degli eventi legati all'aggiunta e alla rimozione di sensori ad un artefatto da selezionare
 */
public class EditSensoreArtefattoListener implements ActionListener, ModifySignal {
    private Interpretatore inter;
    private Rappresentatore rapp;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    public EditSensoreArtefattoListener(Interpretatore inter, Rappresentatore rapp, PannelloNord pannelloNord, Presenter presenter) {
        this.inter = inter;
        this.rapp = rapp;
        this.pannelloNord = pannelloNord;
        this.presenter = presenter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equalsIgnoreCase("Aggiungi un sensore ad un artefatto")) { //aggiunta sensore artefatto
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboStanze);

            JButton selezionaStanza = new JButton("Scegli stanza");

            JPanel alto = new JPanel(new GridLayout(2, 2));
            alto.add(new JLabel("Scegli una stanza da cui scegliere poi un artefatto"));
            alto.add(new JLabel(""));
            alto.add(comboStanze);
            alto.add(selezionaStanza);

            JDialog dialog = new JDialog();
            dialog.setTitle("Aggiunta sensore ad un artefatto");
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
                        medio.add(new JLabel("Scegli un artefatto a cui collegare un sensore:"));
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
                                String[] nomiCategorie = rapp.getNomiCategorieSensori();
                                JComboBox comboCategorie = new JComboBox(nomiCategorie);
                                AutoCompletion.enable(comboCategorie);

                                JTextField campoNomeSensore = new JTextField(20);
                                campoNomeSensore.setToolTipText("Nome nuovo sensore");

                                JButton inserisci = new JButton("Attacca sensore");

                                JPanel basso = new JPanel(new GridLayout(2, 2));
                                basso.add(new JLabel("Scegli la categoria del nuovo sensore: "));
                                basso.add(comboCategorie);
                                basso.add(campoNomeSensore);
                                basso.add(inserisci);

                                dialog.add(basso, BorderLayout.SOUTH);
                                dialog.pack();

                                inserisci.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (inter.aggiungiSensore(campoNomeSensore.getText(), nomiCategorie[comboCategorie.getSelectedIndex()], nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                                            JOptionPane.showConfirmDialog(null, "Sensore inserito con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                            segnalaModifica(rapp.getDescrizioneArtefatto(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
                                        }else
                                            JOptionPane.showConfirmDialog(null, "Sensore non inserito", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
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
        } else { //rimozione sensore artefatto
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboStanze);

            JButton selezionaStanza = new JButton("Scegli stanza");

            JPanel alto = new JPanel(new GridLayout(2, 2));
            alto.add(new JLabel("Scegli una stanza da cui scegliere poi un artefatto"));
            alto.add(new JLabel(""));
            alto.add(comboStanze);
            alto.add(selezionaStanza);

            JDialog dialog = new JDialog();
            dialog.setTitle("Rimozione sensore da un artefatto");
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
                        medio.add(new JLabel("Scegli un artefatto da cui rimuovere un sensore:"));
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
                                String[] nomiSensori = rapp.getNomiSensori(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                                if(nomiSensori.length > 0) {
                                    JComboBox comboSensori = new JComboBox(nomiSensori);
                                    AutoCompletion.enable(comboSensori);

                                    JButton rimuovi = new JButton("Rimuovi sensore");

                                    JPanel basso = new JPanel(new GridLayout(2, 2));
                                    basso.add(new JLabel("Scegli il sensore da rimuovere: "));
                                    basso.add(new JLabel(""));
                                    basso.add(comboSensori);
                                    basso.add(rimuovi);

                                    dialog.add(basso, BorderLayout.SOUTH);
                                    dialog.pack();

                                    rimuovi.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            comboSensori.setEnabled(false);
                                            rimuovi.setEnabled(false);
                                            if (inter.rimuoviSensore(nomiSensori[comboSensori.getSelectedIndex()], nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                                                JOptionPane.showConfirmDialog(null, "Sensore rimosso con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                                segnalaModifica(rapp.getDescrizioneArtefatto(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
                                            }else
                                                JOptionPane.showConfirmDialog(null, "Sensore non rimosso", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                            dialog.dispose();
                                        }
                                    });//qui sotto
                                }else{
                                    JOptionPane.showOptionDialog(null, "Non sono presenti sensori da rimuovere", "Impossibile rimuovere sensori", -1, 1, null, null, null);
                                    dialog.dispose();
                                    return;
                                }
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
        }
    }

    @Override
    public void segnalaModifica(String descrizione) {
        presenter.show(descrizione);
    }
}

