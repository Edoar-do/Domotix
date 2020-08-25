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
 * Listener per la gestione degli eventi relativi al collegamento di sensori e attuatori preesistenti ad un artefatto da selezionare
 */
public class EditCollegaDispArtefattoListener implements ActionListener, ModifySignal {
    private Interpretatore inter;
    private Rappresentatore rapp;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    public EditCollegaDispArtefattoListener(Interpretatore inter, Rappresentatore rapp, PannelloNord pannelloNord, Presenter presenter) {
        this.inter = inter;
        this.rapp = rapp;
        this.pannelloNord = pannelloNord;
        this.presenter = presenter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if(actionCommand.equalsIgnoreCase("Collega attuatore ad un altro artefatto")){ //collegga attuatore artefatto
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
            dialog.setTitle("Collegamento attuatore preesistente ad un artefatto");
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
                                String[] nomiAttuatori = rapp.getNomiAttuatoriAggiungibiliArtefatto(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                                if(nomiAttuatori.length > 0) {
                                    JComboBox comboAttuatori = new JComboBox(nomiAttuatori);
                                    AutoCompletion.enable(comboAttuatori);

                                    JButton collega = new JButton("Collega attuatore");

                                    JPanel basso = new JPanel(new GridLayout(2, 2));
                                    basso.add(new JLabel("Scegli l'attuatore da collegare: "));
                                    basso.add(new JLabel(""));
                                    basso.add(comboAttuatori);
                                    basso.add(collega);

                                    dialog.add(basso, BorderLayout.SOUTH);
                                    dialog.pack();

                                    collega.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            comboAttuatori.setEnabled(false);
                                            collega.setEnabled(false);
                                            if (inter.collegaAttuatore(nomiAttuatori[comboAttuatori.getSelectedIndex()], nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                                                JOptionPane.showConfirmDialog(null, "Attuatore collegato con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                                segnalaModifica(rapp.getDescrizioneArtefatto(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
                                            }else
                                                JOptionPane.showConfirmDialog(null, "Attuatore non collegato", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                            dialog.dispose();
                                        }
                                    });
                                }else{
                                    JOptionPane.showOptionDialog(null, "Non sono presenti attuatori associabili all'artefatto", "Impossibile accedere all'attuatore", -1, 1, null, null, null);
                                    dialog.dispose();
                                    return;
                                }
                            }
                        });
                    }else{
                        JOptionPane.showOptionDialog(null, "Non sono presenti artefatti nella stanza scelta", "Impossibile accedere all'artefatto", -1, 1, null, null, null);
                        dialog.dispose();
                        return;
                    }
                }
            });
            dialog.setVisible(true);
        }else{ //COLLEGAMENTO SENSORE ARTEFATTO - SECONDO CASO DEL COLLEGA LISTENER
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
            dialog.setTitle("Collegamento sensore preesistente ad un artefatto");
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
                                String[] nomiSensori = rapp.getNomiSensoriAggiungibiliArtefatto(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                                if(nomiSensori.length > 0) {
                                    JComboBox comboSensori = new JComboBox(nomiSensori);
                                    AutoCompletion.enable(comboSensori);

                                    JButton collega = new JButton("Collega sensore");

                                    JPanel basso = new JPanel(new GridLayout(2, 2));
                                    basso.add(new JLabel("Scegli il sensore da collegare: "));
                                    basso.add(new JLabel(""));
                                    basso.add(comboSensori);
                                    basso.add(collega);

                                    dialog.add(basso, BorderLayout.SOUTH);
                                    dialog.pack();

                                    collega.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            comboSensori.setEnabled(false);
                                            collega.setEnabled(false);
                                            if (inter.collegaSensore(nomiSensori[comboSensori.getSelectedIndex()], nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                                                JOptionPane.showConfirmDialog(null, "Sensore collegato con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                                segnalaModifica(rapp.getDescrizioneArtefatto(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
                                            }else
                                                JOptionPane.showConfirmDialog(null, "Sensore non collegato", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                            dialog.dispose();
                                        }
                                    });
                                }else{
                                    JOptionPane.showOptionDialog(null, "Non sono presenti sensori associabili all'artefatto", "Impossibile accedere al sensore", -1, 1, null, null, null);
                                    dialog.dispose();
                                    return;
                                }
                            }
                        });
                    }else{
                        JOptionPane.showOptionDialog(null, "Non sono presenti artefatti nella stanza scelta", "Impossibile accedere all'artefatto", -1, 1, null, null, null);
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
